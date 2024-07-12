import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;

public abstract class mn implements mx {
	protected final List<mr> a = Lists.<mr>newArrayList();
	private nb d = nb.b;

	@Override
	public mx a(mr mr) {
		this.a.add(mr);
		return this;
	}

	@Override
	public String a() {
		return "";
	}

	@Override
	public List<mr> b() {
		return this.a;
	}

	@Override
	public mx a(nb nb) {
		this.d = nb;
		return this;
	}

	@Override
	public nb c() {
		return this.d;
	}

	public abstract mn f();

	@Override
	public final mx e() {
		mn mn2 = this.d();
		mn2.a.addAll(this.a);
		mn2.a(this.d);
		return mn2;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof mn)) {
			return false;
		} else {
			mn mn3 = (mn)object;
			return this.a.equals(mn3.a) && Objects.equals(this.c(), mn3.c());
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.c(), this.a});
	}

	public String toString() {
		return "BaseComponent{style=" + this.d + ", siblings=" + this.a + '}';
	}
}
