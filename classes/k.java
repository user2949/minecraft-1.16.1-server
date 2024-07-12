import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class k {
	private final j a;
	private final String b;
	private final List<k.a> c = Lists.<k.a>newArrayList();
	private StackTraceElement[] d = new StackTraceElement[0];

	public k(j j, String string) {
		this.a = j;
		this.b = string;
	}

	public static String a(fu fu) {
		return a(fu.u(), fu.v(), fu.w());
	}

	public static String a(int integer1, int integer2, int integer3) {
		StringBuilder stringBuilder4 = new StringBuilder();

		try {
			stringBuilder4.append(String.format("World: (%d,%d,%d)", integer1, integer2, integer3));
		} catch (Throwable var16) {
			stringBuilder4.append("(Error finding world loc)");
		}

		stringBuilder4.append(", ");

		try {
			int integer5 = integer1 >> 4;
			int integer6 = integer3 >> 4;
			int integer7 = integer1 & 15;
			int integer8 = integer2 >> 4;
			int integer9 = integer3 & 15;
			int integer10 = integer5 << 4;
			int integer11 = integer6 << 4;
			int integer12 = (integer5 + 1 << 4) - 1;
			int integer13 = (integer6 + 1 << 4) - 1;
			stringBuilder4.append(
				String.format(
					"Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)",
					integer7,
					integer8,
					integer9,
					integer5,
					integer6,
					integer10,
					integer11,
					integer12,
					integer13
				)
			);
		} catch (Throwable var15) {
			stringBuilder4.append("(Error finding chunk loc)");
		}

		stringBuilder4.append(", ");

		try {
			int integer5 = integer1 >> 9;
			int integer6 = integer3 >> 9;
			int integer7 = integer5 << 5;
			int integer8 = integer6 << 5;
			int integer9 = (integer5 + 1 << 5) - 1;
			int integer10 = (integer6 + 1 << 5) - 1;
			int integer11 = integer5 << 9;
			int integer12 = integer6 << 9;
			int integer13 = (integer5 + 1 << 9) - 1;
			int integer14 = (integer6 + 1 << 9) - 1;
			stringBuilder4.append(
				String.format(
					"Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)",
					integer5,
					integer6,
					integer7,
					integer8,
					integer9,
					integer10,
					integer11,
					integer12,
					integer13,
					integer14
				)
			);
		} catch (Throwable var14) {
			stringBuilder4.append("(Error finding world loc)");
		}

		return stringBuilder4.toString();
	}

	public k a(String string, l<String> l) {
		try {
			this.a(string, l.call());
		} catch (Throwable var4) {
			this.a(string, var4);
		}

		return this;
	}

	public k a(String string, Object object) {
		this.c.add(new k.a(string, object));
		return this;
	}

	public void a(String string, Throwable throwable) {
		this.a(string, (Object)throwable);
	}

	public int a(int integer) {
		StackTraceElement[] arr3 = Thread.currentThread().getStackTrace();
		if (arr3.length <= 0) {
			return 0;
		} else {
			this.d = new StackTraceElement[arr3.length - 3 - integer];
			System.arraycopy(arr3, 3 + integer, this.d, 0, this.d.length);
			return this.d.length;
		}
	}

	public boolean a(StackTraceElement stackTraceElement1, StackTraceElement stackTraceElement2) {
		if (this.d.length != 0 && stackTraceElement1 != null) {
			StackTraceElement stackTraceElement4 = this.d[0];
			if (stackTraceElement4.isNativeMethod() == stackTraceElement1.isNativeMethod()
				&& stackTraceElement4.getClassName().equals(stackTraceElement1.getClassName())
				&& stackTraceElement4.getFileName().equals(stackTraceElement1.getFileName())
				&& stackTraceElement4.getMethodName().equals(stackTraceElement1.getMethodName())) {
				if (stackTraceElement2 != null != this.d.length > 1) {
					return false;
				} else if (stackTraceElement2 != null && !this.d[1].equals(stackTraceElement2)) {
					return false;
				} else {
					this.d[0] = stackTraceElement1;
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void b(int integer) {
		StackTraceElement[] arr3 = new StackTraceElement[this.d.length - integer];
		System.arraycopy(this.d, 0, arr3, 0, arr3.length);
		this.d = arr3;
	}

	public void a(StringBuilder stringBuilder) {
		stringBuilder.append("-- ").append(this.b).append(" --\n");
		stringBuilder.append("Details:");

		for (k.a a4 : this.c) {
			stringBuilder.append("\n\t");
			stringBuilder.append(a4.a());
			stringBuilder.append(": ");
			stringBuilder.append(a4.b());
		}

		if (this.d != null && this.d.length > 0) {
			stringBuilder.append("\nStacktrace:");

			for (StackTraceElement stackTraceElement6 : this.d) {
				stringBuilder.append("\n\tat ");
				stringBuilder.append(stackTraceElement6);
			}
		}
	}

	public StackTraceElement[] a() {
		return this.d;
	}

	public static void a(k k, fu fu, @Nullable cfj cfj) {
		if (cfj != null) {
			k.a("Block", cfj::toString);
		}

		k.a("Block location", (l<String>)(() -> a(fu)));
	}

	static class a {
		private final String a;
		private final String b;

		public a(String string, Object object) {
			this.a = string;
			if (object == null) {
				this.b = "~~NULL~~";
			} else if (object instanceof Throwable) {
				Throwable throwable4 = (Throwable)object;
				this.b = "~~ERROR~~ " + throwable4.getClass().getSimpleName() + ": " + throwable4.getMessage();
			} else {
				this.b = object.toString();
			}
		}

		public String a() {
			return this.a;
		}

		public String b() {
			return this.b;
		}
	}
}
