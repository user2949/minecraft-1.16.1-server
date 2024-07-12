public interface aas {
	aas a = a();
	aas b = a("pack.source.builtin");
	aas c = a("pack.source.world");
	aas d = a("pack.source.server");

	mr decorate(mr mr);

	static aas a() {
		return mr -> mr;
	}

	static aas a(String string) {
		mr mr2 = new ne(string);
		return mr2x -> new ne("pack.nameAndSource", mr2x, mr2);
	}
}
