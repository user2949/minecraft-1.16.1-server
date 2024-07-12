import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class lt implements lu {
	public static final lw<lt> a = new lw<lt>() {
		public lt b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(288L);
			String string5 = dataInput.readUTF();
			ln.a((long)(16 * string5.length()));
			return lt.a(string5);
		}

		@Override
		public String a() {
			return "STRING";
		}

		@Override
		public String b() {
			return "TAG_String";
		}

		@Override
		public boolean c() {
			return true;
		}
	};
	private static final lt b = new lt("");
	private final String c;

	private lt(String string) {
		Objects.requireNonNull(string, "Null string not allowed");
		this.c = string;
	}

	public static lt a(String string) {
		return string.isEmpty() ? b : new lt(string);
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		dataOutput.writeUTF(this.c);
	}

	@Override
	public byte a() {
		return 8;
	}

	@Override
	public lw<lt> b() {
		return a;
	}

	@Override
	public String toString() {
		return b(this.c);
	}

	public lt c() {
		return this;
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof lt && Objects.equals(this.c, ((lt)object).c);
	}

	public int hashCode() {
		return this.c.hashCode();
	}

	@Override
	public String f_() {
		return this.c;
	}

	@Override
	public mr a(String string, int integer) {
		String string4 = b(this.c);
		String string5 = string4.substring(0, 1);
		mr mr6 = new nd(string4.substring(1, string4.length() - 1)).a(e);
		return new nd(string5).a(mr6).c(string5);
	}

	public static String b(String string) {
		StringBuilder stringBuilder2 = new StringBuilder(" ");
		char character3 = 0;

		for (int integer4 = 0; integer4 < string.length(); integer4++) {
			char character5 = string.charAt(integer4);
			if (character5 == '\\') {
				stringBuilder2.append('\\');
			} else if (character5 == '"' || character5 == '\'') {
				if (character3 == 0) {
					character3 = (char)(character5 == '"' ? 39 : 34);
				}

				if (character3 == character5) {
					stringBuilder2.append('\\');
				}
			}

			stringBuilder2.append(character5);
		}

		if (character3 == 0) {
			character3 = '"';
		}

		stringBuilder2.setCharAt(0, character3);
		stringBuilder2.append(character3);
		return stringBuilder2.toString();
	}
}
