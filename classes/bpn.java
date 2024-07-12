import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class bpn {
	public static final bpn a = new bpn(ImmutableList.of("vanilla"), ImmutableList.of());
	public static final Codec<bpn> b = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.STRING.listOf().fieldOf("Enabled").forGetter(bpn -> bpn.c), Codec.STRING.listOf().fieldOf("Disabled").forGetter(bpn -> bpn.d)
				)
				.apply(instance, bpn::new)
	);
	private final List<String> c;
	private final List<String> d;

	public bpn(List<String> list1, List<String> list2) {
		this.c = ImmutableList.copyOf(list1);
		this.d = ImmutableList.copyOf(list2);
	}

	public List<String> a() {
		return this.c;
	}

	public List<String> b() {
		return this.d;
	}
}
