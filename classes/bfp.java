import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import java.util.List;

public class bfp {
	private final List<bfm> a = Lists.<bfm>newArrayList();
	private int b;

	public bfp a(int integer, float float2) {
		this.a.add(new bfm(integer, float2));
		this.b();
		return this;
	}

	private void b() {
		Int2ObjectSortedMap<bfm> int2ObjectSortedMap2 = new Int2ObjectAVLTreeMap<>();
		this.a.forEach(bfm -> {
			bfm var10000 = int2ObjectSortedMap2.put(bfm.a(), bfm);
		});
		this.a.clear();
		this.a.addAll(int2ObjectSortedMap2.values());
		this.b = 0;
	}

	public float a(int integer) {
		if (this.a.size() <= 0) {
			return 0.0F;
		} else {
			bfm bfm3 = (bfm)this.a.get(this.b);
			bfm bfm4 = (bfm)this.a.get(this.a.size() - 1);
			boolean boolean5 = integer < bfm3.a();
			int integer6 = boolean5 ? 0 : this.b;
			float float7 = boolean5 ? bfm4.b() : bfm3.b();

			for (int integer8 = integer6; integer8 < this.a.size(); integer8++) {
				bfm bfm9 = (bfm)this.a.get(integer8);
				if (bfm9.a() > integer) {
					break;
				}

				this.b = integer8;
				float7 = bfm9.b();
			}

			return float7;
		}
	}
}
