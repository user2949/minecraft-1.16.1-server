import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;

public class ceh extends cdl {
	private final mr[] a = new mr[]{nd.d, nd.d, nd.d, nd.d};
	private boolean b = true;
	private bec c;
	private final mu[] g = new mu[4];
	private bje h = bje.BLACK;

	public ceh() {
		super(cdm.h);
	}

	@Override
	public le a(le le) {
		super.a(le);

		for (int integer3 = 0; integer3 < 4; integer3++) {
			String string4 = mr.a.a(this.a[integer3]);
			le.a("Text" + (integer3 + 1), string4);
		}

		le.a("Color", this.h.c());
		return le;
	}

	@Override
	public void a(cfj cfj, le le) {
		this.b = false;
		super.a(cfj, le);
		this.h = bje.a(le.l("Color"), bje.BLACK);

		for (int integer4 = 0; integer4 < 4; integer4++) {
			String string5 = le.l("Text" + (integer4 + 1));
			mr mr6 = mr.a.a(string5.isEmpty() ? "\"\"" : string5);
			if (this.d instanceof zd) {
				try {
					this.a[integer4] = ms.a(this.a(null), mr6, null, 0);
				} catch (CommandSyntaxException var7) {
					this.a[integer4] = mr6;
				}
			} else {
				this.a[integer4] = mr6;
			}

			this.g[integer4] = null;
		}
	}

	public void a(int integer, mr mr) {
		this.a[integer] = mr;
		this.g[integer] = null;
	}

	@Nullable
	@Override
	public nv a() {
		return new nv(this.e, 9, this.b());
	}

	@Override
	public le b() {
		return this.a(new le());
	}

	@Override
	public boolean t() {
		return true;
	}

	public boolean d() {
		return this.b;
	}

	public void a(bec bec) {
		this.c = bec;
	}

	public bec f() {
		return this.c;
	}

	public boolean b(bec bec) {
		for (mr mr6 : this.a) {
			nb nb7 = mr6 == null ? null : mr6.c();
			if (nb7 != null && nb7.h() != null) {
				mp mp8 = nb7.h();
				if (mp8.a() == mp.a.RUN_COMMAND) {
					bec.cg().aB().a(this.a((ze)bec), mp8.b());
				}
			}
		}

		return true;
	}

	public cz a(@Nullable ze ze) {
		String string3 = ze == null ? "Sign" : ze.P().getString();
		mr mr4 = (mr)(ze == null ? new nd("Sign") : ze.d());
		return new cz(cy.a_, dem.a(this.e), del.a, (zd)this.d, 2, string3, mr4, this.d.l(), ze);
	}

	public bje g() {
		return this.h;
	}

	public boolean a(bje bje) {
		if (bje != this.g()) {
			this.h = bje;
			this.Z_();
			this.d.a(this.o(), this.p(), this.p(), 3);
			return true;
		} else {
			return false;
		}
	}
}
