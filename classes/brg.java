import com.google.common.hash.Hashing;

public class brg {
	private final brg.a a;
	private final long b;
	private final brj c;

	public brg(brg.a a, long long2, brj brj) {
		this.a = a;
		this.b = long2;
		this.c = brj;
	}

	public static long a(long long1) {
		return Hashing.sha256().hashLong(long1).asLong();
	}

	public brg a(brh brh) {
		return new brg(brh, this.b, this.c);
	}

	public bre a(fu fu) {
		return this.c.a(this.b, fu.u(), fu.v(), fu.w(), this.a);
	}

	public interface a {
		bre b(int integer1, int integer2, int integer3);
	}
}
