import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class bki {
	public static final Codec<bki> a = RecordCodecBuilder.create(
		instance -> instance.group(
					gl.am.fieldOf("id").forGetter(bki -> bki.h),
					Codec.INT.fieldOf("Count").forGetter(bki -> bki.f),
					le.a.optionalFieldOf("tag").forGetter(bki -> Optional.ofNullable(bki.i))
				)
				.apply(instance, bki::new)
	);
	private static final Logger d = LogManager.getLogger();
	public static final bki b = new bki((bke)null);
	public static final DecimalFormat c = v.a(
		new DecimalFormat("#.##"), decimalFormat -> decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
	);
	private static final nb e = nb.b.a(i.DARK_PURPLE).b(true);
	private int f;
	private int g;
	@Deprecated
	private final bke h;
	private le i;
	private boolean j;
	private aom k;
	private cfn l;
	private boolean m;
	private cfn n;
	private boolean o;

	public bki(bqa bqa) {
		this(bqa, 1);
	}

	private bki(bqa bqa, int integer, Optional<le> optional) {
		this(bqa, integer);
		optional.ifPresent(this::c);
	}

	public bki(bqa bqa, int integer) {
		this.h = bqa == null ? null : bqa.h();
		this.f = integer;
		if (this.h != null && this.h.k()) {
			this.b(this.g());
		}

		this.I();
	}

	private void I() {
		this.j = false;
		this.j = this.a();
	}

	private bki(le le) {
		this.h = gl.am.a(new uh(le.l("id")));
		this.f = le.f("Count");
		if (le.c("tag", 10)) {
			this.i = le.p("tag");
			this.b().b(le);
		}

		if (this.b().k()) {
			this.b(this.g());
		}

		this.I();
	}

	public static bki a(le le) {
		try {
			return new bki(le);
		} catch (RuntimeException var2) {
			d.debug("Tried to load invalid item: {}", le, var2);
			return b;
		}
	}

	public boolean a() {
		if (this == b) {
			return true;
		} else {
			return this.b() == null || this.b() == bkk.a ? true : this.f <= 0;
		}
	}

	public bki a(int integer) {
		int integer3 = Math.min(integer, this.f);
		bki bki4 = this.i();
		bki4.e(integer3);
		this.g(integer3);
		return bki4;
	}

	public bke b() {
		return this.j ? bkk.a : this.h;
	}

	public ang a(blv blv) {
		bec bec3 = blv.m();
		fu fu4 = blv.a();
		cfn cfn5 = new cfn(blv.o(), fu4, false);
		if (bec3 != null && !bec3.bJ.e && !this.b(blv.o().p(), cfn5)) {
			return ang.PASS;
		} else {
			bke bke6 = this.b();
			ang ang7 = bke6.a(blv);
			if (bec3 != null && ang7.a()) {
				bec3.b(acu.c.b(bke6));
			}

			return ang7;
		}
	}

	public float a(cfj cfj) {
		return this.b().a(this, cfj);
	}

	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		return this.b().a(bqb, bec, anf);
	}

	public bki a(bqb bqb, aoy aoy) {
		return this.b().a(this, bqb, aoy);
	}

	public le b(le le) {
		uh uh3 = gl.am.b(this.b());
		le.a("id", uh3 == null ? "minecraft:air" : uh3.toString());
		le.a("Count", (byte)this.f);
		if (this.i != null) {
			le.a("tag", this.i.g());
		}

		return le;
	}

	public int c() {
		return this.b().i();
	}

	public boolean d() {
		return this.c() > 1 && (!this.e() || !this.f());
	}

	public boolean e() {
		if (!this.j && this.b().j() > 0) {
			le le2 = this.o();
			return le2 == null || !le2.q("Unbreakable");
		} else {
			return false;
		}
	}

	public boolean f() {
		return this.e() && this.g() > 0;
	}

	public int g() {
		return this.i == null ? 0 : this.i.h("Damage");
	}

	public void b(int integer) {
		this.p().b("Damage", Math.max(0, integer));
	}

	public int h() {
		return this.b().j();
	}

	public boolean a(int integer, Random random, @Nullable ze ze) {
		if (!this.e()) {
			return false;
		} else {
			if (integer > 0) {
				int integer5 = bny.a(boa.v, this);
				int integer6 = 0;

				for (int integer7 = 0; integer5 > 0 && integer7 < integer; integer7++) {
					if (bnu.a(this, integer5, random)) {
						integer6++;
					}
				}

				integer -= integer6;
				if (integer <= 0) {
					return false;
				}
			}

			if (ze != null && integer != 0) {
				aa.t.a(ze, this, this.g() + integer);
			}

			int integer5 = this.g() + integer;
			this.b(integer5);
			return integer5 >= this.h();
		}
	}

	public <T extends aoy> void a(int integer, T aoy, Consumer<T> consumer) {
		if (!aoy.l.v && (!(aoy instanceof bec) || !((bec)aoy).bJ.d)) {
			if (this.e()) {
				if (this.a(integer, aoy.cX(), aoy instanceof ze ? (ze)aoy : null)) {
					consumer.accept(aoy);
					bke bke5 = this.b();
					this.g(1);
					if (aoy instanceof bec) {
						((bec)aoy).b(acu.d.b(bke5));
					}

					this.b(0);
				}
			}
		}
	}

	public void a(aoy aoy, bec bec) {
		bke bke4 = this.b();
		if (bke4.a(this, aoy, bec)) {
			bec.b(acu.c.b(bke4));
		}
	}

	public void a(bqb bqb, cfj cfj, fu fu, bec bec) {
		bke bke6 = this.b();
		if (bke6.a(this, bqb, cfj, fu, bec)) {
			bec.b(acu.c.b(bke6));
		}
	}

	public boolean b(cfj cfj) {
		return this.b().b(cfj);
	}

	public ang a(bec bec, aoy aoy, anf anf) {
		return this.b().a(this, bec, aoy, anf);
	}

	public bki i() {
		if (this.a()) {
			return b;
		} else {
			bki bki2 = new bki(this.b(), this.f);
			bki2.d(this.D());
			if (this.i != null) {
				bki2.i = this.i.g();
			}

			return bki2;
		}
	}

	public static boolean a(bki bki1, bki bki2) {
		if (bki1.a() && bki2.a()) {
			return true;
		} else if (bki1.a() || bki2.a()) {
			return false;
		} else {
			return bki1.i == null && bki2.i != null ? false : bki1.i == null || bki1.i.equals(bki2.i);
		}
	}

	public static boolean b(bki bki1, bki bki2) {
		if (bki1.a() && bki2.a()) {
			return true;
		} else {
			return !bki1.a() && !bki2.a() ? bki1.c(bki2) : false;
		}
	}

	private boolean c(bki bki) {
		if (this.f != bki.f) {
			return false;
		} else if (this.b() != bki.b()) {
			return false;
		} else {
			return this.i == null && bki.i != null ? false : this.i == null || this.i.equals(bki.i);
		}
	}

	public static boolean c(bki bki1, bki bki2) {
		if (bki1 == bki2) {
			return true;
		} else {
			return !bki1.a() && !bki2.a() ? bki1.a(bki2) : false;
		}
	}

	public static boolean d(bki bki1, bki bki2) {
		if (bki1 == bki2) {
			return true;
		} else {
			return !bki1.a() && !bki2.a() ? bki1.b(bki2) : false;
		}
	}

	public boolean a(bki bki) {
		return !bki.a() && this.b() == bki.b();
	}

	public boolean b(bki bki) {
		return !this.e() ? this.a(bki) : !bki.a() && this.b() == bki.b();
	}

	public String j() {
		return this.b().f(this);
	}

	public String toString() {
		return this.f + " " + this.b();
	}

	public void a(bqb bqb, aom aom, int integer, boolean boolean4) {
		if (this.g > 0) {
			this.g--;
		}

		if (this.b() != null) {
			this.b().a(this, bqb, aom, integer, boolean4);
		}
	}

	public void a(bqb bqb, bec bec, int integer) {
		bec.a(acu.b.b(this.b()), integer);
		this.b().b(this, bqb, bec);
	}

	public int k() {
		return this.b().e_(this);
	}

	public blu l() {
		return this.b().d_(this);
	}

	public void a(bqb bqb, aoy aoy, int integer) {
		this.b().a(this, bqb, aoy, integer);
	}

	public boolean m() {
		return this.b().j(this);
	}

	public boolean n() {
		return !this.j && this.i != null && !this.i.isEmpty();
	}

	@Nullable
	public le o() {
		return this.i;
	}

	public le p() {
		if (this.i == null) {
			this.c(new le());
		}

		return this.i;
	}

	public le a(String string) {
		if (this.i != null && this.i.c(string, 10)) {
			return this.i.p(string);
		} else {
			le le3 = new le();
			this.a(string, le3);
			return le3;
		}
	}

	@Nullable
	public le b(String string) {
		return this.i != null && this.i.c(string, 10) ? this.i.p(string) : null;
	}

	public void c(String string) {
		if (this.i != null && this.i.e(string)) {
			this.i.r(string);
			if (this.i.isEmpty()) {
				this.i = null;
			}
		}
	}

	public lk q() {
		return this.i != null ? this.i.d("Enchantments", 10) : new lk();
	}

	public void c(@Nullable le le) {
		this.i = le;
		if (this.b().k()) {
			this.b(this.g());
		}
	}

	public mr r() {
		le le2 = this.b("display");
		if (le2 != null && le2.c("Name", 8)) {
			try {
				mr mr3 = mr.a.a(le2.l("Name"));
				if (mr3 != null) {
					return mr3;
				}

				le2.r("Name");
			} catch (JsonParseException var3) {
				le2.r("Name");
			}
		}

		return this.b().h(this);
	}

	public bki a(@Nullable mr mr) {
		le le3 = this.a("display");
		if (mr != null) {
			le3.a("Name", mr.a.a(mr));
		} else {
			le3.r("Name");
		}

		return this;
	}

	public void s() {
		le le2 = this.b("display");
		if (le2 != null) {
			le2.r("Name");
			if (le2.isEmpty()) {
				this.c("display");
			}
		}

		if (this.i != null && this.i.isEmpty()) {
			this.i = null;
		}
	}

	public boolean t() {
		le le2 = this.b("display");
		return le2 != null && le2.c("Name", 8);
	}

	public boolean u() {
		return this.b().e(this);
	}

	public bkw v() {
		return this.b().i(this);
	}

	public boolean w() {
		return !this.b().f_(this) ? false : !this.x();
	}

	public void a(bnw bnw, int integer) {
		this.p();
		if (!this.i.c("Enchantments", 9)) {
			this.i.a("Enchantments", new lk());
		}

		lk lk4 = this.i.d("Enchantments", 10);
		le le5 = new le();
		le5.a("id", String.valueOf(gl.ak.b(bnw)));
		le5.a("lvl", (short)((byte)integer));
		lk4.add(le5);
	}

	public boolean x() {
		return this.i != null && this.i.c("Enchantments", 9) ? !this.i.d("Enchantments", 10).isEmpty() : false;
	}

	public void a(String string, lu lu) {
		this.p().a(string, lu);
	}

	public boolean y() {
		return this.k instanceof bba;
	}

	public void a(@Nullable aom aom) {
		this.k = aom;
	}

	@Nullable
	public bba z() {
		return this.k instanceof bba ? (bba)this.A() : null;
	}

	@Nullable
	public aom A() {
		return !this.j ? this.k : null;
	}

	public int B() {
		return this.n() && this.i.c("RepairCost", 3) ? this.i.h("RepairCost") : 0;
	}

	public void c(int integer) {
		this.p().b("RepairCost", integer);
	}

	public Multimap<aps, apv> a(aor aor) {
		Multimap<aps, apv> multimap3;
		if (this.n() && this.i.c("AttributeModifiers", 9)) {
			multimap3 = HashMultimap.create();
			lk lk4 = this.i.d("AttributeModifiers", 10);

			for (int integer5 = 0; integer5 < lk4.size(); integer5++) {
				le le6 = lk4.a(integer5);
				if (!le6.c("Slot", 8) || le6.l("Slot").equals(aor.d())) {
					Optional<aps> optional7 = gl.aP.b(uh.a(le6.l("AttributeName")));
					if (optional7.isPresent()) {
						apv apv8 = apv.a(le6);
						if (apv8 != null && apv8.a().getLeastSignificantBits() != 0L && apv8.a().getMostSignificantBits() != 0L) {
							multimap3.put((aps)optional7.get(), apv8);
						}
					}
				}
			}
		} else {
			multimap3 = this.b().a(aor);
		}

		return multimap3;
	}

	public void a(aps aps, apv apv, @Nullable aor aor) {
		this.p();
		if (!this.i.c("AttributeModifiers", 9)) {
			this.i.a("AttributeModifiers", new lk());
		}

		lk lk5 = this.i.d("AttributeModifiers", 10);
		le le6 = apv.e();
		le6.a("AttributeName", gl.aP.b(aps).toString());
		if (aor != null) {
			le6.a("Slot", aor.d());
		}

		lk5.add(le6);
	}

	public mr C() {
		mx mx2 = new nd("").a(this.r());
		if (this.t()) {
			mx2.a(i.ITALIC);
		}

		mx mx3 = ms.a((mr)mx2);
		if (!this.j) {
			mx3.a(this.v().e).a(nb -> nb.a(new mv(mv.a.b, new mv.c(this))));
		}

		return mx3;
	}

	private static boolean a(cfn cfn1, @Nullable cfn cfn2) {
		if (cfn2 == null || cfn1.a() != cfn2.a()) {
			return false;
		} else if (cfn1.b() == null && cfn2.b() == null) {
			return true;
		} else {
			return cfn1.b() != null && cfn2.b() != null ? Objects.equals(cfn1.b().a(new le()), cfn2.b().a(new le())) : false;
		}
	}

	public boolean a(adh adh, cfn cfn) {
		if (a(cfn, this.l)) {
			return this.m;
		} else {
			this.l = cfn;
			if (this.n() && this.i.c("CanDestroy", 9)) {
				lk lk4 = this.i.d("CanDestroy", 8);

				for (int integer5 = 0; integer5 < lk4.size(); integer5++) {
					String string6 = lk4.j(integer5);

					try {
						Predicate<cfn> predicate7 = ed.a().a(new StringReader(string6)).create(adh);
						if (predicate7.test(cfn)) {
							this.m = true;
							return true;
						}
					} catch (CommandSyntaxException var7) {
					}
				}
			}

			this.m = false;
			return false;
		}
	}

	public boolean b(adh adh, cfn cfn) {
		if (a(cfn, this.n)) {
			return this.o;
		} else {
			this.n = cfn;
			if (this.n() && this.i.c("CanPlaceOn", 9)) {
				lk lk4 = this.i.d("CanPlaceOn", 8);

				for (int integer5 = 0; integer5 < lk4.size(); integer5++) {
					String string6 = lk4.j(integer5);

					try {
						Predicate<cfn> predicate7 = ed.a().a(new StringReader(string6)).create(adh);
						if (predicate7.test(cfn)) {
							this.o = true;
							return true;
						}
					} catch (CommandSyntaxException var7) {
					}
				}
			}

			this.o = false;
			return false;
		}
	}

	public int D() {
		return this.g;
	}

	public void d(int integer) {
		this.g = integer;
	}

	public int E() {
		return this.j ? 0 : this.f;
	}

	public void e(int integer) {
		this.f = integer;
		this.I();
	}

	public void f(int integer) {
		this.e(this.f + integer);
	}

	public void g(int integer) {
		this.f(-integer);
	}

	public void b(bqb bqb, aoy aoy, int integer) {
		this.b().a(bqb, aoy, this, integer);
	}

	public boolean F() {
		return this.b().s();
	}

	public ack G() {
		return this.b().ag_();
	}

	public ack H() {
		return this.b().af_();
	}
}
