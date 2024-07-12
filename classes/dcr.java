import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dcr extends dcg {
	private static final Logger a = LogManager.getLogger();
	private final mr b;
	@Nullable
	private final dat.c d;

	private dcr(ddm[] arr, @Nullable mr mr, @Nullable dat.c c) {
		super(arr);
		this.b = mr;
		this.d = c;
	}

	@Override
	public dci b() {
		return dcj.j;
	}

	@Override
	public Set<dcx<?>> a() {
		return this.d != null ? ImmutableSet.of(this.d.a()) : ImmutableSet.of();
	}

	public static UnaryOperator<mr> a(dat dat, @Nullable dat.c c) {
		if (c != null) {
			aom aom3 = dat.c(c.a());
			if (aom3 != null) {
				cz cz4 = aom3.cv().a(2);
				return mr -> {
					try {
						return ms.a(cz4, mr, aom3, 0);
					} catch (CommandSyntaxException var4) {
						a.warn("Failed to resolve text component", (Throwable)var4);
						return mr;
					}
				};
			}
		}

		return mr -> mr;
	}

	@Override
	public bki a(bki bki, dat dat) {
		if (this.b != null) {
			bki.a((mr)a(dat, this.d).apply(this.b));
		}

		return bki;
	}

	public static class a extends dcg.c<dcr> {
		public void a(JsonObject jsonObject, dcr dcr, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcr, jsonSerializationContext);
			if (dcr.b != null) {
				jsonObject.add("name", mr.a.b(dcr.b));
			}

			if (dcr.d != null) {
				jsonObject.add("entity", jsonSerializationContext.serialize(dcr.d));
			}
		}

		public dcr b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			mr mr5 = mr.a.a(jsonObject.get("name"));
			dat.c c6 = adt.a(jsonObject, "entity", null, jsonDeserializationContext, dat.c.class);
			return new dcr(arr, mr5, c6);
		}
	}
}
