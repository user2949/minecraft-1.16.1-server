import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cnm implements cnr {
	public static final Codec<cnm> a = RecordCodecBuilder.create(
		instance -> instance.group(ckb.b.fieldOf("feature").forGetter(cnm -> cnm.b), crl.a.fieldOf("decorator").forGetter(cnm -> cnm.c)).apply(instance, cnm::new)
	);
	public final ckb<?, ?> b;
	public final crl<?> c;

	public cnm(ckb<?, ?> ckb, crl<?> crl) {
		this.b = ckb;
		this.c = crl;
	}

	public String toString() {
		return String.format("< %s [%s | %s] >", this.getClass().getSimpleName(), gl.aq.b(this.b.d), gl.ar.b(this.c.b));
	}
}
