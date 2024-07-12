import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.bridge.game.GameVersion;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class o implements GameVersion {
	private static final Logger b = LogManager.getLogger();
	public static final GameVersion a = new o();
	private final String c;
	private final String d;
	private final boolean e;
	private final int f;
	private final int g;
	private final int h;
	private final Date i;
	private final String j;

	private o() {
		this.c = UUID.randomUUID().toString().replaceAll("-", "");
		this.d = "1.16.1";
		this.e = true;
		this.f = 2567;
		this.g = 736;
		this.h = 5;
		this.i = new Date();
		this.j = "1.16.1";
	}

	private o(JsonObject jsonObject) {
		this.c = adt.h(jsonObject, "id");
		this.d = adt.h(jsonObject, "name");
		this.j = adt.h(jsonObject, "release_target");
		this.e = adt.j(jsonObject, "stable");
		this.f = adt.n(jsonObject, "world_version");
		this.g = adt.n(jsonObject, "protocol_version");
		this.h = adt.n(jsonObject, "pack_version");
		this.i = Date.from(ZonedDateTime.parse(adt.h(jsonObject, "build_time")).toInstant());
	}

	public static GameVersion a() {
		try {
			InputStream inputStream1 = o.class.getResourceAsStream("/version.json");
			Throwable var1 = null;

			o var4;
			try {
				if (inputStream1 == null) {
					b.warn("Missing version information!");
					return a;
				}

				InputStreamReader inputStreamReader3 = new InputStreamReader(inputStream1);
				Throwable var3 = null;

				try {
					var4 = new o(adt.a(inputStreamReader3));
				} catch (Throwable var30) {
					var3 = var30;
					throw var30;
				} finally {
					if (inputStreamReader3 != null) {
						if (var3 != null) {
							try {
								inputStreamReader3.close();
							} catch (Throwable var29) {
								var3.addSuppressed(var29);
							}
						} else {
							inputStreamReader3.close();
						}
					}
				}
			} catch (Throwable var32) {
				var1 = var32;
				throw var32;
			} finally {
				if (inputStream1 != null) {
					if (var1 != null) {
						try {
							inputStream1.close();
						} catch (Throwable var28) {
							var1.addSuppressed(var28);
						}
					} else {
						inputStream1.close();
					}
				}
			}

			return var4;
		} catch (JsonParseException | IOException var34) {
			throw new IllegalStateException("Game version information is corrupt", var34);
		}
	}

	@Override
	public String getId() {
		return this.c;
	}

	@Override
	public String getName() {
		return this.d;
	}

	@Override
	public String getReleaseTarget() {
		return this.j;
	}

	@Override
	public int getWorldVersion() {
		return this.f;
	}

	@Override
	public int getProtocolVersion() {
		return this.g;
	}

	@Override
	public int getPackVersion() {
		return this.h;
	}

	@Override
	public Date getBuildTime() {
		return this.i;
	}

	@Override
	public boolean isStable() {
		return this.e;
	}
}
