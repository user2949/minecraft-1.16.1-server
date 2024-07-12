import java.io.DataOutput;
import java.io.IOException;

public interface lu {
	i d = i.AQUA;
	i e = i.GREEN;
	i f = i.GOLD;
	i g = i.RED;

	void a(DataOutput dataOutput) throws IOException;

	String toString();

	byte a();

	lw<?> b();

	lu c();

	default String f_() {
		return this.toString();
	}

	default mr l() {
		return this.a("", 0);
	}

	mr a(String string, int integer);
}
