import java.util.List;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

public class bhx extends bhg {
	private final bqb g;
	@Nullable
	private bnl h;
	private final List<bnl> i;

	public bhx(int integer, beb beb) {
		this(integer, beb, bgs.a);
	}

	public bhx(int integer, beb beb, bgs bgs) {
		super(bhk.u, integer, beb, bgs);
		this.g = beb.e.l;
		this.i = this.g.o().a(bmx.g);
	}

	@Override
	protected boolean a(cfj cfj) {
		return cfj.a(bvs.lZ);
	}

	@Override
	protected boolean b(bec bec, boolean boolean2) {
		return this.h != null && this.h.a(this.d, this.g);
	}

	@Override
	protected bki a(bec bec, bki bki) {
		this.d(0);
		this.d(1);
		this.e.a((BiConsumer<bqb, fu>)((bqb, fu) -> bqb.c(1044, fu, 0)));
		return bki;
	}

	private void d(int integer) {
		bki bki3 = this.d.a(integer);
		bki3.g(1);
		this.d.a(integer, bki3);
	}

	@Override
	public void e() {
		List<bnl> list2 = this.g.o().b(bmx.g, this.d, this.g);
		if (list2.isEmpty()) {
			this.c.a(0, bki.b);
		} else {
			this.h = (bnl)list2.get(0);
			bki bki3 = this.h.a(this.d);
			this.c.a(0, bki3);
		}
	}

	@Override
	protected boolean a(bki bki) {
		return this.i.stream().anyMatch(bnl -> bnl.a(bki));
	}
}
