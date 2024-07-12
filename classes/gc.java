import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;

public final class gc {
	public static final Codec<gc> a = RecordCodecBuilder.create(
		instance -> instance.group(bqb.f.fieldOf("dimension").forGetter(gc::a), fu.a.fieldOf("pos").forGetter(gc::b)).apply(instance, gc::a)
	);
	private final ug<bqb> b;
	private final fu c;

	private gc(ug<bqb> ug, fu fu) {
		this.b = ug;
		this.c = fu;
	}

	public static gc a(ug<bqb> ug, fu fu) {
		return new gc(ug, fu);
	}

	public ug<bqb> a() {
		return this.b;
	}

	public fu b() {
		return this.c;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			gc gc3 = (gc)object;
			return Objects.equals(this.b, gc3.b) && Objects.equals(this.c, gc3.c);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.b, this.c});
	}

	public String toString() {
		return this.b.toString() + " " + this.c;
	}
}
