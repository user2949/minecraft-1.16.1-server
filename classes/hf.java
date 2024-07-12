import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public interface hf {
	hg<?> b();

	void a(mg mg);

	String a();

	@Deprecated
	public interface a<T extends hf> {
		T b(hg<T> hg, StringReader stringReader) throws CommandSyntaxException;

		T b(hg<T> hg, mg mg);
	}
}
