import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Map;
import java.util.Map.Entry;

public class ade<T> extends adg<T> {
	private final gl<T> a;

	public ade(gl<T> gl, String string2, String string3) {
		super(gl::b, string2, string3);
		this.a = gl;
	}

	public void a(mg mg) {
		Map<uh, adf<T>> map3 = this.b();
		mg.d(map3.size());

		for (Entry<uh, adf<T>> entry5 : map3.entrySet()) {
			mg.a((uh)entry5.getKey());
			mg.d(((adf)entry5.getValue()).b().size());

			for (T object7 : ((adf)entry5.getValue()).b()) {
				mg.d(this.a.a(object7));
			}
		}
	}

	public void b(mg mg) {
		Map<uh, adf<T>> map3 = Maps.<uh, adf<T>>newHashMap();
		int integer4 = mg.i();

		for (int integer5 = 0; integer5 < integer4; integer5++) {
			uh uh6 = mg.o();
			int integer7 = mg.i();
			Builder<T> builder8 = ImmutableSet.builder();

			for (int integer9 = 0; integer9 < integer7; integer9++) {
				builder8.add(this.a.a(mg.i()));
			}

			map3.put(uh6, adf.b(builder8.build()));
		}

		this.b(map3);
	}
}
