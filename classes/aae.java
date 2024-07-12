import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public interface aae extends AutoCloseable {
	InputStream a(aaf aaf, uh uh) throws IOException;

	Collection<uh> a(aaf aaf, String string2, String string3, int integer, Predicate<String> predicate);

	boolean b(aaf aaf, uh uh);

	Set<String> a(aaf aaf);

	@Nullable
	<T> T a(aai<T> aai) throws IOException;

	String a();

	void close();
}
