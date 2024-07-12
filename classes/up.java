import java.io.OutputStream;
import java.io.PrintStream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class up extends PrintStream {
	protected static final Logger a = LogManager.getLogger();
	protected final String b;

	public up(String string, OutputStream outputStream) {
		super(outputStream);
		this.b = string;
	}

	public void println(@Nullable String string) {
		this.a(string);
	}

	public void println(Object object) {
		this.a(String.valueOf(object));
	}

	protected void a(@Nullable String string) {
		a.info("[{}]: {}", this.b, string);
	}
}
