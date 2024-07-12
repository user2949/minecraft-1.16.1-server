import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Random;

public class cpu extends cpo {
	public static final Codec<cpu> b = atb.a(cfj.b).<cpu>comapFlatMap(cpu::a, cpu -> cpu.c).fieldOf("entries").codec();
	private final atb<cfj> c;

	private static DataResult<cpu> a(atb<cfj> atb) {
		return atb.b() ? DataResult.error("WeightedStateProvider with no states") : DataResult.success(new cpu(atb));
	}

	private cpu(atb<cfj> atb) {
		this.c = atb;
	}

	@Override
	protected cpp<?> a() {
		return cpp.b;
	}

	public cpu() {
		this(new atb<>());
	}

	public cpu a(cfj cfj, int integer) {
		this.c.a(cfj, integer);
		return this;
	}

	@Override
	public cfj a(Random random, fu fu) {
		return this.c.b(random);
	}
}
