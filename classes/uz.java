import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class uz extends za {
	private final uh h;
	private final Set<UUID> i = Sets.<UUID>newHashSet();
	private int j;
	private int k = 100;

	public uz(uh uh, mr mr) {
		super(mr, amw.a.WHITE, amw.b.PROGRESS);
		this.h = uh;
		this.a(0.0F);
	}

	public uh a() {
		return this.h;
	}

	@Override
	public void a(ze ze) {
		super.a(ze);
		this.i.add(ze.bR());
	}

	public void a(UUID uUID) {
		this.i.add(uUID);
	}

	@Override
	public void b(ze ze) {
		super.b(ze);
		this.i.remove(ze.bR());
	}

	@Override
	public void b() {
		super.b();
		this.i.clear();
	}

	public int c() {
		return this.j;
	}

	public int d() {
		return this.k;
	}

	public void a(int integer) {
		this.j = integer;
		this.a(aec.a((float)integer / (float)this.k, 0.0F, 1.0F));
	}

	public void b(int integer) {
		this.k = integer;
		this.a(aec.a((float)this.j / (float)integer, 0.0F, 1.0F));
	}

	public final mr e() {
		return ms.a(this.j()).a(nb -> nb.a(this.l().a()).a(new mv(mv.a.a, new nd(this.a().toString()))).a(this.a().toString()));
	}

	public boolean a(Collection<ze> collection) {
		Set<UUID> set3 = Sets.<UUID>newHashSet();
		Set<ze> set4 = Sets.<ze>newHashSet();

		for (UUID uUID6 : this.i) {
			boolean boolean7 = false;

			for (ze ze9 : collection) {
				if (ze9.bR().equals(uUID6)) {
					boolean7 = true;
					break;
				}
			}

			if (!boolean7) {
				set3.add(uUID6);
			}
		}

		for (ze ze6 : collection) {
			boolean boolean7 = false;

			for (UUID uUID9 : this.i) {
				if (ze6.bR().equals(uUID9)) {
					boolean7 = true;
					break;
				}
			}

			if (!boolean7) {
				set4.add(ze6);
			}
		}

		for (UUID uUID6 : set3) {
			for (ze ze8 : this.h()) {
				if (ze8.bR().equals(uUID6)) {
					this.b(ze8);
					break;
				}
			}

			this.i.remove(uUID6);
		}

		for (ze ze6 : set4) {
			this.a(ze6);
		}

		return !set3.isEmpty() || !set4.isEmpty();
	}

	public le f() {
		le le2 = new le();
		le2.a("Name", mr.a.a(this.a));
		le2.a("Visible", this.g());
		le2.b("Value", this.j);
		le2.b("Max", this.k);
		le2.a("Color", this.l().b());
		le2.a("Overlay", this.m().a());
		le2.a("DarkenScreen", this.n());
		le2.a("PlayBossMusic", this.o());
		le2.a("CreateWorldFog", this.p());
		lk lk3 = new lk();

		for (UUID uUID5 : this.i) {
			lk3.add(lq.a(uUID5));
		}

		le2.a("Players", lk3);
		return le2;
	}

	public static uz a(le le, uh uh) {
		uz uz3 = new uz(uh, mr.a.a(le.l("Name")));
		uz3.d(le.q("Visible"));
		uz3.a(le.h("Value"));
		uz3.b(le.h("Max"));
		uz3.a(amw.a.a(le.l("Color")));
		uz3.a(amw.b.a(le.l("Overlay")));
		uz3.a(le.q("DarkenScreen"));
		uz3.b(le.q("PlayBossMusic"));
		uz3.c(le.q("CreateWorldFog"));
		lk lk4 = le.d("Players", 11);

		for (int integer5 = 0; integer5 < lk4.size(); integer5++) {
			uz3.a(lq.a(lk4.k(integer5)));
		}

		return uz3;
	}

	public void c(ze ze) {
		if (this.i.contains(ze.bR())) {
			this.a(ze);
		}
	}

	public void d(ze ze) {
		super.b(ze);
	}
}
