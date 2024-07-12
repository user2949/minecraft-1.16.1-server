import com.mojang.serialization.Codec;

public class cmz<P extends cmy> {
	public static final cmz<cnc> a = a("simple_block_placer", cnc.b);
	public static final cmz<cnb> b = a("double_plant_placer", cnb.b);
	public static final cmz<cna> c = a("column_placer", cna.b);
	private final Codec<P> d;

	private static <P extends cmy> cmz<P> a(String string, Codec<P> codec) {
		return gl.a(gl.au, string, new cmz<>(codec));
	}

	private cmz(Codec<P> codec) {
		this.d = codec;
	}

	public Codec<P> a() {
		return this.d;
	}
}
