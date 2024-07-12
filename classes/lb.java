import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class lb extends ld<lc> {
	public static final lw<lb> a = new lw<lb>() {
		public lb b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(192L);
			int integer5 = dataInput.readInt();
			ln.a(8L * (long)integer5);
			byte[] arr6 = new byte[integer5];
			dataInput.readFully(arr6);
			return new lb(arr6);
		}

		@Override
		public String a() {
			return "BYTE[]";
		}

		@Override
		public String b() {
			return "TAG_Byte_Array";
		}
	};
	private byte[] b;

	public lb(byte[] arr) {
		this.b = arr;
	}

	public lb(List<Byte> list) {
		this(a(list));
	}

	private static byte[] a(List<Byte> list) {
		byte[] arr2 = new byte[list.size()];

		for (int integer3 = 0; integer3 < list.size(); integer3++) {
			Byte byte4 = (Byte)list.get(integer3);
			arr2[integer3] = byte4 == null ? 0 : byte4;
		}

		return arr2;
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.b.length);
		dataOutput.write(this.b);
	}

	@Override
	public byte a() {
		return 7;
	}

	@Override
	public lw<lb> b() {
		return a;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder2 = new StringBuilder("[B;");

		for (int integer3 = 0; integer3 < this.b.length; integer3++) {
			if (integer3 != 0) {
				stringBuilder2.append(',');
			}

			stringBuilder2.append(this.b[integer3]).append('B');
		}

		return stringBuilder2.append(']').toString();
	}

	@Override
	public lu c() {
		byte[] arr2 = new byte[this.b.length];
		System.arraycopy(this.b, 0, arr2, 0, this.b.length);
		return new lb(arr2);
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof lb && Arrays.equals(this.b, ((lb)object).b);
	}

	public int hashCode() {
		return Arrays.hashCode(this.b);
	}

	@Override
	public mr a(String string, int integer) {
		mr mr4 = new nd("B").a(g);
		mx mx5 = new nd("[").a(mr4).c(";");

		for (int integer6 = 0; integer6 < this.b.length; integer6++) {
			mx mx7 = new nd(String.valueOf(this.b[integer6])).a(f);
			mx5.c(" ").a(mx7).a(mr4);
			if (integer6 != this.b.length - 1) {
				mx5.c(",");
			}
		}

		mx5.c("]");
		return mx5;
	}

	public byte[] d() {
		return this.b;
	}

	public int size() {
		return this.b.length;
	}

	public lc get(int integer) {
		return lc.a(this.b[integer]);
	}

	public lc set(int integer, lc lc) {
		byte byte4 = this.b[integer];
		this.b[integer] = lc.h();
		return lc.a(byte4);
	}

	public void c(int integer, lc lc) {
		this.b = ArrayUtils.add(this.b, integer, lc.h());
	}

	@Override
	public boolean a(int integer, lu lu) {
		if (lu instanceof lr) {
			this.b[integer] = ((lr)lu).h();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean b(int integer, lu lu) {
		if (lu instanceof lr) {
			this.b = ArrayUtils.add(this.b, integer, ((lr)lu).h());
			return true;
		} else {
			return false;
		}
	}

	public lc c(int integer) {
		byte byte3 = this.b[integer];
		this.b = ArrayUtils.remove(this.b, integer);
		return lc.a(byte3);
	}

	@Override
	public byte d_() {
		return 1;
	}

	public void clear() {
		this.b = new byte[0];
	}
}
