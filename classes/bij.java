import org.apache.commons.lang3.Validate;

public class bij extends blk {
	public bij(bvr bvr1, bvr bvr2, bke.a a) {
		super(bvr1, bvr2, a);
		Validate.isInstanceOf(bup.class, bvr1);
		Validate.isInstanceOf(bup.class, bvr2);
	}

	public bje b() {
		return ((bup)this.e()).b();
	}
}
