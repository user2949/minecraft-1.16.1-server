import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class czh {
	private final czd[] a = new czd[32];
	private final int b;
	private final cze c;
	private final cza d = new cza();

	public czh(cze cze, int integer) {
		this.c = cze;
		this.b = integer;
	}

	@Nullable
	public czf a(bql bql, aoz aoz, Set<fu> set, float float4, int integer, float float6) {
		this.d.a();
		this.c.a(bql, aoz);
		czd czd8 = this.c.b();
		Map<czj, fu> map9 = (Map<czj, fu>)set.stream().collect(Collectors.toMap(fu -> this.c.a((double)fu.u(), (double)fu.v(), (double)fu.w()), Function.identity()));
		czf czf10 = this.a(czd8, map9, float4, integer, float6);
		this.c.a();
		return czf10;
	}

	@Nullable
	private czf a(czd czd, Map<czj, fu> map, float float3, int integer, float float5) {
		Set<czj> set7 = map.keySet();
		czd.e = 0.0F;
		czd.f = this.a(czd, set7);
		czd.g = czd.f;
		this.d.a();
		this.d.a(czd);
		Set<czd> set8 = ImmutableSet.of();
		int integer9 = 0;
		Set<czj> set10 = Sets.<czj>newHashSetWithExpectedSize(set7.size());
		int integer11 = (int)((float)this.b * float5);

		while (!this.d.e()) {
			if (++integer9 >= integer11) {
				break;
			}

			czd czd12 = this.d.c();
			czd12.i = true;

			for (czj czj14 : set7) {
				if (czd12.c(czj14) <= (float)integer) {
					czj14.e();
					set10.add(czj14);
				}
			}

			if (!set10.isEmpty()) {
				break;
			}

			if (!(czd12.a(czd) >= float3)) {
				int integer13 = this.c.a(this.a, czd12);

				for (int integer14 = 0; integer14 < integer13; integer14++) {
					czd czd15 = this.a[integer14];
					float float16 = czd12.a(czd15);
					czd15.j = czd12.j + float16;
					float float17 = czd12.e + float16 + czd15.k;
					if (czd15.j < float3 && (!czd15.c() || float17 < czd15.e)) {
						czd15.h = czd12;
						czd15.e = float17;
						czd15.f = this.a(czd15, set7) * 1.5F;
						if (czd15.c()) {
							this.d.a(czd15, czd15.e + czd15.f);
						} else {
							czd15.g = czd15.e + czd15.f;
							this.d.a(czd15);
						}
					}
				}
			}
		}

		Optional<czf> optional12 = !set10.isEmpty()
			? set10.stream().map(czj -> this.a(czj.d(), (fu)map.get(czj), true)).min(Comparator.comparingInt(czf::e))
			: set7.stream().map(czj -> this.a(czj.d(), (fu)map.get(czj), false)).min(Comparator.comparingDouble(czf::n).thenComparingInt(czf::e));
		return !optional12.isPresent() ? null : (czf)optional12.get();
	}

	private float a(czd czd, Set<czj> set) {
		float float4 = Float.MAX_VALUE;

		for (czj czj6 : set) {
			float float7 = czd.a(czj6);
			czj6.a(float7, czd);
			float4 = Math.min(float7, float4);
		}

		return float4;
	}

	private czf a(czd czd, fu fu, boolean boolean3) {
		List<czd> list5 = Lists.<czd>newArrayList();
		czd czd6 = czd;
		list5.add(0, czd);

		while (czd6.h != null) {
			czd6 = czd6.h;
			list5.add(0, czd6);
		}

		return new czf(list5, fu, boolean3);
	}
}
