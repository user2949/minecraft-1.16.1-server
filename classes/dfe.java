import it.unimi.dsi.fastutil.doubles.DoubleList;

public class dfe extends dfg {
	private final dfg b;
	private final fz.a c;
	private static final DoubleList d = new des(1);

	public dfe(dfg dfg, fz.a a, int integer) {
		super(a(dfg.a, a, integer));
		this.b = dfg;
		this.c = a;
	}

	private static dev a(dev dev, fz.a a, int integer) {
		return new dff(
			dev,
			a.a(integer, 0, 0),
			a.a(0, integer, 0),
			a.a(0, 0, integer),
			a.a(integer + 1, dev.a, dev.a),
			a.a(dev.b, integer + 1, dev.b),
			a.a(dev.c, dev.c, integer + 1)
		);
	}

	@Override
	protected DoubleList a(fz.a a) {
		return a == this.c ? d : this.b.a(a);
	}
}
