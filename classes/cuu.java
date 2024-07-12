import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class cuu {
	public static final Codec<cuu> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cuy.c.fieldOf("input_predicate").forGetter(cuu -> cuu.b),
					cuy.c.fieldOf("location_predicate").forGetter(cuu -> cuu.c),
					cus.c.fieldOf("position_predicate").forGetter(cuu -> cuu.d),
					cfj.b.fieldOf("output_state").forGetter(cuu -> cuu.e),
					le.a.optionalFieldOf("output_nbt").forGetter(cuu -> Optional.ofNullable(cuu.f))
				)
				.apply(instance, cuu::new)
	);
	private final cuy b;
	private final cuy c;
	private final cus d;
	private final cfj e;
	@Nullable
	private final le f;

	public cuu(cuy cuy1, cuy cuy2, cfj cfj) {
		this(cuy1, cuy2, cur.b, cfj, Optional.empty());
	}

	public cuu(cuy cuy1, cuy cuy2, cus cus, cfj cfj) {
		this(cuy1, cuy2, cus, cfj, Optional.empty());
	}

	public cuu(cuy cuy1, cuy cuy2, cus cus, cfj cfj, Optional<le> optional) {
		this.b = cuy1;
		this.c = cuy2;
		this.d = cus;
		this.e = cfj;
		this.f = (le)optional.orElse(null);
	}

	public boolean a(cfj cfj1, cfj cfj2, fu fu3, fu fu4, fu fu5, Random random) {
		return this.b.a(cfj1, random) && this.c.a(cfj2, random) && this.d.a(fu3, fu4, fu5, random);
	}

	public cfj a() {
		return this.e;
	}

	@Nullable
	public le b() {
		return this.f;
	}
}
