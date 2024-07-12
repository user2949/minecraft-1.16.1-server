import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class bkf {
	private final Map<bke, bkf.a> a = Maps.<bke, bkf.a>newHashMap();
	private int b;

	public boolean a(bke bke) {
		return this.a(bke, 0.0F) > 0.0F;
	}

	public float a(bke bke, float float2) {
		bkf.a a4 = (bkf.a)this.a.get(bke);
		if (a4 != null) {
			float float5 = (float)(a4.c - a4.b);
			float float6 = (float)a4.c - ((float)this.b + float2);
			return aec.a(float6 / float5, 0.0F, 1.0F);
		} else {
			return 0.0F;
		}
	}

	public void a() {
		this.b++;
		if (!this.a.isEmpty()) {
			Iterator<Entry<bke, bkf.a>> iterator2 = this.a.entrySet().iterator();

			while (iterator2.hasNext()) {
				Entry<bke, bkf.a> entry3 = (Entry<bke, bkf.a>)iterator2.next();
				if (((bkf.a)entry3.getValue()).c <= this.b) {
					iterator2.remove();
					this.c((bke)entry3.getKey());
				}
			}
		}
	}

	public void a(bke bke, int integer) {
		this.a.put(bke, new bkf.a(this.b, this.b + integer));
		this.b(bke, integer);
	}

	protected void b(bke bke, int integer) {
	}

	protected void c(bke bke) {
	}

	class a {
		private final int b;
		private final int c;

		private a(int integer2, int integer3) {
			this.b = integer2;
			this.c = integer3;
		}
	}
}
