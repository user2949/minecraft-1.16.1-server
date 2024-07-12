import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class bq extends ci<bq.a> {
	private static final uh a = new uh("killed_by_crossbow");

	@Override
	public uh a() {
		return a;
	}

	public bq.a b(JsonObject jsonObject, be.b b, av av) {
		be.b[] arr5 = be.b.b(jsonObject, "victims", av);
		bx.d d6 = bx.d.a(jsonObject.get("unique_entity_types"));
		return new bq.a(b, arr5, d6);
	}

	public void a(ze ze, Collection<aom> collection) {
		List<dat> list4 = Lists.<dat>newArrayList();
		Set<aoq<?>> set5 = Sets.<aoq<?>>newHashSet();

		for (aom aom7 : collection) {
			set5.add(aom7.U());
			list4.add(be.b(ze, aom7));
		}

		this.a(ze, a -> a.a(list4, set5.size()));
	}

	public static class a extends aj {
		private final be.b[] a;
		private final bx.d b;

		public a(be.b b, be.b[] arr, bx.d d) {
			super(bq.a, b);
			this.a = arr;
			this.b = d;
		}

		public static bq.a a(be.a... arr) {
			be.b[] arr2 = new be.b[arr.length];

			for (int integer3 = 0; integer3 < arr.length; integer3++) {
				be.a a4 = arr[integer3];
				arr2[integer3] = be.b.a(a4.b());
			}

			return new bq.a(be.b.a, arr2, bx.d.e);
		}

		public static bq.a a(bx.d d) {
			be.b[] arr2 = new be.b[0];
			return new bq.a(be.b.a, arr2, d);
		}

		public boolean a(Collection<dat> collection, int integer) {
			if (this.a.length > 0) {
				List<dat> list4 = Lists.<dat>newArrayList(collection);

				for (be.b b8 : this.a) {
					boolean boolean9 = false;
					Iterator<dat> iterator10 = list4.iterator();

					while (iterator10.hasNext()) {
						dat dat11 = (dat)iterator10.next();
						if (b8.a(dat11)) {
							iterator10.remove();
							boolean9 = true;
							break;
						}
					}

					if (!boolean9) {
						return false;
					}
				}
			}

			return this.b.d(integer);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("victims", be.b.a(this.a, cg));
			jsonObject3.add("unique_entity_types", this.b.d());
			return jsonObject3;
		}
	}
}
