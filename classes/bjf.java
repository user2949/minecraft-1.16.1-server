import com.google.common.collect.Maps;
import java.util.Map;

public class bjf extends bke {
	private static final Map<bje, bjf> a = Maps.newEnumMap(bje.class);
	private final bje b;

	public bjf(bje bje, bke.a a) {
		super(a);
		this.b = bje;
		bjf.a.put(bje, this);
	}

	@Override
	public ang a(bki bki, bec bec, aoy aoy, anf anf) {
		if (aoy instanceof azd) {
			azd azd6 = (azd)aoy;
			if (azd6.aU() && !azd6.eN() && azd6.eM() != this.b) {
				if (!bec.l.v) {
					azd6.b(this.b);
					bki.g(1);
				}

				return ang.a(bec.l.v);
			}
		}

		return ang.PASS;
	}

	public bje d() {
		return this.b;
	}

	public static bjf a(bje bje) {
		return (bjf)a.get(bje);
	}
}
