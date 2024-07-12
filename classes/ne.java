import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class ne extends mn implements mt {
	private static final Object[] d = new Object[0];
	private static final mu e = mu.b("%");
	private static final mu f = mu.b("null");
	private final String g;
	private final Object[] h;
	@Nullable
	private kz i;
	private final List<mu> j = Lists.<mu>newArrayList();
	private static final Pattern k = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

	public ne(String string) {
		this.g = string;
		this.h = d;
	}

	public ne(String string, Object... arr) {
		this.g = string;
		this.h = arr;
	}

	private void j() {
		kz kz2 = kz.a();
		if (kz2 != this.i) {
			this.i = kz2;
			this.j.clear();
			String string3 = kz2.a(this.g);

			try {
				this.a(kz2.a(string3, true), kz2);
			} catch (nf var4) {
				this.j.clear();
				this.j.add(mu.b(string3));
			}
		}
	}

	private void a(String string, kz kz) {
		Matcher matcher4 = k.matcher(string);

		try {
			int integer5 = 0;
			int integer6 = 0;

			while (matcher4.find(integer6)) {
				int integer7 = matcher4.start();
				int integer8 = matcher4.end();
				if (integer7 > integer6) {
					String string9 = string.substring(integer6, integer7);
					if (string9.indexOf(37) != -1) {
						throw new IllegalArgumentException();
					}

					this.j.add(mu.b(string9));
				}

				String string9 = matcher4.group(2);
				String string10 = string.substring(integer7, integer8);
				if ("%".equals(string9) && "%%".equals(string10)) {
					this.j.add(e);
				} else {
					if (!"s".equals(string9)) {
						throw new nf(this, "Unsupported format: '" + string10 + "'");
					}

					String string11 = matcher4.group(1);
					int integer12 = string11 != null ? Integer.parseInt(string11) - 1 : integer5++;
					if (integer12 < this.h.length) {
						this.j.add(this.a(integer12, kz));
					}
				}

				integer6 = integer8;
			}

			if (integer6 < string.length()) {
				String string7 = string.substring(integer6);
				if (string7.indexOf(37) != -1) {
					throw new IllegalArgumentException();
				}

				this.j.add(mu.b(string7));
			}
		} catch (IllegalArgumentException var12) {
			throw new nf(this, var12);
		}
	}

	private mu a(int integer, kz kz) {
		if (integer >= this.h.length) {
			throw new nf(this, integer);
		} else {
			Object object4 = this.h[integer];
			if (object4 instanceof mr) {
				return (mr)object4;
			} else {
				return object4 == null ? f : mu.b(kz.a(object4.toString(), false));
			}
		}
	}

	public ne f() {
		return new ne(this.g, this.h);
	}

	@Override
	public <T> Optional<T> b(mu.a<T> a) {
		this.j();

		for (mu mu4 : this.j) {
			Optional<T> optional5 = mu4.a(a);
			if (optional5.isPresent()) {
				return optional5;
			}
		}

		return Optional.empty();
	}

	@Override
	public mx a(@Nullable cz cz, @Nullable aom aom, int integer) throws CommandSyntaxException {
		Object[] arr5 = new Object[this.h.length];

		for (int integer6 = 0; integer6 < arr5.length; integer6++) {
			Object object7 = this.h[integer6];
			if (object7 instanceof mr) {
				arr5[integer6] = ms.a(cz, (mr)object7, aom, integer);
			} else {
				arr5[integer6] = object7;
			}
		}

		return new ne(this.g, arr5);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof ne)) {
			return false;
		} else {
			ne ne3 = (ne)object;
			return Arrays.equals(this.h, ne3.h) && this.g.equals(ne3.g) && super.equals(object);
		}
	}

	@Override
	public int hashCode() {
		int integer2 = super.hashCode();
		integer2 = 31 * integer2 + this.g.hashCode();
		return 31 * integer2 + Arrays.hashCode(this.h);
	}

	@Override
	public String toString() {
		return "TranslatableComponent{key='" + this.g + '\'' + ", args=" + Arrays.toString(this.h) + ", siblings=" + this.a + ", style=" + this.c() + '}';
	}

	public String h() {
		return this.g;
	}

	public Object[] i() {
		return this.h;
	}
}
