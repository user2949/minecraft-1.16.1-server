import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;

public class va {
	private final Map<uh, uz> a = Maps.<uh, uz>newHashMap();

	@Nullable
	public uz a(uh uh) {
		return (uz)this.a.get(uh);
	}

	public uz a(uh uh, mr mr) {
		uz uz4 = new uz(uh, mr);
		this.a.put(uh, uz4);
		return uz4;
	}

	public void a(uz uz) {
		this.a.remove(uz.a());
	}

	public Collection<uh> a() {
		return this.a.keySet();
	}

	public Collection<uz> b() {
		return this.a.values();
	}

	public le c() {
		le le2 = new le();

		for (uz uz4 : this.a.values()) {
			le2.a(uz4.a().toString(), uz4.f());
		}

		return le2;
	}

	public void a(le le) {
		for (String string4 : le.d()) {
			uh uh5 = new uh(string4);
			this.a.put(uh5, uz.a(le.p(string4), uh5));
		}
	}

	public void a(ze ze) {
		for (uz uz4 : this.a.values()) {
			uz4.c(ze);
		}
	}

	public void b(ze ze) {
		for (uz uz4 : this.a.values()) {
			uz4.d(ze);
		}
	}
}
