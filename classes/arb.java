import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class arb extends aqh<aoy> {
	public arb() {
		super(ImmutableMap.of(awp.t, awq.VALUE_PRESENT, awp.u, awq.VALUE_PRESENT, awp.v, awq.REGISTERED));
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		apr<?> apr6 = aoy.cI();
		czf czf7 = (czf)apr6.c(awp.t).get();
		List<gc> list8 = (List<gc>)apr6.c(awp.u).get();
		List<fu> list9 = (List<fu>)czf7.d().stream().map(czd -> new fu(czd.a, czd.b, czd.c)).collect(Collectors.toList());
		Set<fu> set10 = this.a(zd, list8, list9);
		int integer11 = czf7.f() - 1;
		this.a(zd, list9, set10, integer11, aoy, apr6);
	}

	private Set<fu> a(zd zd, List<gc> list2, List<fu> list3) {
		return (Set<fu>)list2.stream().filter(gc -> gc.a() == zd.W()).map(gc::b).filter(list3::contains).collect(Collectors.toSet());
	}

	private void a(zd zd, List<fu> list, Set<fu> set, int integer, aoy aoy, apr<?> apr) {
		set.forEach(fu -> {
			int integer6 = list.indexOf(fu);
			cfj cfj7 = zd.d_(fu);
			bvr bvr8 = cfj7.b();
			if (acx.g.a(bvr8) && bvr8 instanceof bxe) {
				boolean boolean9 = integer6 >= integer;
				((bxe)bvr8).a(zd, fu, boolean9);
				gc gc10 = gc.a(zd.W(), fu);
				if (!apr.c(awp.v).isPresent() && boolean9) {
					apr.a(awp.v, Sets.<gc>newHashSet(gc10));
				} else {
					apr.c(awp.v).ifPresent(setx -> {
						if (boolean9) {
							setx.add(gc10);
						} else {
							setx.remove(gc10);
						}
					});
				}
			}
		});
		a(zd, list, integer, aoy, apr);
	}

	public static void a(zd zd, List<fu> list, int integer, aoy aoy, apr<?> apr) {
		apr.c(awp.v).ifPresent(set -> {
			Iterator<gc> iterator6 = set.iterator();

			while (iterator6.hasNext()) {
				gc gc7 = (gc)iterator6.next();
				fu fu8 = gc7.b();
				int integer9 = list.indexOf(fu8);
				if (zd.W() != gc7.a()) {
					iterator6.remove();
				} else {
					cfj cfj10 = zd.d_(fu8);
					bvr bvr11 = cfj10.b();
					if (acx.g.a(bvr11) && bvr11 instanceof bxe && integer9 < integer && fu8.a(aoy.cz(), 4.0)) {
						((bxe)bvr11).a(zd, fu8, false);
						iterator6.remove();
					}
				}
			}
		});
	}
}
