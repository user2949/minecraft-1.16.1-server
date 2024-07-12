import java.lang.Thread.UncaughtExceptionHandler;
import org.apache.logging.log4j.Logger;

public class m implements UncaughtExceptionHandler {
	private final Logger a;

	public m(Logger logger) {
		this.a = logger;
	}

	public void uncaughtException(Thread thread, Throwable throwable) {
		this.a.error("Caught previously unhandled exception :", throwable);
	}
}
