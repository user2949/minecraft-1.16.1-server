import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.Random;
import java.util.Map.Entry;

public abstract class cvq extends cvw<cvx> {
	private long a;
	private ImmutableMap<cfj, cwe> b = ImmutableMap.of();
	private ImmutableMap<cfj, cwe> c = ImmutableMap.of();
	private cwe d;

	public cvq(Codec<cvx> codec) {
		super(codec);
	}

	public void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11, cvx cvx
	) {
		int integer16 = integer10 + 1;
		int integer17 = integer4 & 15;
		int integer18 = integer5 & 15;
		int integer19 = (int)(double7 / 3.0 + 3.0 + random.nextDouble() * 0.25);
		int integer20 = (int)(double7 / 3.0 + 3.0 + random.nextDouble() * 0.25);
		double double21 = 0.03125;
		boolean boolean23 = this.d.a((double)integer4 * 0.03125, 109.0, (double)integer5 * 0.03125) * 75.0 + random.nextDouble() > 0.0;
		cfj cfj24 = (cfj)((Entry)this.c
				.entrySet()
				.stream()
				.max(Comparator.comparing(entry -> ((cwe)entry.getValue()).a((double)integer4, (double)integer10, (double)integer5)))
				.get())
			.getKey();
		cfj cfj25 = (cfj)((Entry)this.b
				.entrySet()
				.stream()
				.max(Comparator.comparing(entry -> ((cwe)entry.getValue()).a((double)integer4, (double)integer10, (double)integer5)))
				.get())
			.getKey();
		fu.a a26 = new fu.a();
		cfj cfj27 = cgy.d_(a26.d(integer17, 128, integer18));

		for (int integer28 = 127; integer28 >= 0; integer28--) {
			a26.d(integer17, integer28, integer18);
			cfj cfj29 = cgy.d_(a26);
			if (cfj27.a(cfj8.b()) && (cfj29.g() || cfj29 == cfj9)) {
				for (int integer30 = 0; integer30 < integer19; integer30++) {
					a26.c(fz.UP);
					if (!cgy.d_(a26).a(cfj8.b())) {
						break;
					}

					cgy.a(a26, cfj24, false);
				}

				a26.d(integer17, integer28, integer18);
			}

			if ((cfj27.g() || cfj27 == cfj9) && cfj29.a(cfj8.b())) {
				for (int integer30 = 0; integer30 < integer20 && cgy.d_(a26).a(cfj8.b()); integer30++) {
					if (boolean23 && integer28 >= integer16 - 4 && integer28 <= integer16 + 1) {
						cgy.a(a26, this.c(), false);
					} else {
						cgy.a(a26, cfj25, false);
					}

					a26.c(fz.DOWN);
				}
			}

			cfj27 = cfj29;
		}
	}

	@Override
	public void a(long long1) {
		if (this.a != long1 || this.d == null || this.b.isEmpty() || this.c.isEmpty()) {
			this.b = a(this.a(), long1);
			this.c = a(this.b(), long1 + (long)this.b.size());
			this.d = new cwe(new ciy(long1 + (long)this.b.size() + (long)this.c.size()), ImmutableList.of(0));
		}

		this.a = long1;
	}

	private static ImmutableMap<cfj, cwe> a(ImmutableList<cfj> immutableList, long long2) {
		Builder<cfj, cwe> builder4 = new Builder<>();

		for (cfj cfj6 : immutableList) {
			builder4.put(cfj6, new cwe(new ciy(long2), ImmutableList.of(-4)));
			long2++;
		}

		return builder4.build();
	}

	protected abstract ImmutableList<cfj> a();

	protected abstract ImmutableList<cfj> b();

	protected abstract cfj c();
}
