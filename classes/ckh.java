import com.mojang.serialization.Codec;
import java.util.Random;

public class ckh extends ckt<cnm> {
	public ckh(Codec<cnm> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cnm cnm) {
		return cnm.c.a(bqu, bqq, cha, random, fu, cnm.b);
	}

	public String toString() {
		return String.format("< %s [%s] >", this.getClass().getSimpleName(), gl.aq.b(this));
	}
}
