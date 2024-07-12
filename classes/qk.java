import java.io.IOException;
import javax.annotation.Nullable;

public class qk implements ni<nl> {
	private qk.a a;
	private mr b;
	private int c;
	private int d;
	private int e;

	public qk() {
	}

	public qk(qk.a a, mr mr) {
		this(a, mr, -1, -1, -1);
	}

	public qk(int integer1, int integer2, int integer3) {
		this(qk.a.TIMES, null, integer1, integer2, integer3);
	}

	public qk(qk.a a, @Nullable mr mr, int integer3, int integer4, int integer5) {
		this.a = a;
		this.b = mr;
		this.c = integer3;
		this.d = integer4;
		this.e = integer5;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.a(qk.a.class);
		if (this.a == qk.a.TITLE || this.a == qk.a.SUBTITLE || this.a == qk.a.ACTIONBAR) {
			this.b = mg.h();
		}

		if (this.a == qk.a.TIMES) {
			this.c = mg.readInt();
			this.d = mg.readInt();
			this.e = mg.readInt();
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		if (this.a == qk.a.TITLE || this.a == qk.a.SUBTITLE || this.a == qk.a.ACTIONBAR) {
			mg.a(this.b);
		}

		if (this.a == qk.a.TIMES) {
			mg.writeInt(this.c);
			mg.writeInt(this.d);
			mg.writeInt(this.e);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}

	public static enum a {
		TITLE,
		SUBTITLE,
		ACTIONBAR,
		TIMES,
		CLEAR,
		RESET;
	}
}
