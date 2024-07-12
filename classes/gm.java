import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import java.util.Objects;
import java.util.Optional;

public interface gm {
	<E> Optional<gs<E>> a(ug<gl<E>> ug);

	static gm.a b() {
		return cif.a(new gm.a());
	}

	public static final class a implements gm {
		public static final Codec<gm.a> a = gh.a(gl.ad, Lifecycle.experimental(), cif.a).<gm.a>xmap(gm.a::new, a -> a.b).fieldOf("dimension").codec();
		private final gh<cif> b;

		public a() {
			this(new gh<>(gl.ad, Lifecycle.experimental()));
		}

		private a(gh<cif> gh) {
			this.b = gh;
		}

		public void a(ug<cif> ug, cif cif) {
			this.b.a(ug, cif);
		}

		@Override
		public <E> Optional<gs<E>> a(ug<gl<E>> ug) {
			return Objects.equals(ug, gl.ad) ? Optional.of(this.b) : Optional.empty();
		}

		public gl<cif> a() {
			return this.b;
		}
	}
}
