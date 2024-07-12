import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;

public class anm implements amz, bhz {
	private final int a;
	private final gi<bki> b;
	private List<anb> c;

	public anm(int integer) {
		this.a = integer;
		this.b = gi.a(integer, bki.b);
	}

	public anm(bki... arr) {
		this.a = arr.length;
		this.b = gi.a(bki.b, arr);
	}

	public void a(anb anb) {
		if (this.c == null) {
			this.c = Lists.<anb>newArrayList();
		}

		this.c.add(anb);
	}

	public void b(anb anb) {
		this.c.remove(anb);
	}

	@Override
	public bki a(int integer) {
		return integer >= 0 && integer < this.b.size() ? this.b.get(integer) : bki.b;
	}

	public List<bki> f() {
		List<bki> list2 = (List<bki>)this.b.stream().filter(bki -> !bki.a()).collect(Collectors.toList());
		this.aa_();
		return list2;
	}

	@Override
	public bki a(int integer1, int integer2) {
		bki bki4 = ana.a(this.b, integer1, integer2);
		if (!bki4.a()) {
			this.Z_();
		}

		return bki4;
	}

	public bki a(bke bke, int integer) {
		bki bki4 = new bki(bke, 0);

		for (int integer5 = this.a - 1; integer5 >= 0; integer5--) {
			bki bki6 = this.a(integer5);
			if (bki6.b().equals(bke)) {
				int integer7 = integer - bki4.E();
				bki bki8 = bki6.a(integer7);
				bki4.f(bki8.E());
				if (bki4.E() == integer) {
					break;
				}
			}
		}

		if (!bki4.a()) {
			this.Z_();
		}

		return bki4;
	}

	public bki a(bki bki) {
		bki bki3 = bki.i();
		this.d(bki3);
		if (bki3.a()) {
			return bki.b;
		} else {
			this.c(bki3);
			return bki3.a() ? bki.b : bki3;
		}
	}

	public boolean b(bki bki) {
		boolean boolean3 = false;

		for (bki bki5 : this.b) {
			if (bki5.a() || this.a(bki5, bki) && bki5.E() < bki5.c()) {
				boolean3 = true;
				break;
			}
		}

		return boolean3;
	}

	@Override
	public bki b(int integer) {
		bki bki3 = this.b.get(integer);
		if (bki3.a()) {
			return bki.b;
		} else {
			this.b.set(integer, bki.b);
			return bki3;
		}
	}

	@Override
	public void a(int integer, bki bki) {
		this.b.set(integer, bki);
		if (!bki.a() && bki.E() > this.X_()) {
			bki.e(this.X_());
		}

		this.Z_();
	}

	@Override
	public int ab_() {
		return this.a;
	}

	@Override
	public boolean c() {
		for (bki bki3 : this.b) {
			if (!bki3.a()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void Z_() {
		if (this.c != null) {
			for (anb anb3 : this.c) {
				anb3.a(this);
			}
		}
	}

	@Override
	public boolean a(bec bec) {
		return true;
	}

	@Override
	public void aa_() {
		this.b.clear();
		this.Z_();
	}

	@Override
	public void a(bee bee) {
		for (bki bki4 : this.b) {
			bee.b(bki4);
		}
	}

	public String toString() {
		return ((List)this.b.stream().filter(bki -> !bki.a()).collect(Collectors.toList())).toString();
	}

	private void c(bki bki) {
		for (int integer3 = 0; integer3 < this.a; integer3++) {
			bki bki4 = this.a(integer3);
			if (bki4.a()) {
				this.a(integer3, bki.i());
				bki.e(0);
				return;
			}
		}
	}

	private void d(bki bki) {
		for (int integer3 = 0; integer3 < this.a; integer3++) {
			bki bki4 = this.a(integer3);
			if (this.a(bki4, bki)) {
				this.b(bki, bki4);
				if (bki.a()) {
					return;
				}
			}
		}
	}

	private boolean a(bki bki1, bki bki2) {
		return bki1.b() == bki2.b() && bki.a(bki1, bki2);
	}

	private void b(bki bki1, bki bki2) {
		int integer4 = Math.min(this.X_(), bki2.c());
		int integer5 = Math.min(bki1.E(), integer4 - bki2.E());
		if (integer5 > 0) {
			bki2.f(integer5);
			bki1.g(integer5);
			this.Z_();
		}
	}

	public void a(lk lk) {
		for (int integer3 = 0; integer3 < lk.size(); integer3++) {
			bki bki4 = bki.a(lk.a(integer3));
			if (!bki4.a()) {
				this.a(bki4);
			}
		}
	}

	public lk g() {
		lk lk2 = new lk();

		for (int integer3 = 0; integer3 < this.ab_(); integer3++) {
			bki bki4 = this.a(integer3);
			if (!bki4.a()) {
				lk2.add(bki4.b(new le()));
			}
		}

		return lk2;
	}
}
