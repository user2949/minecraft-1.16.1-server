import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;

public class bmb {
	private final String a;
	private final ImmutableList<aog> b;

	public static bmb a(String string) {
		return gl.an.a(uh.a(string));
	}

	public bmb(aog... arr) {
		this(null, arr);
	}

	public bmb(@Nullable String string, aog... arr) {
		this.a = string;
		this.b = ImmutableList.copyOf(arr);
	}

	public String b(String string) {
		return string + (this.a == null ? gl.an.b(this).a() : this.a);
	}

	public List<aog> a() {
		return this.b;
	}

	public boolean b() {
		if (!this.b.isEmpty()) {
			for (aog aog3 : this.b) {
				if (aog3.a().a()) {
					return true;
				}
			}
		}

		return false;
	}
}
