import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cix {
	public static final Codec<cix> a = RecordCodecBuilder.create(
			instance -> instance.group(
						Codec.LONG.fieldOf("seed").stable().forGetter(cix::b),
						Codec.BOOL.fieldOf("generate_features").withDefault(true).stable().forGetter(cix::c),
						Codec.BOOL.fieldOf("bonus_chest").withDefault(false).stable().forGetter(cix::d),
						gh.b(gl.af, Lifecycle.stable(), cig.a).xmap(cig::a, Function.identity()).fieldOf("dimensions").forGetter(cix::e),
						Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(cix -> cix.i)
					)
					.apply(instance, instance.stable(cix::new))
		)
		.comapFlatMap(cix::n, Function.identity());
	private static final Logger c = LogManager.getLogger();
	private static final int d = "North Carolina".hashCode();
	public static final cix b = new cix((long)d, true, true, a(cif.a((long)d), a((long)d)));
	private final long e;
	private final boolean f;
	private final boolean g;
	private final gh<cig> h;
	private final Optional<String> i;

	private DataResult<cix> n() {
		return this.o() ? DataResult.success(this, Lifecycle.stable()) : DataResult.success(this);
	}

	private boolean o() {
		return cig.a(this.e, this.h);
	}

	public cix(long long1, boolean boolean2, boolean boolean3, gh<cig> gh) {
		this(long1, boolean2, boolean3, gh, Optional.empty());
	}

	private cix(long long1, boolean boolean2, boolean boolean3, gh<cig> gh, Optional<String> optional) {
		this.e = long1;
		this.f = boolean2;
		this.g = boolean3;
		this.h = gh;
		this.i = optional;
	}

	public static cix a() {
		long long1 = new Random().nextLong();
		return new cix(long1, true, false, a(cif.a(long1), a(long1)));
	}

	public static cip a(long long1) {
		return new cip(new bti(long1, false, false), long1, ciq.a.b.b());
	}

	public long b() {
		return this.e;
	}

	public boolean c() {
		return this.f;
	}

	public boolean d() {
		return this.g;
	}

	public static gh<cig> a(gh<cig> gh, cha cha) {
		cig cig3 = gh.a(cig.b);
		Supplier<cif> supplier4 = () -> cig3 == null ? cif.a() : cig3.b();
		return a(gh, supplier4, cha);
	}

	public static gh<cig> a(gh<cig> gh, Supplier<cif> supplier, cha cha) {
		gh<cig> gh4 = new gh<>(gl.af, Lifecycle.experimental());
		gh4.a(cig.b, new cig(supplier, cha));
		gh4.d(cig.b);

		for (Entry<ug<cig>, cig> entry6 : gh.c()) {
			ug<cig> ug7 = (ug<cig>)entry6.getKey();
			if (ug7 != cig.b) {
				gh4.a(ug7, entry6.getValue());
				if (gh.c(ug7)) {
					gh4.d(ug7);
				}
			}
		}

		return gh4;
	}

	public gh<cig> e() {
		return this.h;
	}

	public cha f() {
		cig cig2 = this.h.a(cig.b);
		return (cha)(cig2 == null ? a(new Random().nextLong()) : cig2.c());
	}

	public ImmutableSet<ug<bqb>> g() {
		return (ImmutableSet<ug<bqb>>)this.e().c().stream().map(entry -> ug.a(gl.ae, ((ug)entry.getKey()).a())).collect(ImmutableSet.toImmutableSet());
	}

	public boolean h() {
		return this.f() instanceof cil;
	}

	public boolean i() {
		return this.f() instanceof cim;
	}

	public cix k() {
		return new cix(this.e, this.f, true, this.h, this.i);
	}

	public static cix a(Properties properties) {
		String string2 = MoreObjects.firstNonNull((String)properties.get("generator-settings"), "");
		properties.put("generator-settings", string2);
		String string3 = MoreObjects.firstNonNull((String)properties.get("level-seed"), "");
		properties.put("level-seed", string3);
		String string4 = (String)properties.get("generate-structures");
		boolean boolean5 = string4 == null || Boolean.parseBoolean(string4);
		properties.put("generate-structures", Objects.toString(boolean5));
		String string6 = (String)properties.get("level-type");
		String string7 = (String)Optional.ofNullable(string6).map(string -> string.toLowerCase(Locale.ROOT)).orElse("default");
		properties.put("level-type", string7);
		long long8 = new Random().nextLong();
		if (!string3.isEmpty()) {
			try {
				long long10 = Long.parseLong(string3);
				if (long10 != 0L) {
					long8 = long10;
				}
			} catch (NumberFormatException var14) {
				long8 = (long)string3.hashCode();
			}
		}

		gh<cig> gh10 = cif.a(long8);
		switch (string7) {
			case "flat":
				JsonObject jsonObject13 = !string2.isEmpty() ? adt.a(string2) : new JsonObject();
				Dynamic<JsonElement> dynamic14 = new Dynamic<>(JsonOps.INSTANCE, jsonObject13);
				return new cix(long8, boolean5, false, a(gh10, new cim((cra)cra.a.parse(dynamic14).resultOrPartial(c::error).orElseGet(cra::i))));
			case "debug_all_block_states":
				return new cix(long8, boolean5, false, a(gh10, cil.d));
			case "amplified":
				return new cix(long8, boolean5, false, a(gh10, new cip(new bti(long8, false, false), long8, ciq.a.c.b())));
			case "largebiomes":
				return new cix(long8, boolean5, false, a(gh10, new cip(new bti(long8, false, true), long8, ciq.a.b.b())));
			default:
				return new cix(long8, boolean5, false, a(gh10, a(long8)));
		}
	}
}
