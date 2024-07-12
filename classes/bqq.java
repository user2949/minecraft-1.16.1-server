import com.mojang.datafixers.DataFixUtils;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class bqq {
	private final bqc a;
	private final cix b;

	public bqq(bqc bqc, cix cix) {
		this.a = bqc;
		this.b = cix;
	}

	public bqq a(zj zj) {
		if (zj.h() != this.a) {
			throw new IllegalStateException("Using invalid feature manager (source level: " + zj.h() + ", region: " + zj);
		} else {
			return new bqq(zj, this.b);
		}
	}

	public Stream<? extends ctz<?>> a(go go, cml<?> cml) {
		return this.a
			.a(go.a(), go.c(), chc.c)
			.b(cml)
			.stream()
			.map(long1 -> go.a(new bph(long1), 0))
			.map(gox -> this.a(gox, cml, this.a.a(gox.a(), gox.c(), chc.b)))
			.filter(ctz -> ctz != null && ctz.e());
	}

	@Nullable
	public ctz<?> a(go go, cml<?> cml, chf chf) {
		return chf.a(cml);
	}

	public void a(go go, cml<?> cml, ctz<?> ctz, chf chf) {
		chf.a(cml, ctz);
	}

	public void a(go go, cml<?> cml, long long3, chf chf) {
		chf.a(cml, long3);
	}

	public boolean a() {
		return this.b.c();
	}

	public ctz<?> a(fu fu, boolean boolean2, cml<?> cml) {
		return DataFixUtils.orElse(
			this.a(go.a(fu), cml).filter(ctz -> ctz.c().b(fu)).filter(ctz -> !boolean2 || ctz.d().stream().anyMatch(cty -> cty.g().b(fu))).findFirst(), ctz.a
		);
	}
}
