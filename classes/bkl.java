import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bkl extends bke {
	private static final Logger a = LogManager.getLogger();

	public bkl(bke.a a) {
		super(a);
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		le le6 = bki5.o();
		if (!bec.bJ.d) {
			bec.a(anf, bki.b);
		}

		if (le6 != null && le6.c("Recipes", 9)) {
			if (!bqb.v) {
				lk lk7 = le6.d("Recipes", 8);
				List<bmu<?>> list8 = Lists.<bmu<?>>newArrayList();
				bmv bmv9 = bqb.l().aD();

				for (int integer10 = 0; integer10 < lk7.size(); integer10++) {
					String string11 = lk7.j(integer10);
					Optional<? extends bmu<?>> optional12 = bmv9.a(new uh(string11));
					if (!optional12.isPresent()) {
						a.error("Invalid recipe: {}", string11);
						return anh.d(bki5);
					}

					list8.add(optional12.get());
				}

				bec.a(list8);
				bec.b(acu.c.b(this));
			}

			return anh.a(bki5, bqb.s_());
		} else {
			a.error("Tag not valid: {}", le6);
			return anh.d(bki5);
		}
	}
}
