import com.google.common.collect.Lists;
import com.mojang.util.QueueLogAppender;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class yj extends JComponent {
	private static final Font a = new Font("Monospaced", 0, 12);
	private static final Logger b = LogManager.getLogger();
	private final yd c;
	private Thread d;
	private final Collection<Runnable> e = Lists.<Runnable>newArrayList();
	private final AtomicBoolean f = new AtomicBoolean();

	public static yj a(yd yd) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception var3) {
		}

		final JFrame jFrame2 = new JFrame("Minecraft server");
		final yj yj3 = new yj(yd);
		jFrame2.setDefaultCloseOperation(2);
		jFrame2.add(yj3);
		jFrame2.pack();
		jFrame2.setLocationRelativeTo(null);
		jFrame2.setVisible(true);
		jFrame2.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				if (!yj3.f.getAndSet(true)) {
					jFrame2.setTitle("Minecraft server - shutting down!");
					yd.a(true);
					yj3.f();
				}
			}
		});
		yj3.a(jFrame2::dispose);
		yj3.a();
		return yj3;
	}

	private yj(yd yd) {
		this.c = yd;
		this.setPreferredSize(new Dimension(854, 480));
		this.setLayout(new BorderLayout());

		try {
			this.add(this.e(), "Center");
			this.add(this.c(), "West");
		} catch (Exception var3) {
			b.error("Couldn't build server GUI", (Throwable)var3);
		}
	}

	public void a(Runnable runnable) {
		this.e.add(runnable);
	}

	private JComponent c() {
		JPanel jPanel2 = new JPanel(new BorderLayout());
		yl yl3 = new yl(this.c);
		this.e.add(yl3::a);
		jPanel2.add(yl3, "North");
		jPanel2.add(this.d(), "Center");
		jPanel2.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
		return jPanel2;
	}

	private JComponent d() {
		JList<?> jList2 = new yk(this.c);
		JScrollPane jScrollPane3 = new JScrollPane(jList2, 22, 30);
		jScrollPane3.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
		return jScrollPane3;
	}

	private JComponent e() {
		JPanel jPanel2 = new JPanel(new BorderLayout());
		JTextArea jTextArea3 = new JTextArea();
		JScrollPane jScrollPane4 = new JScrollPane(jTextArea3, 22, 30);
		jTextArea3.setEditable(false);
		jTextArea3.setFont(a);
		JTextField jTextField5 = new JTextField();
		jTextField5.addActionListener(actionEvent -> {
			String string4 = jTextField5.getText().trim();
			if (!string4.isEmpty()) {
				this.c.a(string4, this.c.aC());
			}

			jTextField5.setText("");
		});
		jTextArea3.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent focusEvent) {
			}
		});
		jPanel2.add(jScrollPane4, "Center");
		jPanel2.add(jTextField5, "South");
		jPanel2.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
		this.d = new Thread(() -> {
			String string4;
			while ((string4 = QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
				this.a(jTextArea3, jScrollPane4, string4);
			}
		});
		this.d.setUncaughtExceptionHandler(new m(b));
		this.d.setDaemon(true);
		return jPanel2;
	}

	public void a() {
		this.d.start();
	}

	public void b() {
		if (!this.f.getAndSet(true)) {
			this.f();
		}
	}

	private void f() {
		this.e.forEach(Runnable::run);
	}

	public void a(JTextArea jTextArea, JScrollPane jScrollPane, String string) {
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(() -> this.a(jTextArea, jScrollPane, string));
		} else {
			Document document5 = jTextArea.getDocument();
			JScrollBar jScrollBar6 = jScrollPane.getVerticalScrollBar();
			boolean boolean7 = false;
			if (jScrollPane.getViewport().getView() == jTextArea) {
				boolean7 = (double)jScrollBar6.getValue() + jScrollBar6.getSize().getHeight() + (double)(a.getSize() * 4) > (double)jScrollBar6.getMaximum();
			}

			try {
				document5.insertString(document5.getLength(), string, null);
			} catch (BadLocationException var8) {
			}

			if (boolean7) {
				jScrollBar6.setValue(Integer.MAX_VALUE);
			}
		}
	}
}
