import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;

public final class cxa extends cfl<cwz, cxa> {
	public static final Codec<cxa> a = a(gl.ah, cwz::h).stable();

	public cxa(cwz cwz, ImmutableMap<cgl<?>, Comparable<?>> immutableMap, MapCodec<cxa> mapCodec) {
		super(cwz, immutableMap, mapCodec);
	}

	public cwz a() {
		return this.c;
	}

	public boolean b() {
		return this.a().c(this);
	}

	public boolean c() {
		return this.a().b();
	}

	public float a(bpg bpg, fu fu) {
		return this.a().a(this, bpg, fu);
	}

	public float d() {
		return this.a().a(this);
	}

	public int e() {
		return this.a().d(this);
	}

	public void a(bqb bqb, fu fu) {
		this.a().a(bqb, fu, this);
	}

	public boolean f() {
		return this.a().j();
	}

	public void b(bqb bqb, fu fu, Random random) {
		this.a().b(bqb, fu, this, random);
	}

	public dem c(bpg bpg, fu fu) {
		return this.a().a(bpg, fu, this);
	}

	public cfj g() {
		return this.a().b(this);
	}

	public boolean a(adf<cwz> adf) {
		return this.a().a(adf);
	}

	public float i() {
		return this.a().c();
	}

	public boolean a(bpg bpg, fu fu, cwz cwz, fz fz) {
		return this.a().a(this, bpg, fu, cwz, fz);
	}

	public dfg d(bpg bpg, fu fu) {
		return this.a().b(this, bpg, fu);
	}
}
