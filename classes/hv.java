import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import java.io.IOException;
import java.nio.file.Path;

public class hv implements hl {
	private static final Gson b = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private final hk c;

	public hv(hk hk) {
		this.c = hk;
	}

	@Override
	public void a(hm hm) throws IOException {
		Path path3 = this.c.b().resolve("reports/commands.json");
		CommandDispatcher<cz> commandDispatcher4 = new da(da.a.ALL).a();
		hl.a(b, hm, fh.a(commandDispatcher4, commandDispatcher4.getRoot()), path3);
	}

	@Override
	public String a() {
		return "Command Syntax";
	}
}
