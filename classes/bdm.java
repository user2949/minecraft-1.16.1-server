import javax.annotation.Nullable;

public class bdm implements boy {
	private final bhl a;
	private final bec b;
	private bpa c = new bpa();
	private int d;

	public bdm(bec bec) {
		this.b = bec;
		this.a = new bhl(this);
	}

	@Nullable
	@Override
	public bec eN() {
		return this.b;
	}

	@Override
	public void f(@Nullable bec bec) {
	}

	@Override
	public bpa eP() {
		return this.c;
	}

	@Override
	public void a(boz boz) {
		boz.j();
	}

	@Override
	public void k(bki bki) {
	}

	@Override
	public bqb eV() {
		return this.b.l;
	}

	@Override
	public int eM() {
		return this.d;
	}

	@Override
	public void t(int integer) {
		this.d = integer;
	}

	@Override
	public boolean eQ() {
		return true;
	}

	@Override
	public ack eR() {
		return acl.pR;
	}
}
