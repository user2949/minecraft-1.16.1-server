import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class eu implements Predicate<bki> {
	private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("arguments.item.overstacked", object1, object2)
	);
	private final bke b;
	@Nullable
	private final le c;

	public eu(bke bke, @Nullable le le) {
		this.b = bke;
		this.c = le;
	}

	public bke a() {
		return this.b;
	}

	public boolean test(bki bki) {
		return bki.b() == this.b && lq.a(this.c, bki.o(), true);
	}

	public bki a(int integer, boolean boolean2) throws CommandSyntaxException {
		bki bki4 = new bki(this.b, integer);
		if (this.c != null) {
			bki4.c(this.c);
		}

		if (boolean2 && integer > bki4.c()) {
			throw a.create(gl.am.b(this.b), bki4.c());
		} else {
			return bki4;
		}
	}

	public String c() {
		StringBuilder stringBuilder2 = new StringBuilder(gl.am.a(this.b));
		if (this.c != null) {
			stringBuilder2.append(this.c);
		}

		return stringBuilder2.toString();
	}
}
