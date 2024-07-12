import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class lk extends ld<lu> {
	public static final lw<lk> a = new lw<lk>() {
		public lk b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(296L);
			if (integer > 512) {
				throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
			} else {
				byte byte5 = dataInput.readByte();
				int integer6 = dataInput.readInt();
				if (byte5 == 0 && integer6 > 0) {
					throw new RuntimeException("Missing type on ListTag");
				} else {
					ln.a(32L * (long)integer6);
					lw<?> lw7 = lx.a(byte5);
					List<lu> list8 = Lists.<lu>newArrayListWithCapacity(integer6);

					for (int integer9 = 0; integer9 < integer6; integer9++) {
						list8.add(lw7.b(dataInput, integer + 1, ln));
					}

					return new lk(list8, byte5);
				}
			}
		}

		@Override
		public String a() {
			return "LIST";
		}

		@Override
		public String b() {
			return "TAG_List";
		}
	};
	private static final ByteSet b = new ByteOpenHashSet(Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6));
	private final List<lu> c;
	private byte h;

	private lk(List<lu> list, byte byte2) {
		this.c = list;
		this.h = byte2;
	}

	public lk() {
		this(Lists.<lu>newArrayList(), (byte)0);
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		if (this.c.isEmpty()) {
			this.h = 0;
		} else {
			this.h = ((lu)this.c.get(0)).a();
		}

		dataOutput.writeByte(this.h);
		dataOutput.writeInt(this.c.size());

		for (lu lu4 : this.c) {
			lu4.a(dataOutput);
		}
	}

	@Override
	public byte a() {
		return 9;
	}

	@Override
	public lw<lk> b() {
		return a;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder2 = new StringBuilder("[");

		for (int integer3 = 0; integer3 < this.c.size(); integer3++) {
			if (integer3 != 0) {
				stringBuilder2.append(',');
			}

			stringBuilder2.append(this.c.get(integer3));
		}

		return stringBuilder2.append(']').toString();
	}

	private void g() {
		if (this.c.isEmpty()) {
			this.h = 0;
		}
	}

	@Override
	public lu remove(int integer) {
		lu lu3 = (lu)this.c.remove(integer);
		this.g();
		return lu3;
	}

	public boolean isEmpty() {
		return this.c.isEmpty();
	}

	public le a(int integer) {
		if (integer >= 0 && integer < this.c.size()) {
			lu lu3 = (lu)this.c.get(integer);
			if (lu3.a() == 10) {
				return (le)lu3;
			}
		}

		return new le();
	}

	public lk b(int integer) {
		if (integer >= 0 && integer < this.c.size()) {
			lu lu3 = (lu)this.c.get(integer);
			if (lu3.a() == 9) {
				return (lk)lu3;
			}
		}

		return new lk();
	}

	public short d(int integer) {
		if (integer >= 0 && integer < this.c.size()) {
			lu lu3 = (lu)this.c.get(integer);
			if (lu3.a() == 2) {
				return ((ls)lu3).g();
			}
		}

		return 0;
	}

	public int e(int integer) {
		if (integer >= 0 && integer < this.c.size()) {
			lu lu3 = (lu)this.c.get(integer);
			if (lu3.a() == 3) {
				return ((lj)lu3).f();
			}
		}

		return 0;
	}

	public int[] f(int integer) {
		if (integer >= 0 && integer < this.c.size()) {
			lu lu3 = (lu)this.c.get(integer);
			if (lu3.a() == 11) {
				return ((li)lu3).g();
			}
		}

		return new int[0];
	}

	public double h(int integer) {
		if (integer >= 0 && integer < this.c.size()) {
			lu lu3 = (lu)this.c.get(integer);
			if (lu3.a() == 6) {
				return ((lf)lu3).i();
			}
		}

		return 0.0;
	}

	public float i(int integer) {
		if (integer >= 0 && integer < this.c.size()) {
			lu lu3 = (lu)this.c.get(integer);
			if (lu3.a() == 5) {
				return ((lh)lu3).j();
			}
		}

		return 0.0F;
	}

	public String j(int integer) {
		if (integer >= 0 && integer < this.c.size()) {
			lu lu3 = (lu)this.c.get(integer);
			return lu3.a() == 8 ? lu3.f_() : lu3.toString();
		} else {
			return "";
		}
	}

	public int size() {
		return this.c.size();
	}

	public lu get(int integer) {
		return (lu)this.c.get(integer);
	}

	@Override
	public lu set(int integer, lu lu) {
		lu lu4 = this.k(integer);
		if (!this.a(integer, lu)) {
			throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", lu.a(), this.h));
		} else {
			return lu4;
		}
	}

	@Override
	public void add(int integer, lu lu) {
		if (!this.b(integer, lu)) {
			throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", lu.a(), this.h));
		}
	}

	@Override
	public boolean a(int integer, lu lu) {
		if (this.a(lu)) {
			this.c.set(integer, lu);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean b(int integer, lu lu) {
		if (this.a(lu)) {
			this.c.add(integer, lu);
			return true;
		} else {
			return false;
		}
	}

	private boolean a(lu lu) {
		if (lu.a() == 0) {
			return false;
		} else if (this.h == 0) {
			this.h = lu.a();
			return true;
		} else {
			return this.h == lu.a();
		}
	}

	public lk c() {
		Iterable<lu> iterable2 = (Iterable<lu>)(lx.a(this.h).c() ? this.c : Iterables.transform(this.c, lu::c));
		List<lu> list3 = Lists.<lu>newArrayList(iterable2);
		return new lk(list3, this.h);
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof lk && Objects.equals(this.c, ((lk)object).c);
	}

	public int hashCode() {
		return this.c.hashCode();
	}

	@Override
	public mr a(String string, int integer) {
		if (this.isEmpty()) {
			return new nd("[]");
		} else if (b.contains(this.h) && this.size() <= 8) {
			String string4 = ", ";
			mx mx5 = new nd("[");

			for (int integer6 = 0; integer6 < this.c.size(); integer6++) {
				if (integer6 != 0) {
					mx5.c(", ");
				}

				mx5.a(((lu)this.c.get(integer6)).l());
			}

			mx5.c("]");
			return mx5;
		} else {
			mx mx4 = new nd("[");
			if (!string.isEmpty()) {
				mx4.c("\n");
			}

			String string5 = String.valueOf(',');

			for (int integer6 = 0; integer6 < this.c.size(); integer6++) {
				mx mx7 = new nd(Strings.repeat(string, integer + 1));
				mx7.a(((lu)this.c.get(integer6)).a(string, integer + 1));
				if (integer6 != this.c.size() - 1) {
					mx7.c(string5).c(string.isEmpty() ? " " : "\n");
				}

				mx4.a(mx7);
			}

			if (!string.isEmpty()) {
				mx4.c("\n").c(Strings.repeat(string, integer));
			}

			mx4.c("]");
			return mx4;
		}
	}

	@Override
	public byte d_() {
		return this.h;
	}

	public void clear() {
		this.c.clear();
		this.h = 0;
	}
}
