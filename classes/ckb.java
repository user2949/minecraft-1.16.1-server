import com.mojang.serialization.Codec;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ckb<FC extends cnr, F extends ckt<FC>> {
	public static final ckb<?, ?> a = new ckb<>(ckt.b, coa.k);
	public static final Codec<ckb<?, ?>> b = gl.aq.<ckb<?, ?>>dispatch("name", ckb -> ckb.d, ckt::a).withDefault(a);
	public static final Logger c = LogManager.getLogger();
	public final F d;
	public final FC e;

	public ckb(F ckt, FC cnr) {
		this.d = ckt;
		this.e = cnr;
	}

	public ckb<?, ?> a(crl<?> crl) {
		ckt<cnm> ckt3 = this.d instanceof cjj ? ckt.ab : ckt.aa;
		return ckt3.b(new cnm(this, crl));
	}

	public cmw<FC> a(float float1) {
		return new cmw<>(this, float1);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu) {
		return this.d.a(bqu, bqq, cha, random, fu, this.e);
	}
}
