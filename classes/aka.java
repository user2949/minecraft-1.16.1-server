import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Const.PrimitiveType;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;

public class aka extends Schema {
	public static final PrimitiveCodec<String> a = new PrimitiveCodec<String>() {
		@Override
		public <T> DataResult<String> read(DynamicOps<T> dynamicOps, T object) {
			return dynamicOps.getStringValue(object).map(aka::a);
		}

		public <T> T write(DynamicOps<T> dynamicOps, String string) {
			return dynamicOps.createString(string);
		}

		public String toString() {
			return "NamespacedString";
		}
	};
	private static final Type<String> b = new PrimitiveType<>(a);

	public aka(int integer, Schema schema) {
		super(integer, schema);
	}

	public static String a(String string) {
		uh uh2 = uh.a(string);
		return uh2 != null ? uh2.toString() : string;
	}

	public static Type<String> a() {
		return b;
	}

	@Override
	public Type<?> getChoiceType(TypeReference typeReference, String string) {
		return super.getChoiceType(typeReference, a(string));
	}
}
