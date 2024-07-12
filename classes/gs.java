import com.mojang.serialization.Lifecycle;

public abstract class gs<T> extends gl<T> {
	public gs(ug<gl<T>> ug, Lifecycle lifecycle) {
		super(ug, lifecycle);
	}

	public abstract <V extends T> V a(int integer, ug<T> ug, V object);

	public abstract <V extends T> V a(ug<T> ug, V object);

	public abstract void d(ug<T> ug);
}
