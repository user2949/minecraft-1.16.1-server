import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ctp extends cml<cob> {
	public ctp(Codec<cob> codec) {
		super(codec);
	}

	@Override
	public cml.a<cob> a() {
		return ctp.a::new;
	}

	public static class a extends ctz<cob> {
		public a(cml<cob> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, cob cob) {
			int integer8 = integer3 * 16;
			int integer9 = integer4 * 16;
			fu fu10 = new fu(integer8, 90, integer9);
			cap cap11 = cap.a(this.d);
			ctq.a(cva, fu10, cap11, this.b, this.d, cob);
			this.b();
		}
	}

	public static enum b implements aeh {
		WARM("warm"),
		COLD("cold");

		public static final Codec<ctp.b> c = aeh.a(ctp.b::values, ctp.b::a);
		private static final Map<String, ctp.b> d = (Map<String, ctp.b>)Arrays.stream(values()).collect(Collectors.toMap(ctp.b::b, b -> b));
		private final String e;

		private b(String string3) {
			this.e = string3;
		}

		public String b() {
			return this.e;
		}

		@Nullable
		public static ctp.b a(String string) {
			return (ctp.b)d.get(string);
		}

		@Override
		public String a() {
			return this.e;
		}
	}
}
