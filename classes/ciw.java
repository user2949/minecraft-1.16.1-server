import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;

public class ciw {
	public static final Codec<ciw> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cos.a.optionalFieldOf("stronghold").forGetter(ciw -> Optional.ofNullable(ciw.e)),
					Codec.simpleMap(gl.aG, cot.a, gl.aG).fieldOf("structures").forGetter(ciw -> ciw.d)
				)
				.apply(instance, ciw::new)
	);
	public static final ImmutableMap<cml<?>, cot> b = ImmutableMap.<cml<?>, cot>builder()
		.put(cml.q, new cot(32, 8, 10387312))
		.put(cml.f, new cot(32, 8, 14357617))
		.put(cml.g, new cot(32, 8, 14357618))
		.put(cml.e, new cot(32, 8, 14357619))
		.put(cml.j, new cot(32, 8, 14357620))
		.put(cml.b, new cot(32, 8, 165745296))
		.put(cml.k, new cot(1, 0, 0))
		.put(cml.l, new cot(32, 5, 10387313))
		.put(cml.o, new cot(20, 11, 10387313))
		.put(cml.d, new cot(80, 20, 10387319))
		.put(cml.p, new cot(1, 0, 0))
		.put(cml.c, new cot(1, 0, 0))
		.put(cml.h, new cot(40, 15, 34222645))
		.put(cml.i, new cot(24, 4, 165745295))
		.put(cml.m, new cot(20, 8, 14357621))
		.put(cml.s, new cot(27, 4, 30084232))
		.put(cml.n, new cot(27, 4, 30084232))
		.put(cml.r, new cot(2, 1, 14357921))
		.build();
	public static final cos c;
	private final Map<cml<?>, cot> d;
	@Nullable
	private final cos e;

	public ciw(Optional<cos> optional, Map<cml<?>, cot> map) {
		this.e = (cos)optional.orElse(null);
		this.d = map;
	}

	public ciw(boolean boolean1) {
		this.d = Maps.<cml<?>, cot>newHashMap(b);
		this.e = boolean1 ? c : null;
	}

	public Map<cml<?>, cot> a() {
		return this.d;
	}

	public cot a(cml<?> cml) {
		return (cot)this.d.getOrDefault(cml, new cot(1, 0, 0));
	}

	@Nullable
	public cos b() {
		return this.e;
	}

	static {
		for (cml<?> cml2 : gl.aG) {
			if (!b.containsKey(cml2)) {
				throw new IllegalStateException("Structure feature without default settings: " + gl.aG.b(cml2));
			}
		}

		c = new cos(32, 3, 128);
	}
}
