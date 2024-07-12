import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class acp extends aco {
	private static final Logger k = LogManager.getLogger();

	public int a(Collection<bmu<?>> collection, ze ze) {
		List<uh> list4 = Lists.<uh>newArrayList();
		int integer5 = 0;

		for (bmu<?> bmu7 : collection) {
			uh uh8 = bmu7.f();
			if (!this.a.contains(uh8) && !bmu7.ah_()) {
				this.a(uh8);
				this.d(uh8);
				list4.add(uh8);
				aa.f.a(ze, bmu7);
				integer5++;
			}
		}

		this.a(pl.a.ADD, ze, list4);
		return integer5;
	}

	public int b(Collection<bmu<?>> collection, ze ze) {
		List<uh> list4 = Lists.<uh>newArrayList();
		int integer5 = 0;

		for (bmu<?> bmu7 : collection) {
			uh uh8 = bmu7.f();
			if (this.a.contains(uh8)) {
				this.c(uh8);
				list4.add(uh8);
				integer5++;
			}
		}

		this.a(pl.a.REMOVE, ze, list4);
		return integer5;
	}

	private void a(pl.a a, ze ze, List<uh> list) {
		ze.b.a(new pl(a, list, Collections.emptyList(), this.c, this.d, this.e, this.f));
	}

	public le i() {
		le le2 = new le();
		le2.a("isGuiOpen", this.c);
		le2.a("isFilteringCraftable", this.d);
		le2.a("isFurnaceGuiOpen", this.e);
		le2.a("isFurnaceFilteringCraftable", this.f);
		le2.a("isBlastingFurnaceGuiOpen", this.g);
		le2.a("isBlastingFurnaceFilteringCraftable", this.h);
		le2.a("isSmokerGuiOpen", this.i);
		le2.a("isSmokerFilteringCraftable", this.j);
		lk lk3 = new lk();

		for (uh uh5 : this.a) {
			lk3.add(lt.a(uh5.toString()));
		}

		le2.a("recipes", lk3);
		lk lk4 = new lk();

		for (uh uh6 : this.b) {
			lk4.add(lt.a(uh6.toString()));
		}

		le2.a("toBeDisplayed", lk4);
		return le2;
	}

	public void a(le le, bmv bmv) {
		this.c = le.q("isGuiOpen");
		this.d = le.q("isFilteringCraftable");
		this.e = le.q("isFurnaceGuiOpen");
		this.f = le.q("isFurnaceFilteringCraftable");
		this.g = le.q("isBlastingFurnaceGuiOpen");
		this.h = le.q("isBlastingFurnaceFilteringCraftable");
		this.i = le.q("isSmokerGuiOpen");
		this.j = le.q("isSmokerFilteringCraftable");
		lk lk4 = le.d("recipes", 8);
		this.a(lk4, this::a, bmv);
		lk lk5 = le.d("toBeDisplayed", 8);
		this.a(lk5, this::f, bmv);
	}

	private void a(lk lk, Consumer<bmu<?>> consumer, bmv bmv) {
		for (int integer5 = 0; integer5 < lk.size(); integer5++) {
			String string6 = lk.j(integer5);

			try {
				uh uh7 = new uh(string6);
				Optional<? extends bmu<?>> optional8 = bmv.a(uh7);
				if (!optional8.isPresent()) {
					k.error("Tried to load unrecognized recipe: {} removed now.", uh7);
				} else {
					consumer.accept(optional8.get());
				}
			} catch (t var8) {
				k.error("Tried to load improperly formatted recipe: {} removed now.", string6);
			}
		}
	}

	public void a(ze ze) {
		ze.b.a(new pl(pl.a.INIT, this.a, this.b, this.c, this.d, this.e, this.f));
	}
}
