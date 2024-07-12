import javax.annotation.Nullable;

public interface amx {
	void aa_();

	static void a(@Nullable Object object) {
		if (object instanceof amx) {
			((amx)object).aa_();
		}
	}
}
