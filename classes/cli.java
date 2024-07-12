import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class cli extends cml<cnw> {
	public cli(Codec<cnw> codec) {
		super(codec);
	}

	protected boolean a(cha cha, brh brh, long long3, ciy ciy, int integer5, int integer6, bre bre, bph bph, cnw cnw) {
		ciy.c(long3, integer5, integer6);
		double double12 = cnw.b;
		return ciy.nextDouble() < double12;
	}

	@Override
	public cml.a<cnw> a() {
		return cli.a::new;
	}

	public static class a extends ctz<cnw> {
		public a(cml<cnw> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, cnw cnw) {
			ctk.d d8 = new ctk.d(0, this.d, (integer3 << 4) + 2, (integer4 << 4) + 2, cnw.c);
			this.b.add(d8);
			d8.a(d8, this.b, this.d);
			this.b();
			if (cnw.c == cli.b.MESA) {
				int integer9 = -5;
				int integer10 = cha.f() - this.c.e + this.c.e() / 2 - -5;
				this.c.a(0, integer10, 0);

				for (cty cty12 : this.b) {
					cty12.a(0, integer10, 0);
				}
			} else {
				this.a(cha.f(), this.d, 10);
			}
		}
	}

	public static enum b implements aeh {
		NORMAL("normal"),
		MESA("mesa");

		public static final Codec<cli.b> c = aeh.a(cli.b::values, cli.b::a);
		private static final Map<String, cli.b> d = (Map<String, cli.b>)Arrays.stream(values()).collect(Collectors.toMap(cli.b::b, b -> b));
		private final String e;

		private b(String string3) {
			this.e = string3;
		}

		public String b() {
			return this.e;
		}

		private static cli.b a(String string) {
			return (cli.b)d.get(string);
		}

		public static cli.b a(int integer) {
			return integer >= 0 && integer < values().length ? values()[integer] : NORMAL;
		}

		@Override
		public String a() {
			return this.e;
		}
	}
}
