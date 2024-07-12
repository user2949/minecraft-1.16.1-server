import com.google.common.collect.Maps;
import java.util.Map;

public class cqg {
	private final Map<uh, cqf> a = Maps.<uh, cqf>newHashMap();

	public cqg() {
		this.a(cqf.b);
	}

	public void a(cqf cqf) {
		this.a.put(cqf.b(), cqf);
	}

	public cqf a(uh uh) {
		cqf cqf3 = (cqf)this.a.get(uh);
		return cqf3 != null ? cqf3 : cqf.c;
	}
}
