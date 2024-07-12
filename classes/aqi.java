import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class aqi {
	public static void a(aoy aoy1, aoy aoy2, float float3) {
		d(aoy1, aoy2);
		b(aoy1, aoy2, float3);
	}

	public static boolean a(apr<?> apr, aoy aoy) {
		return apr.c(awp.h).filter(list -> list.contains(aoy)).isPresent();
	}

	public static boolean a(apr<?> apr, awp<? extends aoy> awp, aoq<?> aoq) {
		return a(apr, awp, aoy -> aoy.U() == aoq);
	}

	private static boolean a(apr<?> apr, awp<? extends aoy> awp, Predicate<aoy> predicate) {
		return apr.c(awp).filter(predicate).filter(aoy::aU).filter(aoy -> a(apr, aoy)).isPresent();
	}

	private static void d(aoy aoy1, aoy aoy2) {
		a(aoy1, aoy2);
		a(aoy2, aoy1);
	}

	public static void a(aoy aoy1, aoy aoy2) {
		aoy1.cI().a(awp.n, new aqp(aoy2, true));
	}

	private static void b(aoy aoy1, aoy aoy2, float float3) {
		int integer4 = 2;
		a(aoy1, aoy2, float3, 2);
		a(aoy2, aoy1, float3, 2);
	}

	public static void a(aoy aoy, aom aom, float float3, int integer) {
		awr awr5 = new awr(new aqp(aom, false), float3, integer);
		aoy.cI().a(awp.n, new aqp(aom, true));
		aoy.cI().a(awp.m, awr5);
	}

	public static void a(aoy aoy, fu fu, float float3, int integer) {
		awr awr5 = new awr(new aqj(fu), float3, integer);
		aoy.cI().a(awp.n, new aqj(fu));
		aoy.cI().a(awp.m, awr5);
	}

	public static void a(aoy aoy, bki bki, dem dem) {
		double double4 = aoy.cF() - 0.3F;
		bbg bbg6 = new bbg(aoy.l, aoy.cC(), double4, aoy.cG(), bki);
		float float7 = 0.3F;
		dem dem8 = dem.d(aoy.cz());
		dem8 = dem8.d().a(0.3F);
		bbg6.e(dem8);
		bbg6.m();
		aoy.l.c(bbg6);
	}

	public static go a(zd zd, go go, int integer) {
		int integer4 = zd.b(go);
		return (go)go.a(go, integer).filter(gox -> zd.b(gox) < integer4).min(Comparator.comparingInt(zd::b)).orElse(go);
	}

	public static boolean a(aoz aoz, aoy aoy, int integer) {
		bke bke4 = aoz.dC().b();
		if (bke4 instanceof bkv && aoz.a((bkv)bke4)) {
			int integer5 = ((bkv)bke4).d() - integer;
			return aoz.a(aoy, (double)integer5);
		} else {
			return b(aoz, aoy);
		}
	}

	public static boolean b(aoy aoy1, aoy aoy2) {
		double double3 = aoy1.g(aoy2.cC(), aoy2.cD(), aoy2.cG());
		double double5 = (double)(aoy1.cx() * 2.0F * aoy1.cx() * 2.0F + aoy2.cx());
		return double3 <= double5;
	}

	public static boolean a(aoy aoy1, aoy aoy2, double double3) {
		Optional<aoy> optional5 = aoy1.cI().c(awp.o);
		if (!optional5.isPresent()) {
			return false;
		} else {
			double double6 = aoy1.d(((aoy)optional5.get()).cz());
			double double8 = aoy1.d(aoy2.cz());
			return double8 > double6 + double3 * double3;
		}
	}

	public static boolean c(aoy aoy1, aoy aoy2) {
		apr<?> apr3 = aoy1.cI();
		return !apr3.a(awp.h) ? false : ((List)apr3.c(awp.h).get()).contains(aoy2);
	}

	public static aoy a(aoy aoy1, Optional<aoy> optional, aoy aoy3) {
		return !optional.isPresent() ? aoy3 : a(aoy1, (aoy)optional.get(), aoy3);
	}

	public static aoy a(aoy aoy1, aoy aoy2, aoy aoy3) {
		dem dem4 = aoy2.cz();
		dem dem5 = aoy3.cz();
		return aoy1.d(dem4) < aoy1.d(dem5) ? aoy2 : aoy3;
	}

	public static Optional<aoy> a(aoy aoy, awp<UUID> awp) {
		Optional<UUID> optional3 = aoy.cI().c(awp);
		return optional3.map(uUID -> (aoy)((zd)aoy.l).a(uUID));
	}

	public static Stream<bdp> a(bdp bdp, Predicate<bdp> predicate) {
		return (Stream<bdp>)bdp.cI()
			.c(awp.g)
			.map(list -> list.stream().filter(aoy -> aoy instanceof bdp && aoy != bdp).map(aoy -> (bdp)aoy).filter(aoy::aU).filter(predicate))
			.orElseGet(Stream::empty);
	}
}
