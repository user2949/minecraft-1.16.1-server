import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class aak implements aai<aaj> {
	public aaj a(JsonObject jsonObject) {
		mr mr3 = mr.a.a(jsonObject.get("description"));
		if (mr3 == null) {
			throw new JsonParseException("Invalid/missing description!");
		} else {
			int integer4 = adt.n(jsonObject, "pack_format");
			return new aaj(mr3, integer4);
		}
	}

	@Override
	public String a() {
		return "pack";
	}
}
