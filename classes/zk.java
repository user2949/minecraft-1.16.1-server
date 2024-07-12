import java.util.function.Function;

public class zk<T> implements bqr<T> {
	private final Function<fu, bqr<T>> a;

	public zk(Function<fu, bqr<T>> function) {
		this.a = function;
	}

	@Override
	public boolean a(fu fu, T object) {
		return ((bqr)this.a.apply(fu)).a(fu, object);
	}

	@Override
	public void a(fu fu, T object, int integer, bqt bqt) {
		((bqr)this.a.apply(fu)).a(fu, object, integer, bqt);
	}

	@Override
	public boolean b(fu fu, T object) {
		return false;
	}
}
