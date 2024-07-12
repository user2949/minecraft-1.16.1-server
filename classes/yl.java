import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.server.MinecraftServer;

public class yl extends JComponent {
	private static final DecimalFormat a = v.a(
		new DecimalFormat("########0.000"), decimalFormat -> decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
	);
	private final int[] b = new int[256];
	private int c;
	private final String[] d = new String[11];
	private final MinecraftServer e;
	private final Timer f;

	public yl(MinecraftServer minecraftServer) {
		this.e = minecraftServer;
		this.setPreferredSize(new Dimension(456, 246));
		this.setMinimumSize(new Dimension(456, 246));
		this.setMaximumSize(new Dimension(456, 246));
		this.f = new Timer(500, actionEvent -> this.b());
		this.f.start();
		this.setBackground(Color.BLACK);
	}

	private void b() {
		long long2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		this.d[0] = "Memory use: " + long2 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
		this.d[1] = "Avg tick: " + a.format(this.a(this.e.h) * 1.0E-6) + " ms";
		this.b[this.c++ & 0xFF] = (int)(long2 * 100L / Runtime.getRuntime().maxMemory());
		this.repaint();
	}

	private double a(long[] arr) {
		long long3 = 0L;

		for (long long8 : arr) {
			long3 += long8;
		}

		return (double)long3 / (double)arr.length;
	}

	public void paint(Graphics graphics) {
		graphics.setColor(new Color(16777215));
		graphics.fillRect(0, 0, 456, 246);

		for (int integer3 = 0; integer3 < 256; integer3++) {
			int integer4 = this.b[integer3 + this.c & 0xFF];
			graphics.setColor(new Color(integer4 + 28 << 16));
			graphics.fillRect(integer3, 100 - integer4, 1, integer4);
		}

		graphics.setColor(Color.BLACK);

		for (int integer3 = 0; integer3 < this.d.length; integer3++) {
			String string4 = this.d[integer3];
			if (string4 != null) {
				graphics.drawString(string4, 32, 116 + integer3 * 16);
			}
		}
	}

	public void a() {
		this.f.stop();
	}
}
