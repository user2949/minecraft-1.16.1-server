import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class cnx implements cnr {
	public static final Codec<cnx> a = cnu.a.listOf().xmap(cnx::new, cnx -> cnx.b);
	private final List<cnu> b;

	public cnx(Map<String, Integer> map) {
		this((List<cnu>)map.entrySet().stream().map(entry -> new cnu(new uh((String)entry.getKey()), (Integer)entry.getValue())).collect(Collectors.toList()));
	}

	private cnx(List<cnu> list) {
		this.b = list;
	}

	public cnu a(Random random) {
		return (cnu)this.b.get(random.nextInt(this.b.size()));
	}
}
