import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;

public class qh implements ni<nl> {
	private String a = "";
	private mr b;
	private mr c;
	private mr d;
	private String e;
	private String f;
	private i g;
	private final Collection<String> h;
	private int i;
	private int j;

	public qh() {
		this.b = nd.d;
		this.c = nd.d;
		this.d = nd.d;
		this.e = dfo.b.ALWAYS.e;
		this.f = dfo.a.ALWAYS.e;
		this.g = i.RESET;
		this.h = Lists.<String>newArrayList();
	}

	public qh(dfk dfk, int integer) {
		this.b = nd.d;
		this.c = nd.d;
		this.d = nd.d;
		this.e = dfo.b.ALWAYS.e;
		this.f = dfo.a.ALWAYS.e;
		this.g = i.RESET;
		this.h = Lists.<String>newArrayList();
		this.a = dfk.b();
		this.i = integer;
		if (integer == 0 || integer == 2) {
			this.b = dfk.c();
			this.j = dfk.m();
			this.e = dfk.j().e;
			this.f = dfk.l().e;
			this.g = dfk.n();
			this.c = dfk.e();
			this.d = dfk.f();
		}

		if (integer == 0) {
			this.h.addAll(dfk.g());
		}
	}

	public qh(dfk dfk, Collection<String> collection, int integer) {
		this.b = nd.d;
		this.c = nd.d;
		this.d = nd.d;
		this.e = dfo.b.ALWAYS.e;
		this.f = dfo.a.ALWAYS.e;
		this.g = i.RESET;
		this.h = Lists.<String>newArrayList();
		if (integer != 3 && integer != 4) {
			throw new IllegalArgumentException("Method must be join or leave for player constructor");
		} else if (collection != null && !collection.isEmpty()) {
			this.i = integer;
			this.a = dfk.b();
			this.h.addAll(collection);
		} else {
			throw new IllegalArgumentException("Players cannot be null/empty");
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e(16);
		this.i = mg.readByte();
		if (this.i == 0 || this.i == 2) {
			this.b = mg.h();
			this.j = mg.readByte();
			this.e = mg.e(40);
			this.f = mg.e(40);
			this.g = mg.a(i.class);
			this.c = mg.h();
			this.d = mg.h();
		}

		if (this.i == 0 || this.i == 3 || this.i == 4) {
			int integer3 = mg.i();

			for (int integer4 = 0; integer4 < integer3; integer4++) {
				this.h.add(mg.e(40));
			}
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.writeByte(this.i);
		if (this.i == 0 || this.i == 2) {
			mg.a(this.b);
			mg.writeByte(this.j);
			mg.a(this.e);
			mg.a(this.f);
			mg.a(this.g);
			mg.a(this.c);
			mg.a(this.d);
		}

		if (this.i == 0 || this.i == 3 || this.i == 4) {
			mg.d(this.h.size());

			for (String string4 : this.h) {
				mg.a(string4);
			}
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
