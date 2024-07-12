import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class cus {
	public static final Codec<cus> c = gl.aJ.dispatch("predicate_type", cus::a, cut::codec);

	public abstract boolean a(fu fu1, fu fu2, fu fu3, Random random);

	protected abstract cut<?> a();
}
