import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.IntegerArgumentType;

public class fn implements fg<IntegerArgumentType> {
	public void a(IntegerArgumentType integerArgumentType, mg mg) {
		boolean boolean4 = integerArgumentType.getMinimum() != Integer.MIN_VALUE;
		boolean boolean5 = integerArgumentType.getMaximum() != Integer.MAX_VALUE;
		mg.writeByte(fk.a(boolean4, boolean5));
		if (boolean4) {
			mg.writeInt(integerArgumentType.getMinimum());
		}

		if (boolean5) {
			mg.writeInt(integerArgumentType.getMaximum());
		}
	}

	public IntegerArgumentType b(mg mg) {
		byte byte3 = mg.readByte();
		int integer4 = fk.a(byte3) ? mg.readInt() : Integer.MIN_VALUE;
		int integer5 = fk.b(byte3) ? mg.readInt() : Integer.MAX_VALUE;
		return IntegerArgumentType.integer(integer4, integer5);
	}

	public void a(IntegerArgumentType integerArgumentType, JsonObject jsonObject) {
		if (integerArgumentType.getMinimum() != Integer.MIN_VALUE) {
			jsonObject.addProperty("min", integerArgumentType.getMinimum());
		}

		if (integerArgumentType.getMaximum() != Integer.MAX_VALUE) {
			jsonObject.addProperty("max", integerArgumentType.getMaximum());
		}
	}
}
