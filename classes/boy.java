import java.util.OptionalInt;
import javax.annotation.Nullable;

public interface boy {
	void f(@Nullable bec bec);

	@Nullable
	bec eN();

	bpa eP();

	void a(boz boz);

	void k(bki bki);

	bqb eV();

	int eM();

	void t(int integer);

	boolean eQ();

	ack eR();

	default boolean fa() {
		return false;
	}

	default void a(bec bec, mr mr, int integer) {
		OptionalInt optionalInt5 = bec.a(new ann((integerx, beb, becx) -> new bhm(integerx, beb, this), mr));
		if (optionalInt5.isPresent()) {
			bpa bpa6 = this.eP();
			if (!bpa6.isEmpty()) {
				bec.a(optionalInt5.getAsInt(), bpa6, integer, this.eM(), this.eQ(), this.fa());
			}
		}
	}
}
