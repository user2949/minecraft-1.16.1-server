public abstract class amr<R extends Runnable> extends amn<R> {
	private int b;

	public amr(String string) {
		super(string);
	}

	@Override
	protected boolean at() {
		return this.bl() || super.at();
	}

	protected boolean bl() {
		return this.b != 0;
	}

	@Override
	protected void c(R runnable) {
		this.b++;

		try {
			super.c(runnable);
		} finally {
			this.b--;
		}
	}
}
