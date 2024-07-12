import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class qt implements ni<nl> {
	private int a;
	private final List<qt.a> b = Lists.<qt.a>newArrayList();

	public qt() {
	}

	public qt(int integer, Collection<apt> collection) {
		this.a = integer;

		for (apt apt5 : collection) {
			this.b.add(new qt.a(apt5.a(), apt5.b(), apt5.c()));
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		int integer3 = mg.readInt();

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			uh uh5 = mg.o();
			aps aps6 = gl.aP.a(uh5);
			double double7 = mg.readDouble();
			List<apv> list9 = Lists.<apv>newArrayList();
			int integer10 = mg.i();

			for (int integer11 = 0; integer11 < integer10; integer11++) {
				UUID uUID12 = mg.k();
				list9.add(new apv(uUID12, "Unknown synced attribute modifier", mg.readDouble(), apv.a.a(mg.readByte())));
			}

			this.b.add(new qt.a(aps6, double7, list9));
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.writeInt(this.b.size());

		for (qt.a a4 : this.b) {
			mg.a(gl.aP.b(a4.a()));
			mg.writeDouble(a4.b());
			mg.d(a4.c().size());

			for (apv apv6 : a4.c()) {
				mg.a(apv6.a());
				mg.writeDouble(apv6.d());
				mg.writeByte(apv6.c().a());
			}
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}

	public class a {
		private final aps b;
		private final double c;
		private final Collection<apv> d;

		public a(aps aps, double double3, Collection<apv> collection) {
			this.b = aps;
			this.c = double3;
			this.d = collection;
		}

		public aps a() {
			return this.b;
		}

		public double b() {
			return this.c;
		}

		public Collection<apv> c() {
			return this.d;
		}
	}
}
