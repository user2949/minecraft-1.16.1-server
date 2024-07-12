import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class kq {
	private final Collection<kg> a = Lists.<kg>newArrayList();
	@Nullable
	private Collection<kh> b = Lists.<kh>newArrayList();

	public kq() {
	}

	public kq(Collection<kg> collection) {
		this.a.addAll(collection);
	}

	public void a(kg kg) {
		this.a.add(kg);
		this.b.forEach(kg::a);
	}

	public void a(kh kh) {
		this.b.add(kh);
		this.a.forEach(kg -> kg.a(kh));
	}

	public void a(Consumer<kg> consumer) {
		this.a(new kh() {
			@Override
			public void a(kg kg) {
			}

			@Override
			public void c(kg kg) {
				consumer.accept(kg);
			}
		});
	}

	public int a() {
		return (int)this.a.stream().filter(kg::i).filter(kg::q).count();
	}

	public int b() {
		return (int)this.a.stream().filter(kg::i).filter(kg::r).count();
	}

	public int c() {
		return (int)this.a.stream().filter(kg::k).count();
	}

	public boolean d() {
		return this.a() > 0;
	}

	public boolean e() {
		return this.b() > 0;
	}

	public int h() {
		return this.a.size();
	}

	public boolean i() {
		return this.c() == this.h();
	}

	public String j() {
		StringBuffer stringBuffer2 = new StringBuffer();
		stringBuffer2.append('[');
		this.a.forEach(kg -> {
			if (!kg.j()) {
				stringBuffer2.append(' ');
			} else if (kg.h()) {
				stringBuffer2.append('+');
			} else if (kg.i()) {
				stringBuffer2.append((char)(kg.q() ? 'X' : 'x'));
			} else {
				stringBuffer2.append('_');
			}
		});
		stringBuffer2.append(']');
		return stringBuffer2.toString();
	}

	public String toString() {
		return this.j();
	}
}
