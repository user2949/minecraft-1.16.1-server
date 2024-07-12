import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;

public class hd implements hf {
	public static final hd a = new hd(1.0F, 0.0F, 0.0F, 1.0F);
	public static final Codec<hd> b = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.FLOAT.fieldOf("r").forGetter(hd -> hd.d),
					Codec.FLOAT.fieldOf("g").forGetter(hd -> hd.e),
					Codec.FLOAT.fieldOf("b").forGetter(hd -> hd.f),
					Codec.FLOAT.fieldOf("scale").forGetter(hd -> hd.g)
				)
				.apply(instance, hd::new)
	);
	public static final hf.a<hd> c = new hf.a<hd>() {
		public hd b(hg<hd> hg, StringReader stringReader) throws CommandSyntaxException {
			stringReader.expect(' ');
			float float4 = (float)stringReader.readDouble();
			stringReader.expect(' ');
			float float5 = (float)stringReader.readDouble();
			stringReader.expect(' ');
			float float6 = (float)stringReader.readDouble();
			stringReader.expect(' ');
			float float7 = (float)stringReader.readDouble();
			return new hd(float4, float5, float6, float7);
		}

		public hd b(hg<hd> hg, mg mg) {
			return new hd(mg.readFloat(), mg.readFloat(), mg.readFloat(), mg.readFloat());
		}
	};
	private final float d;
	private final float e;
	private final float f;
	private final float g;

	public hd(float float1, float float2, float float3, float float4) {
		this.d = float1;
		this.e = float2;
		this.f = float3;
		this.g = aec.a(float4, 0.01F, 4.0F);
	}

	@Override
	public void a(mg mg) {
		mg.writeFloat(this.d);
		mg.writeFloat(this.e);
		mg.writeFloat(this.f);
		mg.writeFloat(this.g);
	}

	@Override
	public String a() {
		return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", gl.az.b(this.b()), this.d, this.e, this.f, this.g);
	}

	@Override
	public hg<hd> b() {
		return hh.o;
	}
}
