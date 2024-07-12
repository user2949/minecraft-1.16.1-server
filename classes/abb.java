import java.io.Closeable;
import java.io.InputStream;

public interface abb extends Closeable {
	InputStream b();

	String d();
}
