import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;

public class cpy {
	private final int a;
	private final int b;
	private final int c;
	private final int d;
	private final cqf.a e;

	public cpy(int integer1, int integer2, int integer3, int integer4, cqf.a a) {
		this.a = integer1;
		this.b = integer2;
		this.c = integer3;
		this.d = integer4;
		this.e = a;
	}

	public int a() {
		return this.a;
	}

	public int b() {
		return this.b;
	}

	public int c() {
		return this.c;
	}

	public <T> Dynamic<T> a(DynamicOps<T> dynamicOps) {
		Builder<T, T> builder3 = ImmutableMap.builder();
		builder3.put(dynamicOps.createString("source_x"), dynamicOps.createInt(this.a))
			.put(dynamicOps.createString("source_ground_y"), dynamicOps.createInt(this.b))
			.put(dynamicOps.createString("source_z"), dynamicOps.createInt(this.c))
			.put(dynamicOps.createString("delta_y"), dynamicOps.createInt(this.d))
			.put(dynamicOps.createString("dest_proj"), dynamicOps.createString(this.e.b()));
		return new Dynamic<>(dynamicOps, dynamicOps.createMap(builder3.build()));
	}

	public static <T> cpy a(Dynamic<T> dynamic) {
		return new cpy(
			dynamic.get("source_x").asInt(0),
			dynamic.get("source_ground_y").asInt(0),
			dynamic.get("source_z").asInt(0),
			dynamic.get("delta_y").asInt(0),
			cqf.a.a(dynamic.get("dest_proj").asString(""))
		);
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			cpy cpy3 = (cpy)object;
			if (this.a != cpy3.a) {
				return false;
			} else if (this.c != cpy3.c) {
				return false;
			} else {
				return this.d != cpy3.d ? false : this.e == cpy3.e;
			}
		} else {
			return false;
		}
	}

	public int hashCode() {
		int integer2 = this.a;
		integer2 = 31 * integer2 + this.b;
		integer2 = 31 * integer2 + this.c;
		integer2 = 31 * integer2 + this.d;
		return 31 * integer2 + this.e.hashCode();
	}

	public String toString() {
		return "JigsawJunction{sourceX=" + this.a + ", sourceGroundY=" + this.b + ", sourceZ=" + this.c + ", deltaY=" + this.d + ", destProjection=" + this.e + '}';
	}
}
