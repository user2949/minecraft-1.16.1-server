import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ap extends ci<ap.a> {
	private static final uh a = new uh("channeled_lightning");

	@Override
	public uh a() {
		return a;
	}

	public ap.a b(JsonObject jsonObject, be.b b, av av) {
		be.b[] arr5 = be.b.b(jsonObject, "victims", av);
		return new ap.a(b, arr5);
	}

	public void a(ze ze, Collection<? extends aom> collection) {
		List<dat> list4 = (List<dat>)collection.stream().map(aom -> be.b(ze, aom)).collect(Collectors.toList());
		this.a(ze, a -> a.a(list4));
	}

	public static class a extends aj {
		private final be.b[] a;

		public a(be.b b, be.b[] arr) {
			super(ap.a, b);
			this.a = arr;
		}

		public static ap.a a(be... arr) {
			return new ap.a(be.b.a, (be.b[])Stream.of(arr).map(be.b::a).toArray(be.b[]::new));
		}

		public boolean a(Collection<? extends dat> collection) {
			for (be.b b6 : this.a) {
				boolean boolean7 = false;

				for (dat dat9 : collection) {
					if (b6.a(dat9)) {
						boolean7 = true;
						break;
					}
				}

				if (!boolean7) {
					return false;
				}
			}

			return true;
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("victims", be.b.a(this.a, cg));
			return jsonObject3;
		}
	}
}
