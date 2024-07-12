public class dbd<T> {
	private final dbc<? extends T> a;

	public dbd(dbc<? extends T> dbc) {
		this.a = dbc;
	}

	public dbc<? extends T> a() {
		return this.a;
	}
}
