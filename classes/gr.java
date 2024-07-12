import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import java.util.stream.IntStream;
import javax.annotation.concurrent.Immutable;

@Immutable
public class gr implements Comparable<gr> {
	public static final Codec<gr> c = Codec.INT_STREAM
		.comapFlatMap(intStream -> v.a(intStream, 3).map(arr -> new gr(arr[0], arr[1], arr[2])), gr -> IntStream.of(new int[]{gr.u(), gr.v(), gr.w()}));
	public static final gr d = new gr(0, 0, 0);
	private int a;
	private int b;
	private int e;

	public gr(int integer1, int integer2, int integer3) {
		this.a = integer1;
		this.b = integer2;
		this.e = integer3;
	}

	public gr(double double1, double double2, double double3) {
		this(aec.c(double1), aec.c(double2), aec.c(double3));
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof gr)) {
			return false;
		} else {
			gr gr3 = (gr)object;
			if (this.u() != gr3.u()) {
				return false;
			} else {
				return this.v() != gr3.v() ? false : this.w() == gr3.w();
			}
		}
	}

	public int hashCode() {
		return (this.v() + this.w() * 31) * 31 + this.u();
	}

	public int compareTo(gr gr) {
		if (this.v() == gr.v()) {
			return this.w() == gr.w() ? this.u() - gr.u() : this.w() - gr.w();
		} else {
			return this.v() - gr.v();
		}
	}

	public int u() {
		return this.a;
	}

	public int v() {
		return this.b;
	}

	public int w() {
		return this.e;
	}

	protected void o(int integer) {
		this.a = integer;
	}

	protected void p(int integer) {
		this.b = integer;
	}

	protected void q(int integer) {
		this.e = integer;
	}

	public gr n() {
		return this.l(1);
	}

	public gr l(int integer) {
		return this.b(fz.DOWN, integer);
	}

	public gr b(fz fz, int integer) {
		return integer == 0 ? this : new gr(this.u() + fz.i() * integer, this.v() + fz.j() * integer, this.w() + fz.k() * integer);
	}

	public gr d(gr gr) {
		return new gr(this.v() * gr.w() - this.w() * gr.v(), this.w() * gr.u() - this.u() * gr.w(), this.u() * gr.v() - this.v() * gr.u());
	}

	public boolean a(gr gr, double double2) {
		return this.a((double)gr.u(), (double)gr.v(), (double)gr.w(), false) < double2 * double2;
	}

	public boolean a(gj gj, double double2) {
		return this.a(gj.a(), gj.b(), gj.c(), true) < double2 * double2;
	}

	public double j(gr gr) {
		return this.a((double)gr.u(), (double)gr.v(), (double)gr.w(), true);
	}

	public double a(gj gj, boolean boolean2) {
		return this.a(gj.a(), gj.b(), gj.c(), boolean2);
	}

	public double a(double double1, double double2, double double3, boolean boolean4) {
		double double9 = boolean4 ? 0.5 : 0.0;
		double double11 = (double)this.u() + double9 - double1;
		double double13 = (double)this.v() + double9 - double2;
		double double15 = (double)this.w() + double9 - double3;
		return double11 * double11 + double13 * double13 + double15 * double15;
	}

	public int k(gr gr) {
		float float3 = (float)Math.abs(gr.u() - this.u());
		float float4 = (float)Math.abs(gr.v() - this.v());
		float float5 = (float)Math.abs(gr.w() - this.w());
		return (int)(float3 + float4 + float5);
	}

	public String toString() {
		return MoreObjects.toStringHelper(this).add("x", this.u()).add("y", this.v()).add("z", this.w()).toString();
	}
}
