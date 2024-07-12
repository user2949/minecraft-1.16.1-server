import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.FloatArgumentType;

public class fm implements fg<FloatArgumentType> {
	public void a(FloatArgumentType floatArgumentType, mg mg) {
		boolean boolean4 = floatArgumentType.getMinimum() != -Float.MAX_VALUE;
		boolean boolean5 = floatArgumentType.getMaximum() != Float.MAX_VALUE;
		mg.writeByte(fk.a(boolean4, boolean5));
		if (boolean4) {
			mg.writeFloat(floatArgumentType.getMinimum());
		}

		if (boolean5) {
			mg.writeFloat(floatArgumentType.getMaximum());
		}
	}

	public FloatArgumentType b(mg mg) {
		byte byte3 = mg.readByte();
		float float4 = fk.a(byte3) ? mg.readFloat() : -Float.MAX_VALUE;
		float float5 = fk.b(byte3) ? mg.readFloat() : Float.MAX_VALUE;
		return FloatArgumentType.floatArg(float4, float5);
	}

	public void a(FloatArgumentType floatArgumentType, JsonObject jsonObject) {
		if (floatArgumentType.getMinimum() != -Float.MAX_VALUE) {
			jsonObject.addProperty("min", floatArgumentType.getMinimum());
		}

		if (floatArgumentType.getMaximum() != Float.MAX_VALUE) {
			jsonObject.addProperty("max", floatArgumentType.getMaximum());
		}
	}
}
