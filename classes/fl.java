import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class fl implements fg<DoubleArgumentType> {
	public void a(DoubleArgumentType doubleArgumentType, mg mg) {
		boolean boolean4 = doubleArgumentType.getMinimum() != -Double.MAX_VALUE;
		boolean boolean5 = doubleArgumentType.getMaximum() != Double.MAX_VALUE;
		mg.writeByte(fk.a(boolean4, boolean5));
		if (boolean4) {
			mg.writeDouble(doubleArgumentType.getMinimum());
		}

		if (boolean5) {
			mg.writeDouble(doubleArgumentType.getMaximum());
		}
	}

	public DoubleArgumentType b(mg mg) {
		byte byte3 = mg.readByte();
		double double4 = fk.a(byte3) ? mg.readDouble() : -Double.MAX_VALUE;
		double double6 = fk.b(byte3) ? mg.readDouble() : Double.MAX_VALUE;
		return DoubleArgumentType.doubleArg(double4, double6);
	}

	public void a(DoubleArgumentType doubleArgumentType, JsonObject jsonObject) {
		if (doubleArgumentType.getMinimum() != -Double.MAX_VALUE) {
			jsonObject.addProperty("min", doubleArgumentType.getMinimum());
		}

		if (doubleArgumentType.getMaximum() != Double.MAX_VALUE) {
			jsonObject.addProperty("max", doubleArgumentType.getMaximum());
		}
	}
}
