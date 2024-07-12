import java.util.Iterator;
import java.util.List;

public class kk {
	private final kg a;
	private final List<kd> b;
	private long c;

	public void a(long long1) {
		try {
			this.c(long1);
		} catch (Exception var4) {
		}
	}

	public void b(long long1) {
		try {
			this.c(long1);
		} catch (Exception var4) {
			this.a.a(var4);
		}
	}

	private void c(long long1) {
		Iterator<kd> iterator4 = this.b.iterator();

		while (iterator4.hasNext()) {
			kd kd5 = (kd)iterator4.next();
			kd5.b.run();
			iterator4.remove();
			long long6 = long1 - this.c;
			long long8 = this.c;
			this.c = long1;
			if (kd5.a != null && kd5.a != long6) {
				this.a.a(new jz("Succeeded in invalid tick: expected " + (long8 + kd5.a) + ", but current tick is " + long1));
				break;
			}
		}
	}
}
