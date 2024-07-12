import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class li extends ld<lj> {
	public static final lw<li> a = new lw<li>() {
		public li b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(192L);
			int integer5 = dataInput.readInt();
			ln.a(32L * (long)integer5);
			int[] arr6 = new int[integer5];

			for (int integer7 = 0; integer7 < integer5; integer7++) {
				arr6[integer7] = dataInput.readInt();
			}

			return new li(arr6);
		}

		@Override
		public String a() {
			return "INT[]";
		}

		@Override
		public String b() {
			return "TAG_Int_Array";
		}
	};
	private int[] b;

	public li(int[] arr) {
		this.b = arr;
	}

	public li(List<Integer> list) {
		this(a(list));
	}

	private static int[] a(List<Integer> list) {
		int[] arr2 = new int[list.size()];

		for (int integer3 = 0; integer3 < list.size(); integer3++) {
			Integer integer4 = (Integer)list.get(integer3);
			arr2[integer3] = integer4 == null ? 0 : integer4;
		}

		return arr2;
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.b.length);

		for (int integer6 : this.b) {
			dataOutput.writeInt(integer6);
		}
	}

	@Override
	public byte a() {
		return 11;
	}

	@Override
	public lw<li> b() {
		return a;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder2 = new StringBuilder("[I;");

		for (int integer3 = 0; integer3 < this.b.length; integer3++) {
			if (integer3 != 0) {
				stringBuilder2.append(',');
			}

			stringBuilder2.append(this.b[integer3]);
		}

		return stringBuilder2.append(']').toString();
	}

	public li c() {
		int[] arr2 = new int[this.b.length];
		System.arraycopy(this.b, 0, arr2, 0, this.b.length);
		return new li(arr2);
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof li && Arrays.equals(this.b, ((li)object).b);
	}

	public int hashCode() {
		return Arrays.hashCode(this.b);
	}

	public int[] g() {
		return this.b;
	}

	@Override
	public mr a(String string, int integer) {
		mr mr4 = new nd("I").a(g);
		mx mx5 = new nd("[").a(mr4).c(";");

		for (int integer6 = 0; integer6 < this.b.length; integer6++) {
			mx5.c(" ").a(new nd(String.valueOf(this.b[integer6])).a(f));
			if (integer6 != this.b.length - 1) {
				mx5.c(",");
			}
		}

		mx5.c("]");
		return mx5;
	}

	public int size() {
		return this.b.length;
	}

	public lj get(int integer) {
		return lj.a(this.b[integer]);
	}

	public lj set(int integer, lj lj) {
		int integer4 = this.b[integer];
		this.b[integer] = lj.f();
		return lj.a(integer4);
	}

	public void c(int integer, lj lj) {
		this.b = ArrayUtils.add(this.b, integer, lj.f());
	}

	@Override
	public boolean a(int integer, lu lu) {
		if (lu instanceof lr) {
			this.b[integer] = ((lr)lu).f();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean b(int integer, lu lu) {
		if (lu instanceof lr) {
			this.b = ArrayUtils.add(this.b, integer, ((lr)lu).f());
			return true;
		} else {
			return false;
		}
	}

	public lj c(int integer) {
		int integer3 = this.b[integer];
		this.b = ArrayUtils.remove(this.b, integer);
		return lj.a(integer3);
	}

	@Override
	public byte d_() {
		return 3;
	}

	public void clear() {
		this.b = new int[0];
	}
}
