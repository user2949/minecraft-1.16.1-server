import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class pl implements ni<nl> {
	private pl.a a;
	private List<uh> b;
	private List<uh> c;
	private boolean d;
	private boolean e;
	private boolean f;
	private boolean g;

	public pl() {
	}

	public pl(pl.a a, Collection<uh> collection2, Collection<uh> collection3, boolean boolean4, boolean boolean5, boolean boolean6, boolean boolean7) {
		this.a = a;
		this.b = ImmutableList.copyOf(collection2);
		this.c = ImmutableList.copyOf(collection3);
		this.d = boolean4;
		this.e = boolean5;
		this.f = boolean6;
		this.g = boolean7;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.a(pl.a.class);
		this.d = mg.readBoolean();
		this.e = mg.readBoolean();
		this.f = mg.readBoolean();
		this.g = mg.readBoolean();
		int integer3 = mg.i();
		this.b = Lists.<uh>newArrayList();

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			this.b.add(mg.o());
		}

		if (this.a == pl.a.INIT) {
			integer3 = mg.i();
			this.c = Lists.<uh>newArrayList();

			for (int integer4 = 0; integer4 < integer3; integer4++) {
				this.c.add(mg.o());
			}
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.writeBoolean(this.d);
		mg.writeBoolean(this.e);
		mg.writeBoolean(this.f);
		mg.writeBoolean(this.g);
		mg.d(this.b.size());

		for (uh uh4 : this.b) {
			mg.a(uh4);
		}

		if (this.a == pl.a.INIT) {
			mg.d(this.c.size());

			for (uh uh4 : this.c) {
				mg.a(uh4);
			}
		}
	}

	public static enum a {
		INIT,
		ADD,
		REMOVE;
	}
}
