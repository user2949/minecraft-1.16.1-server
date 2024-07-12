import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public abstract class csq<DC extends cnn> extends csc<DC> {
	public csq(Codec<DC> codec) {
		super(codec);
	}

	@Override
	public final Stream<fu> a(bqc bqc, cha cha, Random random, DC cnn, fu fu) {
		return this.a(random, cnn, fu);
	}

	protected abstract Stream<fu> a(Random random, DC cnn, fu fu);
}
