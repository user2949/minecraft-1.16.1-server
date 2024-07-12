import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class lq {
	private static final Logger a = LogManager.getLogger();

	@Nullable
	public static GameProfile a(le le) {
		String string2 = null;
		UUID uUID3 = null;
		if (le.c("Name", 8)) {
			string2 = le.l("Name");
		}

		if (le.b("Id")) {
			uUID3 = le.a("Id");
		}

		try {
			GameProfile gameProfile4 = new GameProfile(uUID3, string2);
			if (le.c("Properties", 10)) {
				le le5 = le.p("Properties");

				for (String string7 : le5.d()) {
					lk lk8 = le5.d(string7, 10);

					for (int integer9 = 0; integer9 < lk8.size(); integer9++) {
						le le10 = lk8.a(integer9);
						String string11 = le10.l("Value");
						if (le10.c("Signature", 8)) {
							gameProfile4.getProperties().put(string7, new Property(string7, string11, le10.l("Signature")));
						} else {
							gameProfile4.getProperties().put(string7, new Property(string7, string11));
						}
					}
				}
			}

			return gameProfile4;
		} catch (Throwable var11) {
			return null;
		}
	}

	public static le a(le le, GameProfile gameProfile) {
		if (!aei.b(gameProfile.getName())) {
			le.a("Name", gameProfile.getName());
		}

		if (gameProfile.getId() != null) {
			le.a("Id", gameProfile.getId());
		}

		if (!gameProfile.getProperties().isEmpty()) {
			le le3 = new le();

			for (String string5 : gameProfile.getProperties().keySet()) {
				lk lk6 = new lk();

				for (Property property8 : gameProfile.getProperties().get(string5)) {
					le le9 = new le();
					le9.a("Value", property8.getValue());
					if (property8.hasSignature()) {
						le9.a("Signature", property8.getSignature());
					}

					lk6.add(le9);
				}

				le3.a(string5, lk6);
			}

			le.a("Properties", le3);
		}

		return le;
	}

	@VisibleForTesting
	public static boolean a(@Nullable lu lu1, @Nullable lu lu2, boolean boolean3) {
		if (lu1 == lu2) {
			return true;
		} else if (lu1 == null) {
			return true;
		} else if (lu2 == null) {
			return false;
		} else if (!lu1.getClass().equals(lu2.getClass())) {
			return false;
		} else if (lu1 instanceof le) {
			le le4 = (le)lu1;
			le le5 = (le)lu2;

			for (String string7 : le4.d()) {
				lu lu8 = le4.c(string7);
				if (!a(lu8, le5.c(string7), boolean3)) {
					return false;
				}
			}

			return true;
		} else if (lu1 instanceof lk && boolean3) {
			lk lk4 = (lk)lu1;
			lk lk5 = (lk)lu2;
			if (lk4.isEmpty()) {
				return lk5.isEmpty();
			} else {
				for (int integer6 = 0; integer6 < lk4.size(); integer6++) {
					lu lu7 = lk4.k(integer6);
					boolean boolean8 = false;

					for (int integer9 = 0; integer9 < lk5.size(); integer9++) {
						if (a(lu7, lk5.k(integer9), boolean3)) {
							boolean8 = true;
							break;
						}
					}

					if (!boolean8) {
						return false;
					}
				}

				return true;
			}
		} else {
			return lu1.equals(lu2);
		}
	}

	public static li a(UUID uUID) {
		return new li(gp.a(uUID));
	}

	public static UUID a(lu lu) {
		if (lu.b() != li.a) {
			throw new IllegalArgumentException("Expected UUID-Tag to be of type " + li.a.a() + ", but found " + lu.b().a() + ".");
		} else {
			int[] arr2 = ((li)lu).g();
			if (arr2.length != 4) {
				throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + arr2.length + ".");
			} else {
				return gp.a(arr2);
			}
		}
	}

	public static fu b(le le) {
		return new fu(le.h("X"), le.h("Y"), le.h("Z"));
	}

	public static le a(fu fu) {
		le le2 = new le();
		le2.b("X", fu.u());
		le2.b("Y", fu.v());
		le2.b("Z", fu.w());
		return le2;
	}

	public static cfj c(le le) {
		if (!le.c("Name", 8)) {
			return bvs.a.n();
		} else {
			bvr bvr2 = gl.aj.a(new uh(le.l("Name")));
			cfj cfj3 = bvr2.n();
			if (le.c("Properties", 10)) {
				le le4 = le.p("Properties");
				cfk<bvr, cfj> cfk5 = bvr2.m();

				for (String string7 : le4.d()) {
					cgl<?> cgl8 = cfk5.a(string7);
					if (cgl8 != null) {
						cfj3 = a(cfj3, cgl8, string7, le4, le);
					}
				}
			}

			return cfj3;
		}
	}

	private static <S extends cfl<?, S>, T extends Comparable<T>> S a(S cfl, cgl<T> cgl, String string, le le4, le le5) {
		Optional<T> optional6 = cgl.b(le4.l(string));
		if (optional6.isPresent()) {
			return cfl.a(cgl, (Comparable)optional6.get());
		} else {
			a.warn("Unable to read property: {} with value: {} for blockstate: {}", string, le4.l(string), le5.toString());
			return cfl;
		}
	}

	public static le a(cfj cfj) {
		le le2 = new le();
		le2.a("Name", gl.aj.b(cfj.b()).toString());
		ImmutableMap<cgl<?>, Comparable<?>> immutableMap3 = cfj.s();
		if (!immutableMap3.isEmpty()) {
			le le4 = new le();

			for (Entry<cgl<?>, Comparable<?>> entry6 : immutableMap3.entrySet()) {
				cgl<?> cgl7 = (cgl<?>)entry6.getKey();
				le4.a(cgl7.f(), a(cgl7, (Comparable<?>)entry6.getValue()));
			}

			le2.a("Properties", le4);
		}

		return le2;
	}

	private static <T extends Comparable<T>> String a(cgl<T> cgl, Comparable<?> comparable) {
		return cgl.a((T)comparable);
	}

	public static le a(DataFixer dataFixer, aeo aeo, le le, int integer) {
		return a(dataFixer, aeo, le, integer, u.a().getWorldVersion());
	}

	public static le a(DataFixer dataFixer, aeo aeo, le le, int integer4, int integer5) {
		return (le)dataFixer.update(aeo.a(), new Dynamic<>(lp.a, le), integer4, integer5).getValue();
	}
}
