import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class uh implements Comparable<uh> {
	public static final Codec<uh> a = Codec.STRING.<uh>comapFlatMap(uh::c, uh::toString).stable();
	private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("argument.id.invalid"));
	protected final String b;
	protected final String c;

	protected uh(String[] arr) {
		this.b = StringUtils.isEmpty(arr[0]) ? "minecraft" : arr[0];
		this.c = arr[1];
		if (!e(this.b)) {
			throw new t("Non [a-z0-9_.-] character in namespace of location: " + this.b + ':' + this.c);
		} else if (!d(this.c)) {
			throw new t("Non [a-z0-9/._-] character in path of location: " + this.b + ':' + this.c);
		}
	}

	public uh(String string) {
		this(b(string, ':'));
	}

	public uh(String string1, String string2) {
		this(new String[]{string1, string2});
	}

	public static uh a(String string, char character) {
		return new uh(b(string, character));
	}

	@Nullable
	public static uh a(String string) {
		try {
			return new uh(string);
		} catch (t var2) {
			return null;
		}
	}

	protected static String[] b(String string, char character) {
		String[] arr3 = new String[]{"minecraft", string};
		int integer4 = string.indexOf(character);
		if (integer4 >= 0) {
			arr3[1] = string.substring(integer4 + 1, string.length());
			if (integer4 >= 1) {
				arr3[0] = string.substring(0, integer4);
			}
		}

		return arr3;
	}

	private static DataResult<uh> c(String string) {
		try {
			return DataResult.success(new uh(string));
		} catch (t var2) {
			return DataResult.error("Not a valid resource location: " + string + " " + var2.getMessage());
		}
	}

	public String a() {
		return this.c;
	}

	public String b() {
		return this.b;
	}

	public String toString() {
		return this.b + ':' + this.c;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof uh)) {
			return false;
		} else {
			uh uh3 = (uh)object;
			return this.b.equals(uh3.b) && this.c.equals(uh3.c);
		}
	}

	public int hashCode() {
		return 31 * this.b.hashCode() + this.c.hashCode();
	}

	public int compareTo(uh uh) {
		int integer3 = this.c.compareTo(uh.c);
		if (integer3 == 0) {
			integer3 = this.b.compareTo(uh.b);
		}

		return integer3;
	}

	public static uh a(StringReader stringReader) throws CommandSyntaxException {
		int integer2 = stringReader.getCursor();

		while (stringReader.canRead() && a(stringReader.peek())) {
			stringReader.skip();
		}

		String string3 = stringReader.getString().substring(integer2, stringReader.getCursor());

		try {
			return new uh(string3);
		} catch (t var4) {
			stringReader.setCursor(integer2);
			throw d.createWithContext(stringReader);
		}
	}

	public static boolean a(char character) {
		return character >= '0' && character <= '9'
			|| character >= 'a' && character <= 'z'
			|| character == '_'
			|| character == ':'
			|| character == '/'
			|| character == '.'
			|| character == '-';
	}

	private static boolean d(String string) {
		for (int integer2 = 0; integer2 < string.length(); integer2++) {
			if (!b(string.charAt(integer2))) {
				return false;
			}
		}

		return true;
	}

	private static boolean e(String string) {
		for (int integer2 = 0; integer2 < string.length(); integer2++) {
			if (!c(string.charAt(integer2))) {
				return false;
			}
		}

		return true;
	}

	private static boolean b(char character) {
		return character == '_'
			|| character == '-'
			|| character >= 'a' && character <= 'z'
			|| character >= '0' && character <= '9'
			|| character == '/'
			|| character == '.';
	}

	private static boolean c(char character) {
		return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.';
	}

	public static class a implements JsonDeserializer<uh>, JsonSerializer<uh> {
		public uh deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			return new uh(adt.a(jsonElement, "location"));
		}

		public JsonElement serialize(uh uh, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(uh.toString());
		}
	}
}
