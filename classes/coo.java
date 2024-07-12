import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class coo implements cnr {
	public static final Codec<coo> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cfj.b.fieldOf("to_place").forGetter(coo -> coo.b),
					cfj.b.listOf().fieldOf("place_on").forGetter(coo -> coo.c),
					cfj.b.listOf().fieldOf("place_in").forGetter(coo -> coo.d),
					cfj.b.listOf().fieldOf("place_under").forGetter(coo -> coo.e)
				)
				.apply(instance, coo::new)
	);
	public final cfj b;
	public final List<cfj> c;
	public final List<cfj> d;
	public final List<cfj> e;

	public coo(cfj cfj, List<cfj> list2, List<cfj> list3, List<cfj> list4) {
		this.b = cfj;
		this.c = list2;
		this.d = list3;
		this.e = list4;
	}
}
