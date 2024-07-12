import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

public class fk {
	public static void a() {
		fh.a("brigadier:bool", BoolArgumentType.class, new fi(BoolArgumentType::bool));
		fh.a("brigadier:float", FloatArgumentType.class, new fm());
		fh.a("brigadier:double", DoubleArgumentType.class, new fl());
		fh.a("brigadier:integer", IntegerArgumentType.class, new fn());
		fh.a("brigadier:long", LongArgumentType.class, new fo());
		fh.a("brigadier:string", StringArgumentType.class, new fp());
	}

	public static byte a(boolean boolean1, boolean boolean2) {
		byte byte3 = 0;
		if (boolean1) {
			byte3 = (byte)(byte3 | 1);
		}

		if (boolean2) {
			byte3 = (byte)(byte3 | 2);
		}

		return byte3;
	}

	public static boolean a(byte byte1) {
		return (byte1 & 1) != 0;
	}

	public static boolean b(byte byte1) {
		return (byte1 & 2) != 0;
	}
}
