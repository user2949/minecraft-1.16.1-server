import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zg extends cwr implements AutoCloseable {
	private static final Logger a = LogManager.getLogger();
	private final amq<Runnable> b;
	private final ObjectList<Pair<zg.a, Runnable>> c = new ObjectArrayList<>();
	private final yp d;
	private final amp<yr.a<Runnable>> e;
	private volatile int f = 5;
	private final AtomicBoolean g = new AtomicBoolean();

	public zg(chl chl, yp yp, boolean boolean3, amq<Runnable> amq, amp<yr.a<Runnable>> amp) {
		super(chl, true, boolean3);
		this.d = yp;
		this.e = amp;
		this.b = amq;
	}

	public void close() {
	}

	@Override
	public int a(int integer, boolean boolean2, boolean boolean3) {
		throw (UnsupportedOperationException)v.c(new UnsupportedOperationException("Ran authomatically on a different thread!"));
	}

	@Override
	public void a(fu fu, int integer) {
		throw (UnsupportedOperationException)v.c(new UnsupportedOperationException("Ran authomatically on a different thread!"));
	}

	@Override
	public void a(fu fu) {
		fu fu3 = fu.h();
		this.a(fu.u() >> 4, fu.w() >> 4, zg.a.POST_UPDATE, v.a((Runnable)(() -> super.a(fu3)), (Supplier<String>)(() -> "checkBlock " + fu3)));
	}

	protected void a(bph bph) {
		this.a(bph.b, bph.c, () -> 0, zg.a.PRE_UPDATE, v.a((Runnable)(() -> {
			super.b(bph, false);
			super.a(bph, false);

			for (int integer3 = -1; integer3 < 17; integer3++) {
				super.a(bqi.BLOCK, go.a(bph, integer3), null, true);
				super.a(bqi.SKY, go.a(bph, integer3), null, true);
			}

			for (int integer3 = 0; integer3 < 16; integer3++) {
				super.a(go.a(bph, integer3), true);
			}
		}), (Supplier<String>)(() -> "updateChunkStatus " + bph + " " + true)));
	}

	@Override
	public void a(go go, boolean boolean2) {
		this.a(
			go.a(),
			go.c(),
			() -> 0,
			zg.a.PRE_UPDATE,
			v.a((Runnable)(() -> super.a(go, boolean2)), (Supplier<String>)(() -> "updateSectionStatus " + go + " " + boolean2))
		);
	}

	@Override
	public void a(bph bph, boolean boolean2) {
		this.a(bph.b, bph.c, zg.a.PRE_UPDATE, v.a((Runnable)(() -> super.a(bph, boolean2)), (Supplier<String>)(() -> "enableLight " + bph + " " + boolean2)));
	}

	@Override
	public void a(bqi bqi, go go, @Nullable chd chd, boolean boolean4) {
		this.a(go.a(), go.c(), () -> 0, zg.a.PRE_UPDATE, v.a((Runnable)(() -> super.a(bqi, go, chd, boolean4)), (Supplier<String>)(() -> "queueData " + go)));
	}

	private void a(int integer1, int integer2, zg.a a, Runnable runnable) {
		this.a(integer1, integer2, this.d.c(bph.a(integer1, integer2)), a, runnable);
	}

	private void a(int integer1, int integer2, IntSupplier intSupplier, zg.a a, Runnable runnable) {
		this.e.a(yr.a(() -> {
			this.c.add(Pair.of(a, runnable));
			if (this.c.size() >= this.f) {
				this.b();
			}
		}, bph.a(integer1, integer2), intSupplier));
	}

	@Override
	public void b(bph bph, boolean boolean2) {
		this.a(bph.b, bph.c, () -> 0, zg.a.PRE_UPDATE, v.a((Runnable)(() -> super.b(bph, boolean2)), (Supplier<String>)(() -> "retainData " + bph)));
	}

	public CompletableFuture<cgy> a(cgy cgy, boolean boolean2) {
		bph bph4 = cgy.g();
		cgy.b(false);
		this.a(bph4.b, bph4.c, zg.a.PRE_UPDATE, v.a((Runnable)(() -> {
			chk[] arr5 = cgy.d();

			for (int integer6 = 0; integer6 < 16; integer6++) {
				chk chk7 = arr5[integer6];
				if (!chk.a(chk7)) {
					super.a(go.a(bph4, integer6), false);
				}
			}

			super.a(bph4, true);
			if (!boolean2) {
				cgy.m().forEach(fu -> super.a(fu, cgy.h(fu)));
			}

			this.d.c(bph4);
		}), (Supplier<String>)(() -> "lightChunk " + bph4 + " " + boolean2)));
		return CompletableFuture.supplyAsync(() -> {
			cgy.b(true);
			super.b(bph4, false);
			return cgy;
		}, runnable -> this.a(bph4.b, bph4.c, zg.a.POST_UPDATE, runnable));
	}

	public void A_() {
		if ((!this.c.isEmpty() || super.a()) && this.g.compareAndSet(false, true)) {
			this.b.a(() -> {
				this.b();
				this.g.set(false);
			});
		}
	}

	private void b() {
		int integer2 = Math.min(this.c.size(), this.f);
		ObjectListIterator<Pair<zg.a, Runnable>> objectListIterator3 = this.c.iterator();

		int integer4;
		for (integer4 = 0; objectListIterator3.hasNext() && integer4 < integer2; integer4++) {
			Pair<zg.a, Runnable> pair5 = (Pair<zg.a, Runnable>)objectListIterator3.next();
			if (pair5.getFirst() == zg.a.PRE_UPDATE) {
				pair5.getSecond().run();
			}
		}

		objectListIterator3.back(integer4);
		super.a(Integer.MAX_VALUE, true, true);

		for (int var5 = 0; objectListIterator3.hasNext() && var5 < integer2; var5++) {
			Pair<zg.a, Runnable> pair5 = (Pair<zg.a, Runnable>)objectListIterator3.next();
			if (pair5.getFirst() == zg.a.POST_UPDATE) {
				pair5.getSecond().run();
			}

			objectListIterator3.remove();
		}
	}

	public void a(int integer) {
		this.f = integer;
	}

	static enum a {
		PRE_UPDATE,
		POST_UPDATE;
	}
}
