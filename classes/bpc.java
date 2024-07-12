import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public abstract class bpc implements cy {
	private static final SimpleDateFormat b = new SimpleDateFormat("HH:mm:ss");
	private static final mr c = new nd("@");
	private long d = -1L;
	private boolean e = true;
	private int f;
	private boolean g = true;
	@Nullable
	private mr h;
	private String i = "";
	private mr j = c;

	public int i() {
		return this.f;
	}

	public void a(int integer) {
		this.f = integer;
	}

	public mr j() {
		return this.h == null ? nd.d : this.h;
	}

	public le a(le le) {
		le.a("Command", this.i);
		le.b("SuccessCount", this.f);
		le.a("CustomName", mr.a.a(this.j));
		le.a("TrackOutput", this.g);
		if (this.h != null && this.g) {
			le.a("LastOutput", mr.a.a(this.h));
		}

		le.a("UpdateLastExecution", this.e);
		if (this.e && this.d > 0L) {
			le.a("LastExecution", this.d);
		}

		return le;
	}

	public void b(le le) {
		this.i = le.l("Command");
		this.f = le.h("SuccessCount");
		if (le.c("CustomName", 8)) {
			this.a(mr.a.a(le.l("CustomName")));
		}

		if (le.c("TrackOutput", 1)) {
			this.g = le.q("TrackOutput");
		}

		if (le.c("LastOutput", 8) && this.g) {
			try {
				this.h = mr.a.a(le.l("LastOutput"));
			} catch (Throwable var3) {
				this.h = new nd(var3.getMessage());
			}
		} else {
			this.h = null;
		}

		if (le.e("UpdateLastExecution")) {
			this.e = le.q("UpdateLastExecution");
		}

		if (this.e && le.e("LastExecution")) {
			this.d = le.i("LastExecution");
		} else {
			this.d = -1L;
		}
	}

	public void a(String string) {
		this.i = string;
		this.f = 0;
	}

	public String k() {
		return this.i;
	}

	public boolean a(bqb bqb) {
		if (bqb.v || bqb.Q() == this.d) {
			return false;
		} else if ("Searge".equalsIgnoreCase(this.i)) {
			this.h = new nd("#itzlipofutzli");
			this.f = 1;
			return true;
		} else {
			this.f = 0;
			MinecraftServer minecraftServer3 = this.d().l();
			if (minecraftServer3.l() && !aei.b(this.i)) {
				try {
					this.h = null;
					cz cz4 = this.h().a((commandContext, boolean2, integer) -> {
						if (boolean2) {
							this.f++;
						}
					});
					minecraftServer3.aB().a(cz4, this.i);
				} catch (Throwable var6) {
					j j5 = j.a(var6, "Executing command block");
					k k6 = j5.a("Command to be executed");
					k6.a("Command", this::k);
					k6.a("Name", (l<String>)(() -> this.l().getString()));
					throw new s(j5);
				}
			}

			if (this.e) {
				this.d = bqb.Q();
			} else {
				this.d = -1L;
			}

			return true;
		}
	}

	public mr l() {
		return this.j;
	}

	public void a(@Nullable mr mr) {
		if (mr != null) {
			this.j = mr;
		} else {
			this.j = c;
		}
	}

	@Override
	public void a(mr mr, UUID uUID) {
		if (this.g) {
			this.h = new nd("[" + b.format(new Date()) + "] ").a(mr);
			this.e();
		}
	}

	public abstract zd d();

	public abstract void e();

	public void b(@Nullable mr mr) {
		this.h = mr;
	}

	public void a(boolean boolean1) {
		this.g = boolean1;
	}

	public ang a(bec bec) {
		if (!bec.eV()) {
			return ang.PASS;
		} else {
			if (bec.cf().v) {
				bec.a(this);
			}

			return ang.a(bec.l.v);
		}
	}

	public abstract cz h();

	@Override
	public boolean a() {
		return this.d().S().b(bpx.n) && this.g;
	}

	@Override
	public boolean b() {
		return this.g;
	}

	@Override
	public boolean S_() {
		return this.d().S().b(bpx.h);
	}
}
