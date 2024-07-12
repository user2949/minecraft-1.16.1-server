import com.mojang.serialization.Codec;

public abstract class hg<T extends hf> {
	private final boolean a;
	private final hf.a<T> b;

	protected hg(boolean boolean1, hf.a<T> a) {
		this.a = boolean1;
		this.b = a;
	}

	public hf.a<T> d() {
		return this.b;
	}

	public abstract Codec<T> e();
}
