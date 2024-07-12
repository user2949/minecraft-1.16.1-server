import com.google.common.collect.Maps;
import java.util.Map;

public interface bdu {
	bdu a = a("desert");
	bdu b = a("jungle");
	bdu c = a("plains");
	bdu d = a("savanna");
	bdu e = a("snow");
	bdu f = a("swamp");
	bdu g = a("taiga");
	Map<bre, bdu> h = v.a(Maps.<bre, bdu>newHashMap(), hashMap -> {
		hashMap.put(brk.M, a);
		hashMap.put(brk.O, a);
		hashMap.put(brk.d, a);
		hashMap.put(brk.s, a);
		hashMap.put(brk.ac, a);
		hashMap.put(brk.at, a);
		hashMap.put(brk.av, a);
		hashMap.put(brk.au, a);
		hashMap.put(brk.N, a);
		hashMap.put(brk.aw, b);
		hashMap.put(brk.ax, b);
		hashMap.put(brk.w, b);
		hashMap.put(brk.y, b);
		hashMap.put(brk.x, b);
		hashMap.put(brk.ai, b);
		hashMap.put(brk.aj, b);
		hashMap.put(brk.L, d);
		hashMap.put(brk.K, d);
		hashMap.put(brk.ar, d);
		hashMap.put(brk.as, d);
		hashMap.put(brk.Z, e);
		hashMap.put(brk.l, e);
		hashMap.put(brk.m, e);
		hashMap.put(brk.ah, e);
		hashMap.put(brk.B, e);
		hashMap.put(brk.o, e);
		hashMap.put(brk.F, e);
		hashMap.put(brk.G, e);
		hashMap.put(brk.an, e);
		hashMap.put(brk.n, e);
		hashMap.put(brk.h, f);
		hashMap.put(brk.ag, f);
		hashMap.put(brk.ao, g);
		hashMap.put(brk.ap, g);
		hashMap.put(brk.H, g);
		hashMap.put(brk.I, g);
		hashMap.put(brk.ad, g);
		hashMap.put(brk.aq, g);
		hashMap.put(brk.v, g);
		hashMap.put(brk.e, g);
		hashMap.put(brk.g, g);
		hashMap.put(brk.u, g);
		hashMap.put(brk.af, g);
		hashMap.put(brk.J, g);
	});

	static bdu a(String string) {
		return gl.a(gl.aR, new uh(string), new bdu() {
			public String toString() {
				return string;
			}
		});
	}

	static bdu a(bre bre) {
		return (bdu)h.getOrDefault(bre, c);
	}
}
