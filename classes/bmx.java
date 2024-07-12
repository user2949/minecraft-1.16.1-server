import java.util.Optional;

public interface bmx<T extends bmu<?>> {
	bmx<bmm> a = a("crafting");
	bmx<bng> b = a("smelting");
	bmx<bmj> c = a("blasting");
	bmx<bnh> d = a("smoking");
	bmx<bml> e = a("campfire_cooking");
	bmx<bni> f = a("stonecutting");
	bmx<bnl> g = a("smithing");

	static <T extends bmu<?>> bmx<T> a(String string) {
		return gl.a(gl.aN, new uh(string), new bmx<T>() {
			public String toString() {
				return string;
			}
		});
	}

	default <C extends amz> Optional<T> a(bmu<C> bmu, bqb bqb, C amz) {
		return bmu.a(amz, bqb) ? Optional.of(bmu) : Optional.empty();
	}
}
