import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ac {
	private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
	private Date b;

	public boolean a() {
		return this.b != null;
	}

	public void b() {
		this.b = new Date();
	}

	public void c() {
		this.b = null;
	}

	public Date d() {
		return this.b;
	}

	public String toString() {
		return "CriterionProgress{obtained=" + (this.b == null ? "false" : this.b) + '}';
	}

	public void a(mg mg) {
		mg.writeBoolean(this.b != null);
		if (this.b != null) {
			mg.a(this.b);
		}
	}

	public JsonElement e() {
		return (JsonElement)(this.b != null ? new JsonPrimitive(a.format(this.b)) : JsonNull.INSTANCE);
	}

	public static ac b(mg mg) {
		ac ac2 = new ac();
		if (mg.readBoolean()) {
			ac2.b = mg.p();
		}

		return ac2;
	}

	public static ac a(String string) {
		ac ac2 = new ac();

		try {
			ac2.b = a.parse(string);
			return ac2;
		} catch (ParseException var3) {
			throw new JsonSyntaxException("Invalid datetime: " + string, var3);
		}
	}
}
