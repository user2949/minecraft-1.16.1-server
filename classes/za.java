import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class za extends amw {
	private final Set<ze> h = Sets.<ze>newHashSet();
	private final Set<ze> i = Collections.unmodifiableSet(this.h);
	private boolean j = true;

	public za(mr mr, amw.a a, amw.b b) {
		super(aec.a(), mr, a, b);
	}

	@Override
	public void a(float float1) {
		if (float1 != this.b) {
			super.a(float1);
			this.a(ny.a.UPDATE_PCT);
		}
	}

	@Override
	public void a(amw.a a) {
		if (a != this.c) {
			super.a(a);
			this.a(ny.a.UPDATE_STYLE);
		}
	}

	@Override
	public void a(amw.b b) {
		if (b != this.d) {
			super.a(b);
			this.a(ny.a.UPDATE_STYLE);
		}
	}

	@Override
	public amw a(boolean boolean1) {
		if (boolean1 != this.e) {
			super.a(boolean1);
			this.a(ny.a.UPDATE_PROPERTIES);
		}

		return this;
	}

	@Override
	public amw b(boolean boolean1) {
		if (boolean1 != this.f) {
			super.b(boolean1);
			this.a(ny.a.UPDATE_PROPERTIES);
		}

		return this;
	}

	@Override
	public amw c(boolean boolean1) {
		if (boolean1 != this.g) {
			super.c(boolean1);
			this.a(ny.a.UPDATE_PROPERTIES);
		}

		return this;
	}

	@Override
	public void a(mr mr) {
		if (!Objects.equal(mr, this.a)) {
			super.a(mr);
			this.a(ny.a.UPDATE_NAME);
		}
	}

	private void a(ny.a a) {
		if (this.j) {
			ny ny3 = new ny(a, this);

			for (ze ze5 : this.h) {
				ze5.b.a(ny3);
			}
		}
	}

	public void a(ze ze) {
		if (this.h.add(ze) && this.j) {
			ze.b.a(new ny(ny.a.ADD, this));
		}
	}

	public void b(ze ze) {
		if (this.h.remove(ze) && this.j) {
			ze.b.a(new ny(ny.a.REMOVE, this));
		}
	}

	public void b() {
		if (!this.h.isEmpty()) {
			for (ze ze3 : Lists.newArrayList(this.h)) {
				this.b(ze3);
			}
		}
	}

	public boolean g() {
		return this.j;
	}

	public void d(boolean boolean1) {
		if (boolean1 != this.j) {
			this.j = boolean1;

			for (ze ze4 : this.h) {
				ze4.b.a(new ny(boolean1 ? ny.a.ADD : ny.a.REMOVE, this));
			}
		}
	}

	public Collection<ze> h() {
		return this.i;
	}
}
