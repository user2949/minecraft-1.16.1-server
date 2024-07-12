import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class cin {
	public static enum a implements aeh {
		AIR("air"),
		LIQUID("liquid");

		public static final Codec<cin.a> c = aeh.a(cin.a::values, cin.a::a);
		private static final Map<String, cin.a> d = (Map<String, cin.a>)Arrays.stream(values()).collect(Collectors.toMap(cin.a::b, a -> a));
		private final String e;

		private a(String string3) {
			this.e = string3;
		}

		public String b() {
			return this.e;
		}

		@Nullable
		public static cin.a a(String string) {
			return (cin.a)d.get(string);
		}

		@Override
		public String a() {
			return this.e;
		}
	}

	public static enum b implements aeh {
		RAW_GENERATION("raw_generation"),
		LAKES("lakes"),
		LOCAL_MODIFICATIONS("local_modifications"),
		UNDERGROUND_STRUCTURES("underground_structures"),
		SURFACE_STRUCTURES("surface_structures"),
		STRONGHOLDS("strongholds"),
		UNDERGROUND_ORES("underground_ores"),
		UNDERGROUND_DECORATION("underground_decoration"),
		VEGETAL_DECORATION("vegetal_decoration"),
		TOP_LAYER_MODIFICATION("top_layer_modification");

		public static final Codec<cin.b> k = aeh.a(cin.b::values, cin.b::a);
		private static final Map<String, cin.b> l = (Map<String, cin.b>)Arrays.stream(values()).collect(Collectors.toMap(cin.b::b, b -> b));
		private final String m;

		private b(String string3) {
			this.m = string3;
		}

		public String b() {
			return this.m;
		}

		public static cin.b a(String string) {
			return (cin.b)l.get(string);
		}

		@Override
		public String a() {
			return this.m;
		}
	}
}
