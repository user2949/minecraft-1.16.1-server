import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class dbf extends dbh {
	dbf(dbo[] arr, ddm[] arr) {
		super(arr, arr);
	}

	@Override
	public dbp a() {
		return dbm.f;
	}

	@Override
	protected dbg a(dbg[] arr) {
		switch (arr.length) {
			case 0:
				return a;
			case 1:
				return arr[0];
			case 2:
				return arr[0].b(arr[1]);
			default:
				return (dat, consumer) -> {
					for (dbg dbg7 : arr) {
						if (dbg7.expand(dat, consumer)) {
							return true;
						}
					}

					return false;
				};
		}
	}

	@Override
	public void a(dbe dbe) {
		super.a(dbe);

		for (int integer3 = 0; integer3 < this.c.length - 1; integer3++) {
			if (ArrayUtils.isEmpty((Object[])this.c[integer3].d)) {
				dbe.a("Unreachable entry!");
			}
		}
	}

	public static dbf.a a(dbo.a<?>... arr) {
		return new dbf.a(arr);
	}

	public static class a extends dbo.a<dbf.a> {
		private final List<dbo> a = Lists.<dbo>newArrayList();

		public a(dbo.a<?>... arr) {
			for (dbo.a<?> a6 : arr) {
				this.a.add(a6.b());
			}
		}

		protected dbf.a d() {
			return this;
		}

		@Override
		public dbf.a a(dbo.a<?> a) {
			this.a.add(a.b());
			return this;
		}

		@Override
		public dbo b() {
			return new dbf((dbo[])this.a.toArray(new dbo[0]), this.f());
		}
	}
}
