import java.io.IOException;

public class pp implements ni<nl> {
	private ug<cif> a;
	private ug<bqb> b;
	private long c;
	private bpy d;
	private bpy e;
	private boolean f;
	private boolean g;
	private boolean h;

	public pp() {
	}

	public pp(ug<cif> ug1, ug<bqb> ug2, long long3, bpy bpy4, bpy bpy5, boolean boolean6, boolean boolean7, boolean boolean8) {
		this.a = ug1;
		this.b = ug2;
		this.c = long3;
		this.d = bpy4;
		this.e = bpy5;
		this.f = boolean6;
		this.g = boolean7;
		this.h = boolean8;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = ug.a(gl.ad, mg.o());
		this.b = ug.a(gl.ae, mg.o());
		this.c = mg.readLong();
		this.d = bpy.a(mg.readUnsignedByte());
		this.e = bpy.a(mg.readUnsignedByte());
		this.f = mg.readBoolean();
		this.g = mg.readBoolean();
		this.h = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a.a());
		mg.a(this.b.a());
		mg.writeLong(this.c);
		mg.writeByte(this.d.a());
		mg.writeByte(this.e.a());
		mg.writeBoolean(this.f);
		mg.writeBoolean(this.g);
		mg.writeBoolean(this.h);
	}
}
