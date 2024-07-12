import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class mz extends mn implements mt {
	private final String d;
	@Nullable
	private final ez e;
	private final String f;

	@Nullable
	private static ez d(String string) {
		try {
			return new fa(new StringReader(string)).t();
		} catch (CommandSyntaxException var2) {
			return null;
		}
	}

	public mz(String string1, String string2) {
		this(string1, d(string1), string2);
	}

	private mz(String string1, @Nullable ez ez, String string3) {
		this.d = string1;
		this.e = ez;
		this.f = string3;
	}

	public String g() {
		return this.d;
	}

	public String i() {
		return this.f;
	}

	private String a(cz cz) throws CommandSyntaxException {
		if (this.e != null) {
			List<? extends aom> list3 = this.e.b(cz);
			if (!list3.isEmpty()) {
				if (list3.size() != 1) {
					throw dh.a.create();
				}

				return ((aom)list3.get(0)).bT();
			}
		}

		return this.d;
	}

	private String a(String string, cz cz) {
		MinecraftServer minecraftServer4 = cz.j();
		if (minecraftServer4 != null) {
			dfm dfm5 = minecraftServer4.aF();
			dfj dfj6 = dfm5.d(this.f);
			if (dfm5.b(string, dfj6)) {
				dfl dfl7 = dfm5.c(string, dfj6);
				return Integer.toString(dfl7.b());
			}
		}

		return "";
	}

	public mz f() {
		return new mz(this.d, this.e, this.f);
	}

	@Override
	public mx a(@Nullable cz cz, @Nullable aom aom, int integer) throws CommandSyntaxException {
		if (cz == null) {
			return new nd("");
		} else {
			String string5 = this.a(cz);
			String string6 = aom != null && string5.equals("*") ? aom.bT() : string5;
			return new nd(this.a(string6, cz));
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof mz)) {
			return false;
		} else {
			mz mz3 = (mz)object;
			return this.d.equals(mz3.d) && this.f.equals(mz3.f) && super.equals(object);
		}
	}

	@Override
	public String toString() {
		return "ScoreComponent{name='" + this.d + '\'' + "objective='" + this.f + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
	}
}
