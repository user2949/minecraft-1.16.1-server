import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.apache.commons.lang3.ArrayUtils;

public abstract class dbq extends dbo {
	protected final int c;
	protected final int e;
	protected final dch[] f;
	private final BiFunction<bki, dat, bki> g;
	private final dbn h = new dbq.c() {
		@Override
		public void a(Consumer<bki> consumer, dat dat) {
			dbq.this.a(dch.a(dbq.this.g, consumer, dat), dat);
		}
	};

	protected dbq(int integer1, int integer2, ddm[] arr, dch[] arr) {
		super(arr);
		this.c = integer1;
		this.e = integer2;
		this.f = arr;
		this.g = dcj.a(arr);
	}

	@Override
	public void a(dbe dbe) {
		super.a(dbe);

		for (int integer3 = 0; integer3 < this.f.length; integer3++) {
			this.f[integer3].a(dbe.b(".functions[" + integer3 + "]"));
		}
	}

	protected abstract void a(Consumer<bki> consumer, dat dat);

	@Override
	public boolean expand(dat dat, Consumer<dbn> consumer) {
		if (this.a(dat)) {
			consumer.accept(this.h);
			return true;
		} else {
			return false;
		}
	}

	public static dbq.a<?> a(dbq.d d) {
		return new dbq.b(d);
	}

	public abstract static class a<T extends dbq.a<T>> extends dbo.a<T> implements dce<T> {
		protected int a = 1;
		protected int b = 0;
		private final List<dch> c = Lists.<dch>newArrayList();

		public T b(dch.a a) {
			this.c.add(a.b());
			return this.d();
		}

		protected dch[] a() {
			return (dch[])this.c.toArray(new dch[0]);
		}

		public T a(int integer) {
			this.a = integer;
			return this.d();
		}

		public T b(int integer) {
			this.b = integer;
			return this.d();
		}
	}

	static class b extends dbq.a<dbq.b> {
		private final dbq.d c;

		public b(dbq.d d) {
			this.c = d;
		}

		protected dbq.b d() {
			return this;
		}

		@Override
		public dbo b() {
			return this.c.build(this.a, this.b, this.f(), this.a());
		}
	}

	public abstract class c implements dbn {
		protected c() {
		}

		@Override
		public int a(float float1) {
			return Math.max(aec.d((float)dbq.this.c + (float)dbq.this.e * float1), 0);
		}
	}

	@FunctionalInterface
	public interface d {
		dbq build(int integer1, int integer2, ddm[] arr, dch[] arr);
	}

	public abstract static class e<T extends dbq> extends dbo.b<T> {
		public void a(JsonObject jsonObject, T dbq, JsonSerializationContext jsonSerializationContext) {
			if (dbq.c != 1) {
				jsonObject.addProperty("weight", dbq.c);
			}

			if (dbq.e != 0) {
				jsonObject.addProperty("quality", dbq.e);
			}

			if (!ArrayUtils.isEmpty((Object[])dbq.f)) {
				jsonObject.add("functions", jsonSerializationContext.serialize(dbq.f));
			}
		}

		public final T b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			int integer5 = adt.a(jsonObject, "weight", 1);
			int integer6 = adt.a(jsonObject, "quality", 0);
			dch[] arr7 = adt.a(jsonObject, "functions", new dch[0], jsonDeserializationContext, dch[].class);
			return this.b(jsonObject, jsonDeserializationContext, integer5, integer6, arr, arr7);
		}

		protected abstract T b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int integer3, int integer4, ddm[] arr, dch[] arr);
	}
}
