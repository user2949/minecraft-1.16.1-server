import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class ot implements ni<nl> {
	private int a;
	private int b;
	private int c;
	private le d;
	@Nullable
	private cgz e;
	private byte[] f;
	private List<le> g;
	private boolean h;
	private boolean i;

	public ot() {
	}

	public ot(chj chj, int integer, boolean boolean3) {
		bph bph5 = chj.g();
		this.a = bph5.b;
		this.b = bph5.c;
		this.h = integer == 65535;
		this.i = boolean3;
		this.d = new le();

		for (Entry<cio.a, cio> entry7 : chj.f()) {
			if (((cio.a)entry7.getKey()).c()) {
				this.d.a(((cio.a)entry7.getKey()).b(), new ll(((cio)entry7.getValue()).a()));
			}
		}

		if (this.h) {
			this.e = chj.i().b();
		}

		this.f = new byte[this.a(chj, integer)];
		this.c = this.a(new mg(this.k()), chj, integer);
		this.g = Lists.<le>newArrayList();

		for (Entry<fu, cdl> entry7x : chj.y().entrySet()) {
			fu fu8 = (fu)entry7x.getKey();
			cdl cdl9 = (cdl)entry7x.getValue();
			int integer10 = fu8.v() >> 4;
			if (this.f() || (integer & 1 << integer10) != 0) {
				le le11 = cdl9.b();
				this.g.add(le11);
			}
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readInt();
		this.b = mg.readInt();
		this.h = mg.readBoolean();
		this.i = mg.readBoolean();
		this.c = mg.i();
		this.d = mg.l();
		if (this.h) {
			this.e = new cgz(mg);
		}

		int integer3 = mg.i();
		if (integer3 > 2097152) {
			throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
		} else {
			this.f = new byte[integer3];
			mg.readBytes(this.f);
			int integer4 = mg.i();
			this.g = Lists.<le>newArrayList();

			for (int integer5 = 0; integer5 < integer4; integer5++) {
				this.g.add(mg.l());
			}
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeInt(this.a);
		mg.writeInt(this.b);
		mg.writeBoolean(this.h);
		mg.writeBoolean(this.i);
		mg.d(this.c);
		mg.a(this.d);
		if (this.e != null) {
			this.e.a(mg);
		}

		mg.d(this.f.length);
		mg.writeBytes(this.f);
		mg.d(this.g.size());

		for (le le4 : this.g) {
			mg.a(le4);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}

	private ByteBuf k() {
		ByteBuf byteBuf2 = Unpooled.wrappedBuffer(this.f);
		byteBuf2.writerIndex(0);
		return byteBuf2;
	}

	public int a(mg mg, chj chj, int integer) {
		int integer5 = 0;
		chk[] arr6 = chj.d();
		int integer7 = 0;

		for (int integer8 = arr6.length; integer7 < integer8; integer7++) {
			chk chk9 = arr6[integer7];
			if (chk9 != chj.a && (!this.f() || !chk9.c()) && (integer & 1 << integer7) != 0) {
				integer5 |= 1 << integer7;
				chk9.b(mg);
			}
		}

		return integer5;
	}

	protected int a(chj chj, int integer) {
		int integer4 = 0;
		chk[] arr5 = chj.d();
		int integer6 = 0;

		for (int integer7 = arr5.length; integer6 < integer7; integer6++) {
			chk chk8 = arr5[integer6];
			if (chk8 != chj.a && (!this.f() || !chk8.c()) && (integer & 1 << integer6) != 0) {
				integer4 += chk8.j();
			}
		}

		return integer4;
	}

	public boolean f() {
		return this.h;
	}
}
