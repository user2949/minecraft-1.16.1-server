import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class gl<T> implements Codec<T>, Keyable, gd<T> {
	protected static final Logger e = LogManager.getLogger();
	private static final Map<uh, Supplier<?>> a = Maps.<uh, Supplier<?>>newLinkedHashMap();
	public static final uh f = new uh("root");
	protected static final gs<gs<?>> g = new gh<>(a("root"), Lifecycle.experimental());
	public static final gl<? extends gl<?>> h = g;
	public static final ug<gl<ack>> i = a("sound_event");
	public static final ug<gl<cwz>> j = a("fluid");
	public static final ug<gl<aoe>> k = a("mob_effect");
	public static final ug<gl<bvr>> l = a("block");
	public static final ug<gl<bnw>> m = a("enchantment");
	public static final ug<gl<aoq<?>>> n = a("entity_type");
	public static final ug<gl<bke>> o = a("item");
	public static final ug<gl<bmb>> p = a("potion");
	public static final ug<gl<cjh<?>>> q = a("carver");
	public static final ug<gl<cvw<?>>> r = a("surface_builder");
	public static final ug<gl<ckt<?>>> s = a("feature");
	public static final ug<gl<csc<?>>> t = a("decorator");
	public static final ug<gl<bre>> u = a("biome");
	public static final ug<gl<cpp<?>>> v = a("block_state_provider_type");
	public static final ug<gl<cmz<?>>> w = a("block_placer_type");
	public static final ug<gl<cph<?>>> x = a("foliage_placer_type");
	public static final ug<gl<cqx<?>>> y = a("trunk_placer_type");
	public static final ug<gl<cqn<?>>> z = a("tree_decorator_type");
	public static final ug<gl<cox<?>>> A = a("feature_size_type");
	public static final ug<gl<hg<?>>> B = a("particle_type");
	public static final ug<gl<Codec<? extends brh>>> C = a("biome_source");
	public static final ug<gl<Codec<? extends cha>>> D = a("chunk_generator");
	public static final ug<gl<cdm<?>>> E = a("block_entity_type");
	public static final ug<gl<bbc>> F = a("motive");
	public static final ug<gl<uh>> G = a("custom_stat");
	public static final ug<gl<chc>> H = a("chunk_status");
	public static final ug<gl<cml<?>>> I = a("structure_feature");
	public static final ug<gl<cmm>> J = a("structure_piece");
	public static final ug<gl<cuz<?>>> K = a("rule_test");
	public static final ug<gl<cut<?>>> L = a("pos_rule_test");
	public static final ug<gl<cvd<?>>> M = a("structure_processor");
	public static final ug<gl<cqe<?>>> N = a("structure_pool_element");
	public static final ug<gl<bhk<?>>> O = a("menu");
	public static final ug<gl<bmx<?>>> P = a("recipe_type");
	public static final ug<gl<bmw<?>>> Q = a("recipe_serializer");
	public static final ug<gl<aps>> R = a("attribute");
	public static final ug<gl<act<?>>> S = a("stat_type");
	public static final ug<gl<bdu>> T = a("villager_type");
	public static final ug<gl<bds>> U = a("villager_profession");
	public static final ug<gl<ayc>> V = a("point_of_interest_type");
	public static final ug<gl<awp<?>>> W = a("memory_module_type");
	public static final ug<gl<axo<?>>> X = a("sensor_type");
	public static final ug<gl<bfn>> Y = a("schedule");
	public static final ug<gl<bfl>> Z = a("activity");
	public static final ug<gl<dbp>> aa = a("loot_pool_entry_type");
	public static final ug<gl<dci>> ab = a("loot_function_type");
	public static final ug<gl<ddn>> ac = a("loot_condition_type");
	public static final ug<gl<cif>> ad = a("dimension_type");
	public static final ug<gl<bqb>> ae = a("dimension");
	public static final ug<gl<cig>> af = a("dimension");
	public static final gl<ack> ag = a(i, () -> acl.gL);
	public static final fy<cwz> ah = a(j, "empty", () -> cxb.a);
	public static final gl<aoe> ai = a(k, () -> aoi.z);
	public static final fy<bvr> aj = a(l, "air", () -> bvs.a);
	public static final gl<bnw> ak = a(m, () -> boa.w);
	public static final fy<aoq<?>> al = a(n, "pig", () -> aoq.ah);
	public static final fy<bke> am = a(o, "air", () -> bkk.a);
	public static final fy<bmb> an = a(p, "empty", () -> bme.a);
	public static final gl<cjh<?>> ao = a(q, () -> cjh.a);
	public static final gl<cvw<?>> ap = a(r, () -> cvw.S);
	public static final gl<ckt<?>> aq = a(s, () -> ckt.z);
	public static final gl<csc<?>> ar = a(t, () -> csc.a);
	public static final gl<bre> as = a(u, () -> brk.b);
	public static final gl<cpp<?>> at = a(v, () -> cpp.a);
	public static final gl<cmz<?>> au = a(w, () -> cmz.a);
	public static final gl<cph<?>> av = a(x, () -> cph.a);
	public static final gl<cqx<?>> aw = a(y, () -> cqx.a);
	public static final gl<cqn<?>> ax = a(z, () -> cqn.b);
	public static final gl<cox<?>> ay = a(A, () -> cox.a);
	public static final gl<hg<?>> az = a(B, () -> hh.d);
	public static final gl<Codec<? extends brh>> aA = a(C, Lifecycle.stable(), () -> brh.a);
	public static final gl<Codec<? extends cha>> aB = a(D, Lifecycle.stable(), () -> cha.a);
	public static final gl<cdm<?>> aC = a(E, () -> cdm.a);
	public static final fy<bbc> aD = a(F, "kebab", () -> bbc.a);
	public static final gl<uh> aE = a(G, () -> acu.D);
	public static final fy<chc> aF = a(H, "empty", () -> chc.a);
	public static final gl<cml<?>> aG = a(I, () -> cml.c);
	public static final gl<cmm> aH = a(J, () -> cmm.c);
	public static final gl<cuz<?>> aI = a(K, () -> cuz.a);
	public static final gl<cut<?>> aJ = a(L, () -> cut.a);
	public static final gl<cvd<?>> aK = a(M, () -> cvd.a);
	public static final gl<cqe<?>> aL = a(N, () -> cqe.d);
	public static final gl<bhk<?>> aM = a(O, () -> bhk.h);
	public static final gl<bmx<?>> aN = a(P, () -> bmx.a);
	public static final gl<bmw<?>> aO = a(Q, () -> bmw.b);
	public static final gl<aps> aP = a(R, () -> apx.k);
	public static final gl<act<?>> aQ = a(S, () -> acu.c);
	public static final fy<bdu> aR = a(T, "plains", () -> bdu.c);
	public static final fy<bds> aS = a(U, "none", () -> bds.a);
	public static final fy<ayc> aT = a(V, "unemployed", () -> ayc.c);
	public static final fy<awp<?>> aU = a(W, "dummy", () -> awp.a);
	public static final fy<axo<?>> aV = a(X, "dummy", () -> axo.a);
	public static final gl<bfn> aW = a(Y, () -> bfn.a);
	public static final gl<bfl> aX = a(Z, () -> bfl.b);
	public static final gl<dbp> aY = a(aa, () -> dbm.a);
	public static final gl<dci> aZ = a(ab, () -> dcj.b);
	public static final gl<ddn> ba = a(ac, () -> ddo.a);
	private final ug<gl<T>> b;
	private final Lifecycle c;

	private static <T> ug<gl<T>> a(String string) {
		return ug.a(new uh(string));
	}

	private static <T extends gs<?>> void a(gs<T> gs) {
		gs.forEach(gs2 -> {
			if (gs2.b().isEmpty()) {
				e.error("Registry '{}' was empty after loading", gs.b((T)gs2));
				if (u.d) {
					throw new IllegalStateException("Registry: '" + gs.b((T)gs2) + "' is empty, not allowed, fix me!");
				}
			}

			if (gs2 instanceof fy) {
				uh uh3 = ((fy)gs2).a();
				Validate.notNull(gs2.a(uh3), "Missing default of DefaultedMappedRegistry: " + uh3);
			}
		});
	}

	private static <T> gl<T> a(ug<gl<T>> ug, Supplier<T> supplier) {
		return a(ug, Lifecycle.experimental(), supplier);
	}

	private static <T> fy<T> a(ug<gl<T>> ug, String string, Supplier<T> supplier) {
		return a(ug, string, Lifecycle.experimental(), supplier);
	}

	private static <T> gl<T> a(ug<gl<T>> ug, Lifecycle lifecycle, Supplier<T> supplier) {
		return a(ug, new gh<>(ug, lifecycle), supplier);
	}

	private static <T> fy<T> a(ug<gl<T>> ug, String string, Lifecycle lifecycle, Supplier<T> supplier) {
		return a(ug, new fy<>(string, ug, lifecycle), supplier);
	}

	private static <T, R extends gs<T>> R a(ug<gl<T>> ug, R gs, Supplier<T> supplier) {
		uh uh4 = ug.a();
		a.put(uh4, supplier);
		gs<R> gs5 = g;
		return gs5.a(ug, gs);
	}

	protected gl(ug<gl<T>> ug, Lifecycle lifecycle) {
		this.b = ug;
		this.c = lifecycle;
	}

	public String toString() {
		return "Registry[" + this.b + " (" + this.c + ")]";
	}

	@Override
	public <U> DataResult<Pair<T, U>> decode(DynamicOps<U> dynamicOps, U object) {
		return dynamicOps.compressMaps()
			? dynamicOps.getNumberValue(object).flatMap(number -> {
				int integer3 = number.intValue();
				if (!this.b(integer3)) {
					return DataResult.error("Unknown registry id: " + number);
				} else {
					T object4 = this.a(integer3);
					return DataResult.success(object4, this.c);
				}
			}).map(objectx -> Pair.of(objectx, dynamicOps.empty()))
			: uh.a
				.decode(dynamicOps, object)
				.addLifecycle(this.c)
				.flatMap(
					pair -> !this.c((uh)pair.getFirst()) ? DataResult.error("Unknown registry key: " + pair.getFirst()) : DataResult.success(pair.mapFirst(this::a), this.c)
				);
	}

	@Override
	public <U> DataResult<U> encode(T object1, DynamicOps<U> dynamicOps, U object3) {
		uh uh5 = this.b(object1);
		if (uh5 == null) {
			return DataResult.error("Unknown registry element " + object1);
		} else {
			return dynamicOps.compressMaps()
				? dynamicOps.mergeToPrimitive(object3, dynamicOps.createInt(this.a(object1))).setLifecycle(this.c)
				: dynamicOps.mergeToPrimitive(object3, dynamicOps.createString(uh5.toString())).setLifecycle(this.c);
		}
	}

	@Override
	public <U> Stream<U> keys(DynamicOps<U> dynamicOps) {
		return this.b().stream().map(uh -> dynamicOps.createString(uh.toString()));
	}

	@Nullable
	public abstract uh b(T object);

	public abstract Optional<ug<T>> c(T object);

	public abstract int a(@Nullable T object);

	@Nullable
	public abstract T a(@Nullable ug<T> ug);

	@Nullable
	public abstract T a(@Nullable uh uh);

	public abstract Optional<T> b(@Nullable uh uh);

	public abstract Set<uh> b();

	public Stream<T> e() {
		return StreamSupport.stream(this.spliterator(), false);
	}

	public abstract boolean c(uh uh);

	public abstract boolean c(ug<T> ug);

	public abstract boolean b(int integer);

	public static <T> T a(gl<? super T> gl, String string, T object) {
		return a(gl, new uh(string), object);
	}

	public static <V, T extends V> T a(gl<V> gl, uh uh, T object) {
		return ((gs)gl).a(ug.a(gl.b, uh), object);
	}

	public static <V, T extends V> T a(gl<V> gl, int integer, String string, T object) {
		return ((gs)gl).a(integer, ug.a(gl.b, new uh(string)), object);
	}

	static {
		a.forEach((uh, supplier) -> {
			if (supplier.get() == null) {
				e.error("Unable to bootstrap registry '{}'", uh);
			}
		});
		a(g);
	}
}
