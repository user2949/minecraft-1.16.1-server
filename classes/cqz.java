import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cqz {
	public static final Codec<cqz> a = RecordCodecBuilder.create(
		instance -> instance.group(adl.a(0, 256).fieldOf("height").forGetter(cqz::a), gl.aj.fieldOf("block").withDefault(bvs.a).forGetter(cqz -> cqz.b().b()))
				.apply(instance, cqz::new)
	);
	private final cfj b;
	private final int c;
	private int d;

	public cqz(int integer, bvr bvr) {
		this.c = integer;
		this.b = bvr.n();
	}

	public int a() {
		return this.c;
	}

	public cfj b() {
		return this.b;
	}

	public int c() {
		return this.d;
	}

	public void a(int integer) {
		this.d = integer;
	}

	public String toString() {
		return (this.c != 1 ? this.c + "*" : "") + gl.aj.b(this.b.b());
	}
}
