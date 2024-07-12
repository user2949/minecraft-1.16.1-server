import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

public class in implements ik {
	private final bvr a;
	private final List<iq> b;
	private final Set<cgl<?>> c = Sets.<cgl<?>>newHashSet();
	private final List<io> d = Lists.<io>newArrayList();

	private in(bvr bvr, List<iq> list) {
		this.a = bvr;
		this.b = list;
	}

	public in a(io io) {
		io.b().forEach(cgl -> {
			if (this.a.m().a(cgl.f()) != cgl) {
				throw new IllegalStateException("Property " + cgl + " is not defined for block " + this.a);
			} else if (!this.c.add(cgl)) {
				throw new IllegalStateException("Values of property " + cgl + " already defined for block " + this.a);
			}
		});
		this.d.add(io);
		return this;
	}

	public JsonElement get() {
		Stream<Pair<ip, List<iq>>> stream2 = Stream.of(Pair.of(ip.a(), this.b));

		for (io io4 : this.d) {
			Map<ip, List<iq>> map5 = io4.a();
			stream2 = stream2.flatMap(pair -> map5.entrySet().stream().map(entry -> {
					ip ip3 = ((ip)pair.getFirst()).a((ip)entry.getKey());
					List<iq> list4 = a((List<iq>)pair.getSecond(), (List<iq>)entry.getValue());
					return Pair.of(ip3, list4);
				}));
		}

		Map<String, JsonElement> map3 = new TreeMap();
		stream2.forEach(pair -> {
			JsonElement var10000 = (JsonElement)map3.put(((ip)pair.getFirst()).b(), iq.a((List<iq>)pair.getSecond()));
		});
		JsonObject jsonObject4 = new JsonObject();
		jsonObject4.add("variants", v.a(new JsonObject(), jsonObject -> map3.forEach(jsonObject::add)));
		return jsonObject4;
	}

	private static List<iq> a(List<iq> list1, List<iq> list2) {
		Builder<iq> builder3 = ImmutableList.builder();
		list1.forEach(iq -> list2.forEach(iq3 -> builder3.add(iq.a(iq, iq3))));
		return builder3.build();
	}

	@Override
	public bvr a() {
		return this.a;
	}

	public static in a(bvr bvr) {
		return new in(bvr, ImmutableList.of(iq.a()));
	}

	public static in a(bvr bvr, iq iq) {
		return new in(bvr, ImmutableList.of(iq));
	}

	public static in a(bvr bvr, iq... arr) {
		return new in(bvr, ImmutableList.copyOf(arr));
	}
}
