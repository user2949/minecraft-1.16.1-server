import com.google.common.collect.Lists;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class cz implements db {
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("permissions.requires.player"));
	public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("permissions.requires.entity"));
	private final cy c;
	private final dem d;
	private final zd e;
	private final int f;
	private final String g;
	private final mr h;
	private final MinecraftServer i;
	private final boolean j;
	@Nullable
	private final aom k;
	private final ResultConsumer<cz> l;
	private final dg.a m;
	private final del n;

	public cz(cy cy, dem dem, del del, zd zd, int integer, String string, mr mr, MinecraftServer minecraftServer, @Nullable aom aom) {
		this(cy, dem, del, zd, integer, string, mr, minecraftServer, aom, false, (commandContext, boolean2, integerx) -> {
		}, dg.a.FEET);
	}

	protected cz(
		cy cy,
		dem dem,
		del del,
		zd zd,
		int integer,
		String string,
		mr mr,
		MinecraftServer minecraftServer,
		@Nullable aom aom,
		boolean boolean10,
		ResultConsumer<cz> resultConsumer,
		dg.a a
	) {
		this.c = cy;
		this.d = dem;
		this.e = zd;
		this.j = boolean10;
		this.k = aom;
		this.f = integer;
		this.g = string;
		this.h = mr;
		this.i = minecraftServer;
		this.l = resultConsumer;
		this.m = a;
		this.n = del;
	}

	public cz a(aom aom) {
		return this.k == aom ? this : new cz(this.c, this.d, this.n, this.e, this.f, aom.P().getString(), aom.d(), this.i, aom, this.j, this.l, this.m);
	}

	public cz a(dem dem) {
		return this.d.equals(dem) ? this : new cz(this.c, dem, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
	}

	public cz a(del del) {
		return this.n.c(del) ? this : new cz(this.c, this.d, del, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
	}

	public cz a(ResultConsumer<cz> resultConsumer) {
		return this.l.equals(resultConsumer) ? this : new cz(this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, resultConsumer, this.m);
	}

	public cz a(ResultConsumer<cz> resultConsumer, BinaryOperator<ResultConsumer<cz>> binaryOperator) {
		ResultConsumer<cz> resultConsumer4 = (ResultConsumer<cz>)binaryOperator.apply(this.l, resultConsumer);
		return this.a(resultConsumer4);
	}

	public cz a() {
		return this.j ? this : new cz(this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, true, this.l, this.m);
	}

	public cz a(int integer) {
		return integer == this.f ? this : new cz(this.c, this.d, this.n, this.e, integer, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
	}

	public cz b(int integer) {
		return integer <= this.f ? this : new cz(this.c, this.d, this.n, this.e, integer, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
	}

	public cz a(dg.a a) {
		return a == this.m ? this : new cz(this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, a);
	}

	public cz a(zd zd) {
		return zd == this.e ? this : new cz(this.c, this.d, this.n, zd, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
	}

	public cz a(aom aom, dg.a a) throws CommandSyntaxException {
		return this.b(a.a(aom));
	}

	public cz b(dem dem) throws CommandSyntaxException {
		dem dem3 = this.m.a(this);
		double double4 = dem.b - dem3.b;
		double double6 = dem.c - dem3.c;
		double double8 = dem.d - dem3.d;
		double double10 = (double)aec.a(double4 * double4 + double8 * double8);
		float float12 = aec.g((float)(-(aec.d(double6, double10) * 180.0F / (float)Math.PI)));
		float float13 = aec.g((float)(aec.d(double8, double4) * 180.0F / (float)Math.PI) - 90.0F);
		return this.a(new del(float12, float13));
	}

	public mr b() {
		return this.h;
	}

	public String c() {
		return this.g;
	}

	@Override
	public boolean c(int integer) {
		return this.f >= integer;
	}

	public dem d() {
		return this.d;
	}

	public zd e() {
		return this.e;
	}

	@Nullable
	public aom f() {
		return this.k;
	}

	public aom g() throws CommandSyntaxException {
		if (this.k == null) {
			throw b.create();
		} else {
			return this.k;
		}
	}

	public ze h() throws CommandSyntaxException {
		if (!(this.k instanceof ze)) {
			throw a.create();
		} else {
			return (ze)this.k;
		}
	}

	public del i() {
		return this.n;
	}

	public MinecraftServer j() {
		return this.i;
	}

	public dg.a k() {
		return this.m;
	}

	public void a(mr mr, boolean boolean2) {
		if (this.c.a() && !this.j) {
			this.c.a(mr, v.b);
		}

		if (boolean2 && this.c.S_() && !this.j) {
			this.b(mr);
		}
	}

	private void b(mr mr) {
		mr mr3 = new ne("chat.type.admin", this.b(), mr).a(new i[]{i.GRAY, i.ITALIC});
		if (this.i.aJ().b(bpx.n)) {
			for (ze ze5 : this.i.ac().s()) {
				if (ze5 != this.c && this.i.ac().h(ze5.ez())) {
					ze5.a(mr3, v.b);
				}
			}
		}

		if (this.c != this.i && this.i.aJ().b(bpx.k)) {
			this.i.a(mr3, v.b);
		}
	}

	public void a(mr mr) {
		if (this.c.b() && !this.j) {
			this.c.a(new nd("").a(mr).a(i.RED), v.b);
		}
	}

	public void a(CommandContext<cz> commandContext, boolean boolean2, int integer) {
		if (this.l != null) {
			this.l.onCommandComplete(commandContext, boolean2, integer);
		}
	}

	@Override
	public Collection<String> l() {
		return Lists.<String>newArrayList(this.i.J());
	}

	@Override
	public Collection<String> m() {
		return this.i.aF().f();
	}

	@Override
	public Collection<uh> n() {
		return gl.ag.b();
	}

	@Override
	public Stream<uh> o() {
		return this.i.aD().d();
	}

	@Override
	public CompletableFuture<Suggestions> a(CommandContext<db> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return null;
	}

	@Override
	public Set<ug<bqb>> p() {
		return this.i.E();
	}
}
