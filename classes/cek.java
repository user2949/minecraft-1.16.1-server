import javax.annotation.Nullable;

public class cek extends cdl implements ceo {
	private final bpd a = new bpd() {
		@Override
		public void a(int integer) {
			cek.this.d.a(cek.this.e, bvs.bP, integer, 0);
		}

		@Override
		public bqb a() {
			return cek.this.d;
		}

		@Override
		public fu b() {
			return cek.this.e;
		}

		@Override
		public void a(bqp bqp) {
			super.a(bqp);
			if (this.a() != null) {
				cfj cfj3 = this.a().d_(this.b());
				this.a().a(cek.this.e, cfj3, cfj3, 4);
			}
		}
	};

	public cek() {
		super(cdm.i);
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.a.a(le);
	}

	@Override
	public le a(le le) {
		super.a(le);
		this.a.b(le);
		return le;
	}

	@Override
	public void al_() {
		this.a.c();
	}

	@Nullable
	@Override
	public nv a() {
		return new nv(this.e, 1, this.b());
	}

	@Override
	public le b() {
		le le2 = this.a(new le());
		le2.r("SpawnPotentials");
		return le2;
	}

	@Override
	public boolean a_(int integer1, int integer2) {
		return this.a.b(integer1) ? true : super.a_(integer1, integer2);
	}

	@Override
	public boolean t() {
		return true;
	}

	public bpd d() {
		return this.a;
	}
}
