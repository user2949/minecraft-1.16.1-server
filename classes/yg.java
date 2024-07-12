import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class yg implements Runnable {
	private static final Logger a = LogManager.getLogger();
	private final yd b;
	private final long c;

	public yg(yd yd) {
		this.b = yd;
		this.c = yd.bd();
	}

	public void run() {
		while (this.b.u()) {
			long long2 = this.b.aw();
			long long4 = v.b();
			long long6 = long4 - long2;
			if (long6 > this.c) {
				a.fatal(
					"A single server tick took {} seconds (should be max {})",
					String.format(Locale.ROOT, "%.2f", (float)long6 / 1000.0F),
					String.format(Locale.ROOT, "%.2f", 0.05F)
				);
				a.fatal("Considering it to be crashed, server will forcibly shutdown.");
				ThreadMXBean threadMXBean8 = ManagementFactory.getThreadMXBean();
				ThreadInfo[] arr9 = threadMXBean8.dumpAllThreads(true, true);
				StringBuilder stringBuilder10 = new StringBuilder();
				Error error11 = new Error();

				for (ThreadInfo threadInfo15 : arr9) {
					if (threadInfo15.getThreadId() == this.b.au().getId()) {
						error11.setStackTrace(threadInfo15.getStackTrace());
					}

					stringBuilder10.append(threadInfo15);
					stringBuilder10.append("\n");
				}

				j j12 = new j("Watching Server", error11);
				this.b.b(j12);
				k k13 = j12.a("Thread Dump");
				k13.a("Threads", stringBuilder10);
				File file14 = new File(new File(this.b.A(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
				if (j12.a(file14)) {
					a.error("This crash report has been saved to: {}", file14.getAbsolutePath());
				} else {
					a.error("We were unable to save this crash report to disk.");
				}

				this.a();
			}

			try {
				Thread.sleep(long2 + this.c - long4);
			} catch (InterruptedException var15) {
			}
		}
	}

	private void a() {
		try {
			Timer timer2 = new Timer();
			timer2.schedule(new TimerTask() {
				public void run() {
					Runtime.getRuntime().halt(1);
				}
			}, 10000L);
			System.exit(1);
		} catch (Throwable var2) {
			Runtime.getRuntime().halt(1);
		}
	}
}
