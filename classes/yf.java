import java.nio.file.Path;
import java.util.function.UnaryOperator;

public class yf {
	private final Path a;
	private ye b;

	public yf(Path path) {
		this.a = path;
		this.b = ye.a(path);
	}

	public ye a() {
		return this.b;
	}

	public void b() {
		this.b.c(this.a);
	}

	public yf a(UnaryOperator<ye> unaryOperator) {
		(this.b = (ye)unaryOperator.apply(this.b)).c(this.a);
		return this;
	}
}
