import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;

public class ms {
	public static mx a(mx mx, nb nb) {
		if (nb.g()) {
			return mx;
		} else {
			nb nb3 = mx.c();
			if (nb3.g()) {
				return mx.a(nb);
			} else {
				return nb3.equals(nb) ? mx : mx.a(nb3.a(nb));
			}
		}
	}

	public static mx a(@Nullable cz cz, mr mr, @Nullable aom aom, int integer) throws CommandSyntaxException {
		if (integer > 100) {
			return mr.e();
		} else {
			mx mx5 = mr instanceof mt ? ((mt)mr).a(cz, aom, integer + 1) : mr.f();

			for (mr mr7 : mr.b()) {
				mx5.a(a(cz, mr7, aom, integer + 1));
			}

			return mx5.c(a(cz, mr.c(), aom, integer));
		}
	}

	private static nb a(@Nullable cz cz, nb nb, @Nullable aom aom, int integer) throws CommandSyntaxException {
		mv mv5 = nb.i();
		if (mv5 != null) {
			mr mr6 = mv5.a(mv.a.a);
			if (mr6 != null) {
				mv mv7 = new mv(mv.a.a, a(cz, mr6, aom, integer + 1));
				return nb.a(mv7);
			}
		}

		return nb;
	}

	public static mr a(GameProfile gameProfile) {
		if (gameProfile.getName() != null) {
			return new nd(gameProfile.getName());
		} else {
			return gameProfile.getId() != null ? new nd(gameProfile.getId().toString()) : new nd("(unknown)");
		}
	}

	public static mr a(Collection<String> collection) {
		return a(collection, string -> new nd(string).a(i.GREEN));
	}

	public static <T extends Comparable<T>> mr a(Collection<T> collection, Function<T, mr> function) {
		if (collection.isEmpty()) {
			return nd.d;
		} else if (collection.size() == 1) {
			return (mr)function.apply(collection.iterator().next());
		} else {
			List<T> list3 = Lists.<T>newArrayList(collection);
			list3.sort(Comparable::compareTo);
			return b(list3, function);
		}
	}

	public static <T> mx b(Collection<T> collection, Function<T, mr> function) {
		if (collection.isEmpty()) {
			return new nd("");
		} else if (collection.size() == 1) {
			return ((mr)function.apply(collection.iterator().next())).e();
		} else {
			mx mx3 = new nd("");
			boolean boolean4 = true;

			for (T object6 : collection) {
				if (!boolean4) {
					mx3.a(new nd(", ").a(i.GRAY));
				}

				mx3.a((mr)function.apply(object6));
				boolean4 = false;
			}

			return mx3;
		}
	}

	public static mx a(mr mr) {
		return new nd("[").a(mr).c("]");
	}

	public static mr a(Message message) {
		return (mr)(message instanceof mr ? (mr)message : new nd(message.getString()));
	}
}
