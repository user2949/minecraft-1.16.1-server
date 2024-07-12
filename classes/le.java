import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class le implements lu {
	public static final Codec<le> a = Codec.PASSTHROUGH.comapFlatMap(dynamic -> {
		lu lu2 = dynamic.convert(lp.a).getValue();
		return lu2 instanceof le ? DataResult.success((le)lu2) : DataResult.error("Not a compound tag: " + lu2);
	}, le -> new Dynamic<>(lp.a, le));
	private static final Logger c = LogManager.getLogger();
	private static final Pattern h = Pattern.compile("[A-Za-z0-9._+-]+");
	public static final lw<le> b = new lw<le>() {
		public le b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(384L);
			if (integer > 512) {
				throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
			} else {
				Map<String, lu> map5 = Maps.<String, lu>newHashMap();

				byte byte6;
				while ((byte6 = le.c(dataInput, ln)) != 0) {
					String string7 = le.d(dataInput, ln);
					ln.a((long)(224 + 16 * string7.length()));
					lu lu8 = le.b(lx.a(byte6), string7, dataInput, integer + 1, ln);
					if (map5.put(string7, lu8) != null) {
						ln.a(288L);
					}
				}

				return new le(map5);
			}
		}

		@Override
		public String a() {
			return "COMPOUND";
		}

		@Override
		public String b() {
			return "TAG_Compound";
		}
	};
	private final Map<String, lu> i;

	protected le(Map<String, lu> map) {
		this.i = map;
	}

	public le() {
		this(Maps.<String, lu>newHashMap());
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		for (String string4 : this.i.keySet()) {
			lu lu5 = (lu)this.i.get(string4);
			a(string4, lu5, dataOutput);
		}

		dataOutput.writeByte(0);
	}

	public Set<String> d() {
		return this.i.keySet();
	}

	@Override
	public byte a() {
		return 10;
	}

	@Override
	public lw<le> b() {
		return b;
	}

	public int e() {
		return this.i.size();
	}

	@Nullable
	public lu a(String string, lu lu) {
		return (lu)this.i.put(string, lu);
	}

	public void a(String string, byte byte2) {
		this.i.put(string, lc.a(byte2));
	}

	public void a(String string, short short2) {
		this.i.put(string, ls.a(short2));
	}

	public void b(String string, int integer) {
		this.i.put(string, lj.a(integer));
	}

	public void a(String string, long long2) {
		this.i.put(string, lm.a(long2));
	}

	public void a(String string, UUID uUID) {
		this.i.put(string, lq.a(uUID));
	}

	public UUID a(String string) {
		return lq.a(this.c(string));
	}

	public boolean b(String string) {
		lu lu3 = this.c(string);
		return lu3 != null && lu3.b() == li.a && ((li)lu3).g().length == 4;
	}

	public void a(String string, float float2) {
		this.i.put(string, lh.a(float2));
	}

	public void a(String string, double double2) {
		this.i.put(string, lf.a(double2));
	}

	public void a(String string1, String string2) {
		this.i.put(string1, lt.a(string2));
	}

	public void a(String string, byte[] arr) {
		this.i.put(string, new lb(arr));
	}

	public void a(String string, int[] arr) {
		this.i.put(string, new li(arr));
	}

	public void b(String string, List<Integer> list) {
		this.i.put(string, new li(list));
	}

	public void a(String string, long[] arr) {
		this.i.put(string, new ll(arr));
	}

	public void c(String string, List<Long> list) {
		this.i.put(string, new ll(list));
	}

	public void a(String string, boolean boolean2) {
		this.i.put(string, lc.a(boolean2));
	}

	@Nullable
	public lu c(String string) {
		return (lu)this.i.get(string);
	}

	public byte d(String string) {
		lu lu3 = (lu)this.i.get(string);
		return lu3 == null ? 0 : lu3.a();
	}

	public boolean e(String string) {
		return this.i.containsKey(string);
	}

	public boolean c(String string, int integer) {
		int integer4 = this.d(string);
		if (integer4 == integer) {
			return true;
		} else {
			return integer != 99 ? false : integer4 == 1 || integer4 == 2 || integer4 == 3 || integer4 == 4 || integer4 == 5 || integer4 == 6;
		}
	}

	public byte f(String string) {
		try {
			if (this.c(string, 99)) {
				return ((lr)this.i.get(string)).h();
			}
		} catch (ClassCastException var3) {
		}

		return 0;
	}

	public short g(String string) {
		try {
			if (this.c(string, 99)) {
				return ((lr)this.i.get(string)).g();
			}
		} catch (ClassCastException var3) {
		}

		return 0;
	}

	public int h(String string) {
		try {
			if (this.c(string, 99)) {
				return ((lr)this.i.get(string)).f();
			}
		} catch (ClassCastException var3) {
		}

		return 0;
	}

	public long i(String string) {
		try {
			if (this.c(string, 99)) {
				return ((lr)this.i.get(string)).e();
			}
		} catch (ClassCastException var3) {
		}

		return 0L;
	}

	public float j(String string) {
		try {
			if (this.c(string, 99)) {
				return ((lr)this.i.get(string)).j();
			}
		} catch (ClassCastException var3) {
		}

		return 0.0F;
	}

	public double k(String string) {
		try {
			if (this.c(string, 99)) {
				return ((lr)this.i.get(string)).i();
			}
		} catch (ClassCastException var3) {
		}

		return 0.0;
	}

	public String l(String string) {
		try {
			if (this.c(string, 8)) {
				return ((lu)this.i.get(string)).f_();
			}
		} catch (ClassCastException var3) {
		}

		return "";
	}

	public byte[] m(String string) {
		try {
			if (this.c(string, 7)) {
				return ((lb)this.i.get(string)).d();
			}
		} catch (ClassCastException var3) {
			throw new s(this.a(string, lb.a, var3));
		}

		return new byte[0];
	}

	public int[] n(String string) {
		try {
			if (this.c(string, 11)) {
				return ((li)this.i.get(string)).g();
			}
		} catch (ClassCastException var3) {
			throw new s(this.a(string, li.a, var3));
		}

		return new int[0];
	}

	public long[] o(String string) {
		try {
			if (this.c(string, 12)) {
				return ((ll)this.i.get(string)).g();
			}
		} catch (ClassCastException var3) {
			throw new s(this.a(string, ll.a, var3));
		}

		return new long[0];
	}

	public le p(String string) {
		try {
			if (this.c(string, 10)) {
				return (le)this.i.get(string);
			}
		} catch (ClassCastException var3) {
			throw new s(this.a(string, b, var3));
		}

		return new le();
	}

	public lk d(String string, int integer) {
		try {
			if (this.d(string) == 9) {
				lk lk4 = (lk)this.i.get(string);
				if (!lk4.isEmpty() && lk4.d_() != integer) {
					return new lk();
				}

				return lk4;
			}
		} catch (ClassCastException var4) {
			throw new s(this.a(string, lk.a, var4));
		}

		return new lk();
	}

	public boolean q(String string) {
		return this.f(string) != 0;
	}

	public void r(String string) {
		this.i.remove(string);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder2 = new StringBuilder("{");
		Collection<String> collection3 = this.i.keySet();
		if (c.isDebugEnabled()) {
			List<String> list4 = Lists.<String>newArrayList(this.i.keySet());
			Collections.sort(list4);
			collection3 = list4;
		}

		for (String string5 : collection3) {
			if (stringBuilder2.length() != 1) {
				stringBuilder2.append(',');
			}

			stringBuilder2.append(s(string5)).append(':').append(this.i.get(string5));
		}

		return stringBuilder2.append('}').toString();
	}

	public boolean isEmpty() {
		return this.i.isEmpty();
	}

	private j a(String string, lw<?> lw, ClassCastException classCastException) {
		j j5 = j.a(classCastException, "Reading NBT data");
		k k6 = j5.a("Corrupt NBT tag", 1);
		k6.a("Tag type found", (l<String>)(() -> ((lu)this.i.get(string)).b().a()));
		k6.a("Tag type expected", lw::a);
		k6.a("Tag name", string);
		return j5;
	}

	public le c() {
		Map<String, lu> map2 = Maps.<String, lu>newHashMap(Maps.transformValues(this.i, lu::c));
		return new le(map2);
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof le && Objects.equals(this.i, ((le)object).i);
	}

	public int hashCode() {
		return this.i.hashCode();
	}

	private static void a(String string, lu lu, DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(lu.a());
		if (lu.a() != 0) {
			dataOutput.writeUTF(string);
			lu.a(dataOutput);
		}
	}

	private static byte c(DataInput dataInput, ln ln) throws IOException {
		return dataInput.readByte();
	}

	private static String d(DataInput dataInput, ln ln) throws IOException {
		return dataInput.readUTF();
	}

	private static lu b(lw<?> lw, String string, DataInput dataInput, int integer, ln ln) {
		try {
			return lw.b(dataInput, integer, ln);
		} catch (IOException var8) {
			j j7 = j.a(var8, "Loading NBT data");
			k k8 = j7.a("NBT Tag");
			k8.a("Tag name", string);
			k8.a("Tag type", lw.a());
			throw new s(j7);
		}
	}

	public le a(le le) {
		for (String string4 : le.i.keySet()) {
			lu lu5 = (lu)le.i.get(string4);
			if (lu5.a() == 10) {
				if (this.c(string4, 10)) {
					le le6 = this.p(string4);
					le6.a((le)lu5);
				} else {
					this.a(string4, lu5.c());
				}
			} else {
				this.a(string4, lu5.c());
			}
		}

		return this;
	}

	protected static String s(String string) {
		return h.matcher(string).matches() ? string : lt.b(string);
	}

	protected static mr t(String string) {
		if (h.matcher(string).matches()) {
			return new nd(string).a(d);
		} else {
			String string2 = lt.b(string);
			String string3 = string2.substring(0, 1);
			mr mr4 = new nd(string2.substring(1, string2.length() - 1)).a(d);
			return new nd(string3).a(mr4).c(string3);
		}
	}

	@Override
	public mr a(String string, int integer) {
		if (this.i.isEmpty()) {
			return new nd("{}");
		} else {
			mx mx4 = new nd("{");
			Collection<String> collection5 = this.i.keySet();
			if (c.isDebugEnabled()) {
				List<String> list6 = Lists.<String>newArrayList(this.i.keySet());
				Collections.sort(list6);
				collection5 = list6;
			}

			if (!string.isEmpty()) {
				mx4.c("\n");
			}

			Iterator<String> iterator6 = collection5.iterator();

			while (iterator6.hasNext()) {
				String string7 = (String)iterator6.next();
				mx mx8 = new nd(Strings.repeat(string, integer + 1)).a(t(string7)).c(String.valueOf(':')).c(" ").a(((lu)this.i.get(string7)).a(string, integer + 1));
				if (iterator6.hasNext()) {
					mx8.c(String.valueOf(',')).c(string.isEmpty() ? " " : "\n");
				}

				mx4.a(mx8);
			}

			if (!string.isEmpty()) {
				mx4.c("\n").c(Strings.repeat(string, integer));
			}

			mx4.c("}");
			return mx4;
		}
	}

	protected Map<String, lu> h() {
		return Collections.unmodifiableMap(this.i);
	}
}
