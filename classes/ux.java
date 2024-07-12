import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class ux extends dfm {
	private final MinecraftServer a;
	private final Set<dfj> b = Sets.<dfj>newHashSet();
	private Runnable[] c = new Runnable[0];

	public ux(MinecraftServer minecraftServer) {
		this.a = minecraftServer;
	}

	@Override
	public void a(dfl dfl) {
		super.a(dfl);
		if (this.b.contains(dfl.d())) {
			this.a.ac().a(new qi(ux.a.CHANGE, dfl.d().b(), dfl.e(), dfl.b()));
		}

		this.b();
	}

	@Override
	public void a(String string) {
		super.a(string);
		this.a.ac().a(new qi(ux.a.REMOVE, null, string, 0));
		this.b();
	}

	@Override
	public void a(String string, dfj dfj) {
		super.a(string, dfj);
		if (this.b.contains(dfj)) {
			this.a.ac().a(new qi(ux.a.REMOVE, dfj.b(), string, 0));
		}

		this.b();
	}

	@Override
	public void a(int integer, @Nullable dfj dfj) {
		dfj dfj4 = this.a(integer);
		super.a(integer, dfj);
		if (dfj4 != dfj && dfj4 != null) {
			if (this.h(dfj4) > 0) {
				this.a.ac().a(new py(integer, dfj));
			} else {
				this.g(dfj4);
			}
		}

		if (dfj != null) {
			if (this.b.contains(dfj)) {
				this.a.ac().a(new py(integer, dfj));
			} else {
				this.e(dfj);
			}
		}

		this.b();
	}

	@Override
	public boolean a(String string, dfk dfk) {
		if (super.a(string, dfk)) {
			this.a.ac().a(new qh(dfk, Arrays.asList(string), 3));
			this.b();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void b(String string, dfk dfk) {
		super.b(string, dfk);
		this.a.ac().a(new qh(dfk, Arrays.asList(string), 4));
		this.b();
	}

	@Override
	public void a(dfj dfj) {
		super.a(dfj);
		this.b();
	}

	@Override
	public void b(dfj dfj) {
		super.b(dfj);
		if (this.b.contains(dfj)) {
			this.a.ac().a(new qf(dfj, 2));
		}

		this.b();
	}

	@Override
	public void c(dfj dfj) {
		super.c(dfj);
		if (this.b.contains(dfj)) {
			this.g(dfj);
		}

		this.b();
	}

	@Override
	public void a(dfk dfk) {
		super.a(dfk);
		this.a.ac().a(new qh(dfk, 0));
		this.b();
	}

	@Override
	public void b(dfk dfk) {
		super.b(dfk);
		this.a.ac().a(new qh(dfk, 2));
		this.b();
	}

	@Override
	public void c(dfk dfk) {
		super.c(dfk);
		this.a.ac().a(new qh(dfk, 1));
		this.b();
	}

	public void a(Runnable runnable) {
		this.c = (Runnable[])Arrays.copyOf(this.c, this.c.length + 1);
		this.c[this.c.length - 1] = runnable;
	}

	protected void b() {
		for (Runnable runnable5 : this.c) {
			runnable5.run();
		}
	}

	public List<ni<?>> d(dfj dfj) {
		List<ni<?>> list3 = Lists.<ni<?>>newArrayList();
		list3.add(new qf(dfj, 0));

		for (int integer4 = 0; integer4 < 19; integer4++) {
			if (this.a(integer4) == dfj) {
				list3.add(new py(integer4, dfj));
			}
		}

		for (dfl dfl5 : this.i(dfj)) {
			list3.add(new qi(ux.a.CHANGE, dfl5.d().b(), dfl5.e(), dfl5.b()));
		}

		return list3;
	}

	public void e(dfj dfj) {
		List<ni<?>> list3 = this.d(dfj);

		for (ze ze5 : this.a.ac().s()) {
			for (ni<?> ni7 : list3) {
				ze5.b.a(ni7);
			}
		}

		this.b.add(dfj);
	}

	public List<ni<?>> f(dfj dfj) {
		List<ni<?>> list3 = Lists.<ni<?>>newArrayList();
		list3.add(new qf(dfj, 1));

		for (int integer4 = 0; integer4 < 19; integer4++) {
			if (this.a(integer4) == dfj) {
				list3.add(new py(integer4, dfj));
			}
		}

		return list3;
	}

	public void g(dfj dfj) {
		List<ni<?>> list3 = this.f(dfj);

		for (ze ze5 : this.a.ac().s()) {
			for (ni<?> ni7 : list3) {
				ze5.b.a(ni7);
			}
		}

		this.b.remove(dfj);
	}

	public int h(dfj dfj) {
		int integer3 = 0;

		for (int integer4 = 0; integer4 < 19; integer4++) {
			if (this.a(integer4) == dfj) {
				integer3++;
			}
		}

		return integer3;
	}

	public static enum a {
		CHANGE,
		REMOVE;
	}
}
