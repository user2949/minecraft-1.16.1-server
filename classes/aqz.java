import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class aqz extends aqh<apg> {
	private final float b;

	public aqz(float float1) {
		super(ImmutableMap.of(awp.m, awq.VALUE_ABSENT));
		this.b = float1;
	}

	protected boolean a(zd zd, apg apg) {
		return !zd.f(apg.cA());
	}

	protected void a(zd zd, apg apg, long long3) {
		fu fu6 = apg.cA();
		List<fu> list7 = (List<fu>)fu.b(fu6.b(-1, -1, -1), fu6.b(1, 1, 1)).map(fu::h).collect(Collectors.toList());
		Collections.shuffle(list7);
		Optional<fu> optional8 = list7.stream().filter(fu -> !zd.f(fu)).filter(fu -> zd.a(fu, apg)).filter(fu -> zd.j(apg)).findFirst();
		optional8.ifPresent(fu -> apg.cI().a(awp.m, new awr(fu, this.b, 0)));
	}
}
