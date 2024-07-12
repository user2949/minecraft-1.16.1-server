import com.mojang.serialization.Lifecycle;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class fy<T> extends gh<T> {
	private final uh bb;
	private T bc;

	public fy(String string, ug<gl<T>> ug, Lifecycle lifecycle) {
		super(ug, lifecycle);
		this.bb = new uh(string);
	}

	@Override
	public <V extends T> V a(int integer, ug<T> ug, V object) {
		if (this.bb.equals(ug.a())) {
			this.bc = (T)object;
		}

		return super.a(integer, ug, object);
	}

	@Override
	public int a(@Nullable T object) {
		int integer3 = super.a(object);
		return integer3 == -1 ? super.a(this.bc) : integer3;
	}

	@Nonnull
	@Override
	public uh b(T object) {
		uh uh3 = super.b(object);
		return uh3 == null ? this.bb : uh3;
	}

	@Nonnull
	@Override
	public T a(@Nullable uh uh) {
		T object3 = super.a(uh);
		return object3 == null ? this.bc : object3;
	}

	@Nonnull
	@Override
	public T a(int integer) {
		T object3 = super.a(integer);
		return object3 == null ? this.bc : object3;
	}

	@Nonnull
	@Override
	public T a(Random random) {
		T object3 = super.a(random);
		return object3 == null ? this.bc : object3;
	}

	public uh a() {
		return this.bb;
	}
}
