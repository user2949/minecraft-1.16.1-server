import com.google.common.collect.Maps;
import java.util.Map;

public class bkx extends bke {
	private static final Map<ack, bkx> a = Maps.<ack, bkx>newHashMap();
	private final int b;
	private final ack c;

	protected bkx(int integer, ack ack, bke.a a) {
		super(a);
		this.b = integer;
		this.c = ack;
		bkx.a.put(this.c, this);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		cfj cfj5 = bqb3.d_(fu4);
		if (cfj5.a(bvs.cI) && !(Boolean)cfj5.c(byv.a)) {
			bki bki6 = blv.l();
			if (!bqb3.v) {
				((byv)bvs.cI).a(bqb3, fu4, cfj5, bki6);
				bqb3.a(null, 1010, fu4, bke.a(this));
				bki6.g(1);
				bec bec7 = blv.m();
				if (bec7 != null) {
					bec7.a(acu.ak);
				}
			}

			return ang.a(bqb3.v);
		} else {
			return ang.PASS;
		}
	}

	public int f() {
		return this.b;
	}
}
