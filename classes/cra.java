import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cra {
	private static final Logger b = LogManager.getLogger();
	public static final Codec<cra> a = RecordCodecBuilder.<cra>create(
			instance -> instance.group(
						ciw.a.fieldOf("structures").forGetter(cra::d),
						cqz.a.listOf().fieldOf("layers").forGetter(cra::f),
						Codec.BOOL.fieldOf("lakes").withDefault(false).forGetter(cra -> cra.l),
						Codec.BOOL.fieldOf("features").withDefault(false).forGetter(cra -> cra.k),
						adl.a(gl.as.fieldOf("biome"), v.a("Unknown biome, defaulting to plains", b::error), () -> brk.c).forGetter(cra -> cra.h)
					)
					.apply(instance, cra::new)
		)
		.stable();
	private static final ckb<?, ?> c = ckt.y.b(new cng(bvs.A.n())).a(csc.E.a(new crf(4)));
	private static final ckb<?, ?> d = ckt.y.b(new cng(bvs.B.n())).a(csc.D.a(new crf(80)));
	private static final Map<cml<?>, ckc<?, ?>> e = v.a(Maps.<cml<?>, ckc<?, ?>>newHashMap(), hashMap -> {
		hashMap.put(cml.c, brf.b);
		hashMap.put(cml.q, brf.t);
		hashMap.put(cml.k, brf.k);
		hashMap.put(cml.j, brf.j);
		hashMap.put(cml.f, brf.f);
		hashMap.put(cml.e, brf.e);
		hashMap.put(cml.g, brf.g);
		hashMap.put(cml.m, brf.m);
		hashMap.put(cml.i, brf.h);
		hashMap.put(cml.l, brf.l);
		hashMap.put(cml.o, brf.q);
		hashMap.put(cml.d, brf.d);
		hashMap.put(cml.n, brf.o);
		hashMap.put(cml.b, brf.a);
		hashMap.put(cml.h, brf.y);
		hashMap.put(cml.s, brf.s);
	});
	private final ciw f;
	private final List<cqz> g = Lists.<cqz>newArrayList();
	private bre h;
	private final cfj[] i = new cfj[256];
	private boolean j;
	private boolean k = false;
	private boolean l = false;

	public cra(ciw ciw, List<cqz> list, boolean boolean3, boolean boolean4, bre bre) {
		this(ciw);
		if (boolean3) {
			this.b();
		}

		if (boolean4) {
			this.a();
		}

		this.g.addAll(list);
		this.h();
		this.h = bre;
	}

	public cra(ciw ciw) {
		this.f = ciw;
	}

	public void a() {
		this.k = true;
	}

	public void b() {
		this.l = true;
	}

	public bre c() {
		bre bre2 = this.e();
		bre bre3 = new bre(new bre.a().a(bre2.z()).a(bre2.d()).a(bre2.y()).a(bre2.k()).b(bre2.o()).c(bre2.p()).d(bre2.l()).a(bre2.q()).a(bre2.C())) {
		};
		if (this.l) {
			bre3.a(cin.b.LAKES, c);
			bre3.a(cin.b.LAKES, d);
		}

		for (Entry<cml<?>, cot> entry5 : this.f.a().entrySet()) {
			bre3.a(bre2.b((ckc<?, ?>)e.get(entry5.getKey())));
		}

		boolean boolean4 = (!this.j || bre2 == brk.aa) && this.k;
		if (boolean4) {
			List<cin.b> list5 = Lists.<cin.b>newArrayList();
			list5.add(cin.b.UNDERGROUND_STRUCTURES);
			list5.add(cin.b.SURFACE_STRUCTURES);

			for (cin.b b9 : cin.b.values()) {
				if (!list5.contains(b9)) {
					for (ckb<?, ?> ckb11 : bre2.a(b9)) {
						bre3.a(b9, ckb11);
					}
				}
			}
		}

		cfj[] arr5 = this.g();

		for (int integer6 = 0; integer6 < arr5.length; integer6++) {
			cfj cfj7 = arr5[integer6];
			if (cfj7 != null && !cio.a.MOTION_BLOCKING.e().test(cfj7)) {
				this.i[integer6] = null;
				bre3.a(cin.b.TOP_LAYER_MODIFICATION, ckt.S.b(new cnv(integer6, cfj7)));
			}
		}

		return bre3;
	}

	public ciw d() {
		return this.f;
	}

	public bre e() {
		return this.h;
	}

	public void a(bre bre) {
		this.h = bre;
	}

	public List<cqz> f() {
		return this.g;
	}

	public cfj[] g() {
		return this.i;
	}

	public void h() {
		Arrays.fill(this.i, 0, this.i.length, null);
		int integer2 = 0;

		for (cqz cqz4 : this.g) {
			cqz4.a(integer2);
			integer2 += cqz4.a();
		}

		this.j = true;

		for (cqz cqz3 : this.g) {
			for (int integer4 = cqz3.c(); integer4 < cqz3.c() + cqz3.a(); integer4++) {
				cfj cfj5 = cqz3.b();
				if (!cfj5.a(bvs.a)) {
					this.j = false;
					this.i[integer4] = cfj5;
				}
			}
		}
	}

	public static cra i() {
		ciw ciw1 = new ciw(Optional.of(ciw.c), Maps.<cml<?>, cot>newHashMap(ImmutableMap.of(cml.q, ciw.b.get(cml.q))));
		cra cra2 = new cra(ciw1);
		cra2.a(brk.c);
		cra2.f().add(new cqz(1, bvs.z));
		cra2.f().add(new cqz(2, bvs.j));
		cra2.f().add(new cqz(1, bvs.i));
		cra2.h();
		return cra2;
	}
}
