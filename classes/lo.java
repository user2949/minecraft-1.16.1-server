import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class lo {
	public static le a(InputStream inputStream) throws IOException {
		DataInputStream dataInputStream2 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)));
		Throwable var2 = null;

		le var3;
		try {
			var3 = a(dataInputStream2, ln.a);
		} catch (Throwable var12) {
			var2 = var12;
			throw var12;
		} finally {
			if (dataInputStream2 != null) {
				if (var2 != null) {
					try {
						dataInputStream2.close();
					} catch (Throwable var11) {
						var2.addSuppressed(var11);
					}
				} else {
					dataInputStream2.close();
				}
			}
		}

		return var3;
	}

	public static void a(le le, OutputStream outputStream) throws IOException {
		DataOutputStream dataOutputStream3 = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));
		Throwable var3 = null;

		try {
			a(le, dataOutputStream3);
		} catch (Throwable var12) {
			var3 = var12;
			throw var12;
		} finally {
			if (dataOutputStream3 != null) {
				if (var3 != null) {
					try {
						dataOutputStream3.close();
					} catch (Throwable var11) {
						var3.addSuppressed(var11);
					}
				} else {
					dataOutputStream3.close();
				}
			}
		}
	}

	public static le a(DataInputStream dataInputStream) throws IOException {
		return a(dataInputStream, ln.a);
	}

	public static le a(DataInput dataInput, ln ln) throws IOException {
		lu lu3 = a(dataInput, 0, ln);
		if (lu3 instanceof le) {
			return (le)lu3;
		} else {
			throw new IOException("Root tag must be a named compound tag");
		}
	}

	public static void a(le le, DataOutput dataOutput) throws IOException {
		a((lu)le, dataOutput);
	}

	private static void a(lu lu, DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(lu.a());
		if (lu.a() != 0) {
			dataOutput.writeUTF("");
			lu.a(dataOutput);
		}
	}

	private static lu a(DataInput dataInput, int integer, ln ln) throws IOException {
		byte byte4 = dataInput.readByte();
		if (byte4 == 0) {
			return lg.b;
		} else {
			dataInput.readUTF();

			try {
				return lx.a(byte4).b(dataInput, integer, ln);
			} catch (IOException var7) {
				j j6 = j.a(var7, "Loading NBT data");
				k k7 = j6.a("NBT Tag");
				k7.a("Tag type", byte4);
				throw new s(j6);
			}
		}
	}
}
