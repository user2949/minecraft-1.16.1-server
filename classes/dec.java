import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dec<C> {
	private static final Logger b = LogManager.getLogger();
	public static final dec<MinecraftServer> a = new dec<MinecraftServer>().a(new ddz.a()).a(new dea.a());
	private final Map<uh, deb.a<C, ?>> c = Maps.<uh, deb.a<C, ?>>newHashMap();
	private final Map<Class<?>, deb.a<C, ?>> d = Maps.<Class<?>, deb.a<C, ?>>newHashMap();

	public dec<C> a(deb.a<C, ?> a) {
		this.c.put(a.a(), a);
		this.d.put(a.b(), a);
		return this;
	}

	private <T extends deb<C>> deb.a<C, T> a(Class<?> class1) {
		return (deb.a<C, T>)this.d.get(class1);
	}

	public <T extends deb<C>> le a(T deb) {
		deb.a<C, T> a3 = this.a(deb.getClass());
		le le4 = new le();
		a3.a(le4, deb);
		le4.a("Type", a3.a().toString());
		return le4;
	}

	@Nullable
	public deb<C> a(le le) {
		uh uh3 = uh.a(le.l("Type"));
		deb.a<C, ?> a4 = (deb.a<C, ?>)this.c.get(uh3);
		if (a4 == null) {
			b.error("Failed to deserialize timer callback: " + le);
			return null;
		} else {
			try {
				return a4.b(le);
			} catch (Exception var5) {
				b.error("Failed to deserialize timer callback: " + le, (Throwable)var5);
				return null;
			}
		}
	}
}
