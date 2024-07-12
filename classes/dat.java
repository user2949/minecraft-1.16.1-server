import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class dat {
	private final Random a;
	private final float b;
	private final zd c;
	private final Function<uh, daw> d;
	private final Set<daw> e = Sets.<daw>newLinkedHashSet();
	private final Function<uh, ddm> f;
	private final Set<ddm> g = Sets.<ddm>newLinkedHashSet();
	private final Map<dcx<?>, Object> h;
	private final Map<uh, dat.b> i;

	private dat(Random random, float float2, zd zd, Function<uh, daw> function4, Function<uh, ddm> function5, Map<dcx<?>, Object> map6, Map<uh, dat.b> map7) {
		this.a = random;
		this.b = float2;
		this.c = zd;
		this.d = function4;
		this.f = function5;
		this.h = ImmutableMap.copyOf(map6);
		this.i = ImmutableMap.copyOf(map7);
	}

	public boolean a(dcx<?> dcx) {
		return this.h.containsKey(dcx);
	}

	public void a(uh uh, Consumer<bki> consumer) {
		dat.b b4 = (dat.b)this.i.get(uh);
		if (b4 != null) {
			b4.add(this, consumer);
		}
	}

	@Nullable
	public <T> T c(dcx<T> dcx) {
		return (T)this.h.get(dcx);
	}

	public boolean a(daw daw) {
		return this.e.add(daw);
	}

	public void b(daw daw) {
		this.e.remove(daw);
	}

	public boolean a(ddm ddm) {
		return this.g.add(ddm);
	}

	public void b(ddm ddm) {
		this.g.remove(ddm);
	}

	public daw a(uh uh) {
		return (daw)this.d.apply(uh);
	}

	public ddm b(uh uh) {
		return (ddm)this.f.apply(uh);
	}

	public Random a() {
		return this.a;
	}

	public float b() {
		return this.b;
	}

	public zd c() {
		return this.c;
	}

	public static class a {
		private final zd a;
		private final Map<dcx<?>, Object> b = Maps.<dcx<?>, Object>newIdentityHashMap();
		private final Map<uh, dat.b> c = Maps.<uh, dat.b>newHashMap();
		private Random d;
		private float e;

		public a(zd zd) {
			this.a = zd;
		}

		public dat.a a(Random random) {
			this.d = random;
			return this;
		}

		public dat.a a(long long1) {
			if (long1 != 0L) {
				this.d = new Random(long1);
			}

			return this;
		}

		public dat.a a(long long1, Random random) {
			if (long1 == 0L) {
				this.d = random;
			} else {
				this.d = new Random(long1);
			}

			return this;
		}

		public dat.a a(float float1) {
			this.e = float1;
			return this;
		}

		public <T> dat.a a(dcx<T> dcx, T object) {
			this.b.put(dcx, object);
			return this;
		}

		public <T> dat.a b(dcx<T> dcx, @Nullable T object) {
			if (object == null) {
				this.b.remove(dcx);
			} else {
				this.b.put(dcx, object);
			}

			return this;
		}

		public dat.a a(uh uh, dat.b b) {
			dat.b b4 = (dat.b)this.c.put(uh, b);
			if (b4 != null) {
				throw new IllegalStateException("Duplicated dynamic drop '" + this.c + "'");
			} else {
				return this;
			}
		}

		public zd a() {
			return this.a;
		}

		public <T> T a(dcx<T> dcx) {
			T object3 = (T)this.b.get(dcx);
			if (object3 == null) {
				throw new IllegalArgumentException("No parameter " + dcx);
			} else {
				return object3;
			}
		}

		@Nullable
		public <T> T b(dcx<T> dcx) {
			return (T)this.b.get(dcx);
		}

		public dat a(dcy dcy) {
			Set<dcx<?>> set3 = Sets.<dcx<?>>difference(this.b.keySet(), dcy.b());
			if (!set3.isEmpty()) {
				throw new IllegalArgumentException("Parameters not allowed in this parameter set: " + set3);
			} else {
				Set<dcx<?>> set4 = Sets.<dcx<?>>difference(dcy.a(), this.b.keySet());
				if (!set4.isEmpty()) {
					throw new IllegalArgumentException("Missing required parameters: " + set4);
				} else {
					Random random5 = this.d;
					if (random5 == null) {
						random5 = new Random();
					}

					MinecraftServer minecraftServer6 = this.a.l();
					return new dat(random5, this.e, this.a, minecraftServer6.aH()::a, minecraftServer6.aI()::a, this.b, this.c);
				}
			}
		}
	}

	@FunctionalInterface
	public interface b {
		void add(dat dat, Consumer<bki> consumer);
	}

	public static enum c {
		THIS("this", dda.a),
		KILLER("killer", dda.d),
		DIRECT_KILLER("direct_killer", dda.e),
		KILLER_PLAYER("killer_player", dda.b);

		private final String e;
		private final dcx<? extends aom> f;

		private c(String string3, dcx<? extends aom> dcx) {
			this.e = string3;
			this.f = dcx;
		}

		public dcx<? extends aom> a() {
			return this.f;
		}

		public static dat.c a(String string) {
			for (dat.c c5 : values()) {
				if (c5.e.equals(string)) {
					return c5;
				}
			}

			throw new IllegalArgumentException("Invalid entity target " + string);
		}

		public static class a extends TypeAdapter<dat.c> {
			public void write(JsonWriter jsonWriter, dat.c c) throws IOException {
				jsonWriter.value(c.e);
			}

			public dat.c read(JsonReader jsonReader) throws IOException {
				return dat.c.a(jsonReader.nextString());
			}
		}
	}
}
