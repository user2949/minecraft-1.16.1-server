import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dcu extends dcg {
	private static final Logger a = LogManager.getLogger();

	private dcu(ddm[] arr) {
		super(arr);
	}

	@Override
	public dci b() {
		return dcj.f;
	}

	@Override
	public bki a(bki bki, dat dat) {
		if (bki.a()) {
			return bki;
		} else {
			Optional<bng> optional4 = dat.c().o().a(bmx.b, new anm(bki), dat.c());
			if (optional4.isPresent()) {
				bki bki5 = ((bng)optional4.get()).c();
				if (!bki5.a()) {
					bki bki6 = bki5.i();
					bki6.e(bki.E());
					return bki6;
				}
			}

			a.warn("Couldn't smelt {} because there is no smelting recipe", bki);
			return bki;
		}
	}

	public static dcg.a<?> c() {
		return a(dcu::new);
	}

	public static class a extends dcg.c<dcu> {
		public dcu b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			return new dcu(arr);
		}
	}
}
