import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;

public class oq implements ni<nl> {
	public static final oq.a a = new oq.a(0);
	public static final oq.a b = new oq.a(1);
	public static final oq.a c = new oq.a(2);
	public static final oq.a d = new oq.a(3);
	public static final oq.a e = new oq.a(4);
	public static final oq.a f = new oq.a(5);
	public static final oq.a g = new oq.a(6);
	public static final oq.a h = new oq.a(7);
	public static final oq.a i = new oq.a(8);
	public static final oq.a j = new oq.a(9);
	public static final oq.a k = new oq.a(10);
	public static final oq.a l = new oq.a(11);
	private oq.a m;
	private float n;

	public oq() {
	}

	public oq(oq.a a, float float2) {
		this.m = a;
		this.n = float2;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.m = oq.a.a.get(mg.readUnsignedByte());
		this.n = mg.readFloat();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.m.b);
		mg.writeFloat(this.n);
	}

	public void a(nl nl) {
		nl.a(this);
	}

	public static class a {
		private static final Int2ObjectMap<oq.a> a = new Int2ObjectOpenHashMap<>();
		private final int b;

		public a(int integer) {
			this.b = integer;
			a.put(integer, this);
		}
	}
}
