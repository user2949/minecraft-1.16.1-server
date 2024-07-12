import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class ll extends ld<lm> {
	public static final lw<ll> a = new lw<ll>() {
		public ll b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(192L);
			int integer5 = dataInput.readInt();
			ln.a(64L * (long)integer5);
			long[] arr6 = new long[integer5];

			for (int integer7 = 0; integer7 < integer5; integer7++) {
				arr6[integer7] = dataInput.readLong();
			}

			return new ll(arr6);
		}

		@Override
		public String a() {
			return "LONG[]";
		}

		@Override
		public String b() {
			return "TAG_Long_Array";
		}
	};
	private long[] b;

	public ll(long[] arr) {
		this.b = arr;
	}

	public ll(LongSet longSet) {
		this.b = longSet.toLongArray();
	}

	public ll(List<Long> list) {
		this(a(list));
	}

	private static long[] a(List<Long> list) {
		long[] arr2 = new long[list.size()];

		for (int integer3 = 0; integer3 < list.size(); integer3++) {
			Long long4 = (Long)list.get(integer3);
			arr2[integer3] = long4 == null ? 0L : long4;
		}

		return arr2;
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.b.length);

		for (long long6 : this.b) {
			dataOutput.writeLong(long6);
		}
	}

	@Override
	public byte a() {
		return 12;
	}

	@Override
	public lw<ll> b() {
		return a;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder2 = new StringBuilder("[L;");

		for (int integer3 = 0; integer3 < this.b.length; integer3++) {
			if (integer3 != 0) {
				stringBuilder2.append(',');
			}

			stringBuilder2.append(this.b[integer3]).append('L');
		}

		return stringBuilder2.append(']').toString();
	}

	public ll c() {
		long[] arr2 = new long[this.b.length];
		System.arraycopy(this.b, 0, arr2, 0, this.b.length);
		return new ll(arr2);
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof ll && Arrays.equals(this.b, ((ll)object).b);
	}

	public int hashCode() {
		return Arrays.hashCode(this.b);
	}

	@Override
	public mr a(String string, int integer) {
		mr mr4 = new nd("L").a(g);
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

	public long[] g() {
		return this.b;
	}

	public int size() {
		return this.b.length;
	}

	public lm get(int integer) {
		return lm.a(this.b[integer]);
	}

	public lm set(int integer, lm lm) {
		long long4 = this.b[integer];
		this.b[integer] = lm.e();
		return lm.a(long4);
	}

	public void c(int integer, lm lm) {
		this.b = ArrayUtils.add(this.b, integer, lm.e());
	}

	@Override
	public boolean a(int integer, lu lu) {
		if (lu instanceof lr) {
			this.b[integer] = ((lr)lu).e();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean b(int integer, lu lu) {
		if (lu instanceof lr) {
			this.b = ArrayUtils.add(this.b, integer, ((lr)lu).e());
			return true;
		} else {
			return false;
		}
	}

	public lm c(int integer) {
		long long3 = this.b[integer];
		this.b = ArrayUtils.remove(this.b, integer);
		return lm.a(long3);
	}

	@Override
	public byte d_() {
		return 4;
	}

	public void clear() {
		this.b = new long[0];
	}
}
