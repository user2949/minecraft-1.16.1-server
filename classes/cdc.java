import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class cdc extends cdl implements ank {
	@Nullable
	private mr a;
	@Nullable
	private bje b = bje.WHITE;
	@Nullable
	private lk c;
	private boolean g;
	@Nullable
	private List<Pair<cdd, bje>> h;

	public cdc() {
		super(cdm.s);
	}

	public cdc(bje bje) {
		this();
		this.b = bje;
	}

	@Override
	public mr P() {
		return (mr)(this.a != null ? this.a : new ne("block.minecraft.banner"));
	}

	@Nullable
	@Override
	public mr R() {
		return this.a;
	}

	public void a(mr mr) {
		this.a = mr;
	}

	@Override
	public le a(le le) {
		super.a(le);
		if (this.c != null) {
			le.a("Patterns", this.c);
		}

		if (this.a != null) {
			le.a("CustomName", mr.a.a(this.a));
		}

		return le;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		if (le.c("CustomName", 8)) {
			this.a = mr.a.a(le.l("CustomName"));
		}

		if (this.n()) {
			this.b = ((bup)this.p().b()).b();
		} else {
			this.b = null;
		}

		this.c = le.d("Patterns", 10);
		this.h = null;
		this.g = true;
	}

	@Nullable
	@Override
	public nv a() {
		return new nv(this.e, 6, this.b());
	}

	@Override
	public le b() {
		return this.a(new le());
	}

	public static int b(bki bki) {
		le le2 = bki.b("BlockEntityTag");
		return le2 != null && le2.e("Patterns") ? le2.d("Patterns", 10).size() : 0;
	}

	public static void c(bki bki) {
		le le2 = bki.b("BlockEntityTag");
		if (le2 != null && le2.c("Patterns", 9)) {
			lk lk3 = le2.d("Patterns", 10);
			if (!lk3.isEmpty()) {
				lk3.c(lk3.size() - 1);
				if (lk3.isEmpty()) {
					bki.c("BlockEntityTag");
				}
			}
		}
	}

	public bje a(Supplier<cfj> supplier) {
		if (this.b == null) {
			this.b = ((bup)((cfj)supplier.get()).b()).b();
		}

		return this.b;
	}
}
