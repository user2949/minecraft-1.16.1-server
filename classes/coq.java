import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class coq implements cnr {
	public static final Codec<coq> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.BOOL.fieldOf("crystal_invulnerable").withDefault(false).forGetter(coq -> coq.b),
					cmi.a.a.listOf().fieldOf("spikes").forGetter(coq -> coq.c),
					fu.a.optionalFieldOf("crystal_beam_target").forGetter(coq -> Optional.ofNullable(coq.d))
				)
				.apply(instance, coq::new)
	);
	private final boolean b;
	private final List<cmi.a> c;
	@Nullable
	private final fu d;

	public coq(boolean boolean1, List<cmi.a> list, @Nullable fu fu) {
		this(boolean1, list, Optional.ofNullable(fu));
	}

	private coq(boolean boolean1, List<cmi.a> list, Optional<fu> optional) {
		this.b = boolean1;
		this.c = list;
		this.d = (fu)optional.orElse(null);
	}

	public boolean a() {
		return this.b;
	}

	public List<cmi.a> b() {
		return this.c;
	}

	@Nullable
	public fu c() {
		return this.d;
	}
}
