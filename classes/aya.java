import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;

public class aya {
	private final fu a;
	private final ayc b;
	private int c;
	private final Runnable d;

	public static Codec<aya> a(Runnable runnable) {
		return RecordCodecBuilder.create(
			instance -> instance.group(
						fu.a.fieldOf("pos").forGetter(aya -> aya.a),
						gl.aT.fieldOf("type").forGetter(aya -> aya.b),
						Codec.INT.fieldOf("free_tickets").withDefault(0).forGetter(aya -> aya.c),
						RecordCodecBuilder.point(runnable)
					)
					.apply(instance, aya::new)
		);
	}

	private aya(fu fu, ayc ayc, int integer, Runnable runnable) {
		this.a = fu.h();
		this.b = ayc;
		this.c = integer;
		this.d = runnable;
	}

	public aya(fu fu, ayc ayc, Runnable runnable) {
		this(fu, ayc, ayc.b(), runnable);
	}

	protected boolean b() {
		if (this.c <= 0) {
			return false;
		} else {
			this.c--;
			this.d.run();
			return true;
		}
	}

	protected boolean c() {
		if (this.c >= this.b.b()) {
			return false;
		} else {
			this.c++;
			this.d.run();
			return true;
		}
	}

	public boolean d() {
		return this.c > 0;
	}

	public boolean e() {
		return this.c != this.b.b();
	}

	public fu f() {
		return this.a;
	}

	public ayc g() {
		return this.b;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else {
			return object != null && this.getClass() == object.getClass() ? Objects.equals(this.a, ((aya)object).a) : false;
		}
	}

	public int hashCode() {
		return this.a.hashCode();
	}
}
