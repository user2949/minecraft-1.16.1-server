import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;

public abstract class brh implements brg.a {
	public static final Codec<brh> a = gl.aA.dispatchStable(brh::a, Function.identity());
	private static final List<bre> e = Lists.<bre>newArrayList(brk.f, brk.c, brk.g, brk.u, brk.t, brk.w, brk.x);
	protected final Map<cml<?>, Boolean> b = Maps.<cml<?>, Boolean>newHashMap();
	protected final Set<cfj> c = Sets.<cfj>newHashSet();
	protected final List<bre> d;

	protected brh(List<bre> list) {
		this.d = list;
	}

	protected abstract Codec<? extends brh> a();

	public List<bre> b() {
		return e;
	}

	public List<bre> c() {
		return this.d;
	}

	public Set<bre> a(int integer1, int integer2, int integer3, int integer4) {
		int integer6 = integer1 - integer4 >> 2;
		int integer7 = integer2 - integer4 >> 2;
		int integer8 = integer3 - integer4 >> 2;
		int integer9 = integer1 + integer4 >> 2;
		int integer10 = integer2 + integer4 >> 2;
		int integer11 = integer3 + integer4 >> 2;
		int integer12 = integer9 - integer6 + 1;
		int integer13 = integer10 - integer7 + 1;
		int integer14 = integer11 - integer8 + 1;
		Set<bre> set15 = Sets.<bre>newHashSet();

		for (int integer16 = 0; integer16 < integer14; integer16++) {
			for (int integer17 = 0; integer17 < integer12; integer17++) {
				for (int integer18 = 0; integer18 < integer13; integer18++) {
					int integer19 = integer6 + integer17;
					int integer20 = integer7 + integer18;
					int integer21 = integer8 + integer16;
					set15.add(this.b(integer19, integer20, integer21));
				}
			}
		}

		return set15;
	}

	@Nullable
	public fu a(int integer1, int integer2, int integer3, int integer4, List<bre> list, Random random) {
		return this.a(integer1, integer2, integer3, integer4, 1, list, random, false);
	}

	@Nullable
	public fu a(int integer1, int integer2, int integer3, int integer4, int integer5, List<bre> list, Random random, boolean boolean8) {
		int integer10 = integer1 >> 2;
		int integer11 = integer3 >> 2;
		int integer12 = integer4 >> 2;
		int integer13 = integer2 >> 2;
		fu fu14 = null;
		int integer15 = 0;
		int integer16 = boolean8 ? 0 : integer12;
		int integer17 = integer16;

		while (integer17 <= integer12) {
			for (int integer18 = -integer17; integer18 <= integer17; integer18 += integer5) {
				boolean boolean19 = Math.abs(integer18) == integer17;

				for (int integer20 = -integer17; integer20 <= integer17; integer20 += integer5) {
					if (boolean8) {
						boolean boolean21 = Math.abs(integer20) == integer17;
						if (!boolean21 && !boolean19) {
							continue;
						}
					}

					int integer21 = integer10 + integer20;
					int integer22 = integer11 + integer18;
					if (list.contains(this.b(integer21, integer13, integer22))) {
						if (fu14 == null || random.nextInt(integer15 + 1) == 0) {
							fu14 = new fu(integer21 << 2, integer2, integer22 << 2);
							if (boolean8) {
								return fu14;
							}
						}

						integer15++;
					}
				}
			}

			integer17 += integer5;
		}

		return fu14;
	}

	public boolean a(cml<?> cml) {
		return (Boolean)this.b.computeIfAbsent(cml, cmlx -> this.d.stream().anyMatch(bre -> bre.a(cmlx)));
	}

	public Set<cfj> d() {
		if (this.c.isEmpty()) {
			for (bre bre3 : this.d) {
				this.c.add(bre3.A().a());
			}
		}

		return this.c;
	}

	static {
		gl.a(gl.aA, "fixed", bse.e);
		gl.a(gl.aA, "multi_noise", btc.f);
		gl.a(gl.aA, "checkerboard", brn.e);
		gl.a(gl.aA, "vanilla_layered", bti.e);
		gl.a(gl.aA, "the_end", buh.e);
	}
}
