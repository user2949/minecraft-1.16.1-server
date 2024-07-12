import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.List;

public class qc implements ni<nl> {
	private int a;
	private final List<Pair<aor, bki>> b;

	public qc() {
		this.b = Lists.<Pair<aor, bki>>newArrayList();
	}

	public qc(int integer, List<Pair<aor, bki>> list) {
		this.a = integer;
		this.b = list;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		aor[] arr3 = aor.values();

		int integer4;
		do {
			integer4 = mg.readByte();
			aor aor5 = arr3[integer4 & 127];
			bki bki6 = mg.m();
			this.b.add(Pair.of(aor5, bki6));
		} while ((integer4 & -128) != 0);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		int integer3 = this.b.size();

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			Pair<aor, bki> pair5 = (Pair<aor, bki>)this.b.get(integer4);
			aor aor6 = pair5.getFirst();
			boolean boolean7 = integer4 != integer3 - 1;
			int integer8 = aor6.ordinal();
			mg.writeByte(boolean7 ? integer8 | -128 : integer8);
			mg.a(pair5.getSecond());
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
