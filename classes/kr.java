import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;

public class kr {
	public static String a = "gameteststructures";

	public static cap a(int integer) {
		switch (integer) {
			case 0:
				return cap.NONE;
			case 1:
				return cap.CLOCKWISE_90;
			case 2:
				return cap.CLOCKWISE_180;
			case 3:
				return cap.COUNTERCLOCKWISE_90;
			default:
				throw new IllegalArgumentException("rotationSteps must be a value from 0-3. Got value " + integer);
		}
	}

	public static deg a(cel cel) {
		fu fu2 = cel.o();
		fu fu3 = fu2.a(cel.j().b(-1, -1, -1));
		fu fu4 = cve.a(fu3, bzj.NONE, cel.l(), fu2);
		return new deg(fu2, fu4);
	}

	public static ctd b(cel cel) {
		fu fu2 = cel.o();
		fu fu3 = fu2.a(cel.j().b(-1, -1, -1));
		fu fu4 = cve.a(fu3, bzj.NONE, cel.l(), fu2);
		return new ctd(fu2, fu4);
	}

	public static void a(fu fu1, fu fu2, cap cap, zd zd) {
		fu fu5 = cve.a(fu1.a(fu2), bzj.NONE, cap, fu1);
		zd.a(fu5, bvs.er.n());
		cdq cdq6 = (cdq)zd.c(fu5);
		cdq6.d().a("test runthis");
		fu fu7 = cve.a(fu5.b(0, 0, -1), bzj.NONE, cap, fu5);
		zd.a(fu7, bvs.cB.n().a(cap));
	}

	public static void a(String string, fu fu2, fu fu3, cap cap, zd zd) {
		ctd ctd6 = a(fu2, fu3, cap);
		a(ctd6, fu2.v(), zd);
		zd.a(fu2, bvs.mY.n());
		cel cel7 = (cel)zd.c(fu2);
		cel7.a(false);
		cel7.a(new uh(string));
		cel7.c(fu3);
		cel7.a(cgq.SAVE);
		cel7.f(true);
	}

	public static cel a(String string, fu fu, cap cap, int integer, zd zd, boolean boolean6) {
		fu fu7 = a(string, zd).a();
		ctd ctd8 = a(fu, fu7, cap);
		fu fu9;
		if (cap == cap.NONE) {
			fu9 = fu;
		} else if (cap == cap.CLOCKWISE_90) {
			fu9 = fu.b(fu7.w() - 1, 0, 0);
		} else if (cap == cap.CLOCKWISE_180) {
			fu9 = fu.b(fu7.u() - 1, 0, fu7.w() - 1);
		} else {
			if (cap != cap.COUNTERCLOCKWISE_90) {
				throw new IllegalArgumentException("Invalid rotation: " + cap);
			}

			fu9 = fu.b(0, 0, fu7.u() - 1);
		}

		a(fu, zd);
		a(ctd8, fu.v(), zd);
		cel cel10 = a(string, fu9, cap, zd, boolean6);
		zd.j().a(ctd8, true, false);
		zd.a(ctd8);
		return cel10;
	}

	private static void a(fu fu, zd zd) {
		bph bph3 = new bph(fu);

		for (int integer4 = -1; integer4 < 4; integer4++) {
			for (int integer5 = -1; integer5 < 4; integer5++) {
				int integer6 = bph3.b + integer4;
				int integer7 = bph3.c + integer5;
				zd.a(integer6, integer7, true);
			}
		}
	}

	public static void a(ctd ctd, int integer, zd zd) {
		ctd ctd4 = new ctd(ctd.a - 2, ctd.b - 3, ctd.c - 3, ctd.d + 3, ctd.e + 20, ctd.f + 3);
		fu.a(ctd4).forEach(fu -> a(integer, fu, zd));
		zd.j().a(ctd4, true, false);
		zd.a(ctd4);
		deg deg5 = new deg((double)ctd4.a, (double)ctd4.b, (double)ctd4.c, (double)ctd4.d, (double)ctd4.e, (double)ctd4.f);
		List<aom> list6 = zd.a(aom.class, deg5, aom -> !(aom instanceof bec));
		list6.forEach(aom::aa);
	}

	public static ctd a(fu fu1, fu fu2, cap cap) {
		fu fu4 = fu1.a(fu2).b(-1, -1, -1);
		fu fu5 = cve.a(fu4, bzj.NONE, cap, fu1);
		ctd ctd6 = ctd.a(fu1.u(), fu1.v(), fu1.w(), fu5.u(), fu5.v(), fu5.w());
		int integer7 = Math.min(ctd6.a, ctd6.d);
		int integer8 = Math.min(ctd6.c, ctd6.f);
		fu fu9 = new fu(fu1.u() - integer7, 0, fu1.w() - integer8);
		ctd6.a(fu9);
		return ctd6;
	}

	public static Optional<fu> a(fu fu, int integer, zd zd) {
		return c(fu, integer, zd).stream().filter(fu3 -> a(fu3, fu, zd)).findFirst();
	}

	@Nullable
	public static fu b(fu fu, int integer, zd zd) {
		Comparator<fu> comparator4 = Comparator.comparingInt(fu2 -> fu2.k(fu));
		Collection<fu> collection5 = c(fu, integer, zd);
		Optional<fu> optional6 = collection5.stream().min(comparator4);
		return (fu)optional6.orElse(null);
	}

	public static Collection<fu> c(fu fu, int integer, zd zd) {
		Collection<fu> collection4 = Lists.<fu>newArrayList();
		deg deg5 = new deg(fu);
		deg5 = deg5.g((double)integer);

		for (int integer6 = (int)deg5.a; integer6 <= (int)deg5.d; integer6++) {
			for (int integer7 = (int)deg5.b; integer7 <= (int)deg5.e; integer7++) {
				for (int integer8 = (int)deg5.c; integer8 <= (int)deg5.f; integer8++) {
					fu fu9 = new fu(integer6, integer7, integer8);
					cfj cfj10 = zd.d_(fu9);
					if (cfj10.a(bvs.mY)) {
						collection4.add(fu9);
					}
				}
			}
		}

		return collection4;
	}

	private static cve a(String string, zd zd) {
		cva cva3 = zd.r_();
		cve cve4 = cva3.b(new uh(string));
		if (cve4 != null) {
			return cve4;
		} else {
			String string5 = string + ".snbt";
			Path path6 = Paths.get(a, string5);
			le le7 = a(path6);
			if (le7 == null) {
				throw new RuntimeException("Could not find structure file " + path6 + ", and the structure is not available in the world structures either.");
			} else {
				return cva3.a(le7);
			}
		}
	}

	private static cel a(String string, fu fu, cap cap, zd zd, boolean boolean5) {
		zd.a(fu, bvs.mY.n());
		cel cel6 = (cel)zd.c(fu);
		cel6.a(cgq.LOAD);
		cel6.b(cap);
		cel6.a(false);
		cel6.a(new uh(string));
		cel6.c(boolean5);
		if (cel6.j() != fu.b) {
			return cel6;
		} else {
			cve cve7 = a(string, zd);
			cel6.a(boolean5, cve7);
			if (cel6.j() == fu.b) {
				throw new RuntimeException("Failed to load structure " + string);
			} else {
				return cel6;
			}
		}
	}

	@Nullable
	private static le a(Path path) {
		try {
			BufferedReader bufferedReader2 = Files.newBufferedReader(path);
			String string3 = IOUtils.toString(bufferedReader2);
			return lv.a(string3);
		} catch (IOException var3) {
			return null;
		} catch (CommandSyntaxException var4) {
			throw new RuntimeException("Error while trying to load structure " + path, var4);
		}
	}

	private static void a(int integer, fu fu, zd zd) {
		cfj cfj4 = null;
		cra cra5 = cra.i();
		if (cra5 instanceof cra) {
			cfj[] arr6 = cra5.g();
			if (fu.v() < integer && fu.v() <= arr6.length) {
				cfj4 = arr6[fu.v() - 1];
			}
		} else if (fu.v() == integer - 1) {
			cfj4 = zd.v(fu).A().a();
		} else if (fu.v() < integer - 1) {
			cfj4 = zd.v(fu).A().b();
		}

		if (cfj4 == null) {
			cfj4 = bvs.a.n();
		}

		ec ec6 = new ec(cfj4, Collections.emptySet(), null);
		ec6.a(zd, fu, 2);
		zd.a(fu, cfj4.b());
	}

	private static boolean a(fu fu1, fu fu2, zd zd) {
		cel cel4 = (cel)zd.c(fu1);
		deg deg5 = a(cel4).g(1.0);
		return deg5.d(dem.a(fu2));
	}
}
