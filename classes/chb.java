import java.io.IOException;
import javax.annotation.Nullable;

public abstract class chb implements chl, AutoCloseable {
	@Nullable
	public chj a(int integer1, int integer2, boolean boolean3) {
		return (chj)this.a(integer1, integer2, chc.m, boolean3);
	}

	@Nullable
	public chj a(int integer1, int integer2) {
		return this.a(integer1, integer2, false);
	}

	@Nullable
	@Override
	public bpg c(int integer1, int integer2) {
		return this.a(integer1, integer2, chc.a, false);
	}

	public boolean b(int integer1, int integer2) {
		return this.a(integer1, integer2, chc.m, false) != null;
	}

	@Nullable
	public abstract cgy a(int integer1, int integer2, chc chc, boolean boolean4);

	public abstract String e();

	public void close() throws IOException {
	}

	public abstract cwr l();

	public void a(boolean boolean1, boolean boolean2) {
	}

	public void a(bph bph, boolean boolean2) {
	}

	public boolean a(aom aom) {
		return true;
	}

	public boolean a(bph bph) {
		return true;
	}

	public boolean a(fu fu) {
		return true;
	}
}
