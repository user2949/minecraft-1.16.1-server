import java.io.IOException;
import javax.annotation.Nullable;

public class qn implements ni<nl> {
	private uh a;
	private acm b;

	public qn() {
	}

	public qn(@Nullable uh uh, @Nullable acm acm) {
		this.a = uh;
		this.b = acm;
	}

	@Override
	public void a(mg mg) throws IOException {
		int integer3 = mg.readByte();
		if ((integer3 & 1) > 0) {
			this.b = mg.a(acm.class);
		}

		if ((integer3 & 2) > 0) {
			this.a = mg.o();
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		if (this.b != null) {
			if (this.a != null) {
				mg.writeByte(3);
				mg.a(this.b);
				mg.a(this.a);
			} else {
				mg.writeByte(1);
				mg.a(this.b);
			}
		} else if (this.a != null) {
			mg.writeByte(2);
			mg.a(this.a);
		} else {
			mg.writeByte(0);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
