import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.minecraft.server.MinecraftServer;

public class uu {
	private static final uh a = new uh("tick");
	private static final uh b = new uh("load");
	private final MinecraftServer c;
	private boolean d;
	private final ArrayDeque<uu.a> e = new ArrayDeque();
	private final List<uu.a> f = Lists.<uu.a>newArrayList();
	private final List<cw> g = Lists.<cw>newArrayList();
	private boolean h;
	private ut i;

	public uu(MinecraftServer minecraftServer, ut ut) {
		this.c = minecraftServer;
		this.i = ut;
		this.b(ut);
	}

	public int b() {
		return this.c.aJ().c(bpx.v);
	}

	public CommandDispatcher<cz> c() {
		return this.c.aB().a();
	}

	public void d() {
		this.a(this.g, a);
		if (this.h) {
			this.h = false;
			Collection<cw> collection2 = this.i.b().b(b).b();
			this.a(collection2, b);
		}
	}

	private void a(Collection<cw> collection, uh uh) {
		this.c.aO().a(uh::toString);

		for (cw cw5 : collection) {
			this.a(cw5, this.e());
		}

		this.c.aO().c();
	}

	public int a(cw cw, cz cz) {
		int integer4 = this.b();
		if (this.d) {
			if (this.e.size() + this.f.size() < integer4) {
				this.f.add(new uu.a(this, cz, new cw.d(cw)));
			}

			return 0;
		} else {
			int var16;
			try {
				this.d = true;
				int integer5 = 0;
				cw.c[] arr6 = cw.b();

				for (int integer7 = arr6.length - 1; integer7 >= 0; integer7--) {
					this.e.push(new uu.a(this, cz, arr6[integer7]));
				}

				do {
					if (this.e.isEmpty()) {
						return integer5;
					}

					try {
						uu.a a7 = (uu.a)this.e.removeFirst();
						this.c.aO().a(a7::toString);
						a7.a(this.e, integer4);
						if (!this.f.isEmpty()) {
							Lists.reverse(this.f).forEach(this.e::addFirst);
							this.f.clear();
						}
					} finally {
						this.c.aO().c();
					}
				} while (++integer5 < integer4);

				var16 = integer5;
			} finally {
				this.e.clear();
				this.f.clear();
				this.d = false;
			}

			return var16;
		}
	}

	public void a(ut ut) {
		this.i = ut;
		this.b(ut);
	}

	private void b(ut ut) {
		this.g.clear();
		this.g.addAll(ut.b().b(a).b());
		this.h = true;
	}

	public cz e() {
		return this.c.aC().a(2).a();
	}

	public Optional<cw> a(uh uh) {
		return this.i.a(uh);
	}

	public adf<cw> b(uh uh) {
		return this.i.b(uh);
	}

	public Iterable<uh> f() {
		return this.i.a().keySet();
	}

	public Iterable<uh> g() {
		return this.i.b().a();
	}

	public static class a {
		private final uu a;
		private final cz b;
		private final cw.c c;

		public a(uu uu, cz cz, cw.c c) {
			this.a = uu;
			this.b = cz;
			this.c = c;
		}

		public void a(ArrayDeque<uu.a> arrayDeque, int integer) {
			try {
				this.c.a(this.a, this.b, arrayDeque, integer);
			} catch (Throwable var4) {
			}
		}

		public String toString() {
			return this.c.toString();
		}
	}
}
