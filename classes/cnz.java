import com.mojang.serialization.Codec;
import java.util.function.Supplier;

public class cnz implements cnn {
	public static final Codec<cnz> a = Codec.unit((Supplier<cnz>)(() -> cnz.b));
	public static final cnz b = new cnz();
}
