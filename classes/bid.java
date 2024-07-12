import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.UUID;

public class bid extends bke implements bly {
	private static final UUID[] j = new UUID[]{
		UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
		UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
		UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
		UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
	};
	public static final gw a = new gv() {
		@Override
		protected bki a(fv fv, bki bki) {
			return bid.a(fv, bki) ? bki : super.a(fv, bki);
		}
	};
	protected final aor b;
	private final int k;
	private final float l;
	protected final float c;
	protected final bie d;
	private final Multimap<aps, apv> m;

	public static boolean a(fv fv, bki bki) {
		fu fu3 = fv.d().a(fv.e().c(bxd.a));
		List<aoy> list4 = fv.h().a(aoy.class, new deg(fu3), aop.g.and(new aop.a(bki)));
		if (list4.isEmpty()) {
			return false;
		} else {
			aoy aoy5 = (aoy)list4.get(0);
			aor aor6 = aoz.j(bki);
			bki bki7 = bki.a(1);
			aoy5.a(aor6, bki7);
			if (aoy5 instanceof aoz) {
				((aoz)aoy5).a(aor6, 2.0F);
				((aoz)aoy5).et();
			}

			return true;
		}
	}

	public bid(bie bie, aor aor, bke.a a) {
		super(a.b(bie.a(aor)));
		this.d = bie;
		this.b = aor;
		this.k = bie.b(aor);
		this.l = bie.e();
		this.c = bie.f();
		bxd.a(this, bid.a);
		Builder<aps, apv> builder5 = ImmutableMultimap.builder();
		UUID uUID6 = j[aor.b()];
		builder5.put(apx.i, new apv(uUID6, "Armor modifier", (double)this.k, apv.a.ADDITION));
		builder5.put(apx.j, new apv(uUID6, "Armor toughness", (double)this.l, apv.a.ADDITION));
		if (bie == bif.NETHERITE) {
			builder5.put(apx.c, new apv(uUID6, "Armor knockback resistance", (double)this.c, apv.a.ADDITION));
		}

		this.m = builder5.build();
	}

	public aor b() {
		return this.b;
	}

	@Override
	public int c() {
		return this.d.a();
	}

	public bie ad_() {
		return this.d;
	}

	@Override
	public boolean a(bki bki1, bki bki2) {
		return this.d.c().a(bki2) || super.a(bki1, bki2);
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		aor aor6 = aoz.j(bki5);
		bki bki7 = bec.b(aor6);
		if (bki7.a()) {
			bec.a(aor6, bki5.i());
			bki5.e(0);
			return anh.a(bki5, bqb.s_());
		} else {
			return anh.d(bki5);
		}
	}

	@Override
	public Multimap<aps, apv> a(aor aor) {
		return aor == this.b ? this.m : super.a(aor);
	}

	public int e() {
		return this.k;
	}

	public float f() {
		return this.l;
	}
}
