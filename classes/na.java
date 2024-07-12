import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class na extends mn implements mt {
	private static final Logger d = LogManager.getLogger();
	private final String e;
	@Nullable
	private final ez f;

	public na(String string) {
		this.e = string;
		ez ez3 = null;

		try {
			fa fa4 = new fa(new StringReader(string));
			ez3 = fa4.t();
		} catch (CommandSyntaxException var4) {
			d.warn("Invalid selector component: {}", string, var4.getMessage());
		}

		this.f = ez3;
	}

	public String g() {
		return this.e;
	}

	@Override
	public mx a(@Nullable cz cz, @Nullable aom aom, int integer) throws CommandSyntaxException {
		return (mx)(cz != null && this.f != null ? ez.a(this.f.b(cz)) : new nd(""));
	}

	@Override
	public String a() {
		return this.e;
	}

	public na f() {
		return new na(this.e);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof na)) {
			return false;
		} else {
			na na3 = (na)object;
			return this.e.equals(na3.e) && super.equals(object);
		}
	}

	@Override
	public String toString() {
		return "SelectorComponent{pattern='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
	}
}
