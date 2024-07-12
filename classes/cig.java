import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;

public final class cig {
	public static final MapCodec<cig> a = RecordCodecBuilder.mapCodec(
		instance -> instance.group(cif.k.fieldOf("type").forGetter(cig::a), cha.a.fieldOf("generator").forGetter(cig::c)).apply(instance, instance.stable(cig::new))
	);
	public static final ug<cig> b = ug.a(gl.af, new uh("overworld"));
	public static final ug<cig> c = ug.a(gl.af, new uh("the_nether"));
	public static final ug<cig> d = ug.a(gl.af, new uh("the_end"));
	private static final LinkedHashSet<ug<cig>> e = Sets.newLinkedHashSet(ImmutableList.of(b, c, d));
	private final Supplier<cif> f;
	private final cha g;

	public cig(Supplier<cif> supplier, cha cha) {
		this.f = supplier;
		this.g = cha;
	}

	public Supplier<cif> a() {
		return this.f;
	}

	public cif b() {
		return (cif)this.f.get();
	}

	public cha c() {
		return this.g;
	}

	public static gh<cig> a(gh<cig> gh) {
		gh<cig> gh2 = new gh<>(gl.af, Lifecycle.experimental());

		for (ug<cig> ug4 : e) {
			cig cig5 = gh.a(ug4);
			if (cig5 != null) {
				gh2.a(ug4, cig5);
				if (gh.c(ug4)) {
					gh2.d(ug4);
				}
			}
		}

		for (Entry<ug<cig>, cig> entry4 : gh.c()) {
			ug<cig> ug5 = (ug<cig>)entry4.getKey();
			if (!e.contains(ug5)) {
				gh2.a(ug5, entry4.getValue());
				if (gh.c(ug5)) {
					gh2.d(ug5);
				}
			}
		}

		return gh2;
	}

	public static boolean a(long long1, gh<cig> gh) {
		List<Entry<ug<cig>, cig>> list4 = Lists.<Entry<ug<cig>, cig>>newArrayList(gh.c());
		if (list4.size() != e.size()) {
			return false;
		} else {
			Entry<ug<cig>, cig> entry5 = (Entry<ug<cig>, cig>)list4.get(0);
			Entry<ug<cig>, cig> entry6 = (Entry<ug<cig>, cig>)list4.get(1);
			Entry<ug<cig>, cig> entry7 = (Entry<ug<cig>, cig>)list4.get(2);
			if (entry5.getKey() != b || entry6.getKey() != c || entry7.getKey() != d) {
				return false;
			} else if (((cig)entry5.getValue()).b() != cif.f && ((cig)entry5.getValue()).b() != cif.j) {
				return false;
			} else if (((cig)entry6.getValue()).b() != cif.g) {
				return false;
			} else if (((cig)entry7.getValue()).b() != cif.h) {
				return false;
			} else if (((cig)entry6.getValue()).c() instanceof cip && ((cig)entry7.getValue()).c() instanceof cip) {
				cip cip8 = (cip)((cig)entry6.getValue()).c();
				cip cip9 = (cip)((cig)entry7.getValue()).c();
				if (!cip8.a(long1, ciq.a.d)) {
					return false;
				} else if (!cip9.a(long1, ciq.a.e)) {
					return false;
				} else if (!(cip8.d() instanceof btc)) {
					return false;
				} else {
					btc btc10 = (btc)cip8.d();
					if (!btc10.b(long1)) {
						return false;
					} else if (!(cip9.d() instanceof buh)) {
						return false;
					} else {
						buh buh11 = (buh)cip9.d();
						return buh11.b(long1);
					}
				}
			} else {
				return false;
			}
		}
	}
}
