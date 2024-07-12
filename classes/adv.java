import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class adv {
	private static final Logger b = LogManager.getLogger();
	public static final ListeningExecutorService a = MoreExecutors.listeningDecorator(
		Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(true).setUncaughtExceptionHandler(new m(b)).setNameFormat("Downloader %d").build())
	);

	public static int a() {
		try {
			ServerSocket serverSocket1 = new ServerSocket(0);
			Throwable var1 = null;

			int var2;
			try {
				var2 = serverSocket1.getLocalPort();
			} catch (Throwable var12) {
				var1 = var12;
				throw var12;
			} finally {
				if (serverSocket1 != null) {
					if (var1 != null) {
						try {
							serverSocket1.close();
						} catch (Throwable var11) {
							var1.addSuppressed(var11);
						}
					} else {
						serverSocket1.close();
					}
				}
			}

			return var2;
		} catch (IOException var14) {
			return 25564;
		}
	}
}
