import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class qv implements ni<nl> {
	private List<bmu<?>> a;

	public qv() {
	}

	public qv(Collection<bmu<?>> collection) {
		this.a = Lists.<bmu<?>>newArrayList(collection);
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = Lists.<bmu<?>>newArrayList();
		int integer3 = mg.i();

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			this.a.add(c(mg));
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a.size());

		for (bmu<?> bmu4 : this.a) {
			a(bmu4, mg);
		}
	}

	public static bmu<?> c(mg mg) {
		uh uh2 = mg.o();
		uh uh3 = mg.o();
		return ((bmw)gl.aO.b(uh2).orElseThrow(() -> new IllegalArgumentException("Unknown recipe serializer " + uh2))).a(uh3, mg);
	}

	public static <T extends bmu<?>> void a(T bmu, mg mg) {
		mg.a(gl.aO.b(bmu.ai_()));
		mg.a(bmu.f());
		((bmw<T>)bmu.ai_()).a(mg, bmu);
	}
}
