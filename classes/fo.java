import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.LongArgumentType;

public class fo implements fg<LongArgumentType> {
	public void a(LongArgumentType longArgumentType, mg mg) {
		boolean boolean4 = longArgumentType.getMinimum() != Long.MIN_VALUE;
		boolean boolean5 = longArgumentType.getMaximum() != Long.MAX_VALUE;
		mg.writeByte(fk.a(boolean4, boolean5));
		if (boolean4) {
			mg.writeLong(longArgumentType.getMinimum());
		}

		if (boolean5) {
			mg.writeLong(longArgumentType.getMaximum());
		}
	}

	public LongArgumentType b(mg mg) {
		byte byte3 = mg.readByte();
		long long4 = fk.a(byte3) ? mg.readLong() : Long.MIN_VALUE;
		long long6 = fk.b(byte3) ? mg.readLong() : Long.MAX_VALUE;
		return LongArgumentType.longArg(long4, long6);
	}

	public void a(LongArgumentType longArgumentType, JsonObject jsonObject) {
		if (longArgumentType.getMinimum() != Long.MIN_VALUE) {
			jsonObject.addProperty("min", longArgumentType.getMinimum());
		}

		if (longArgumentType.getMaximum() != Long.MAX_VALUE) {
			jsonObject.addProperty("max", longArgumentType.getMaximum());
		}
	}
}
