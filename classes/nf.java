public class nf extends IllegalArgumentException {
	public nf(ne ne, String string) {
		super(String.format("Error parsing: %s: %s", ne, string));
	}

	public nf(ne ne, int integer) {
		super(String.format("Invalid index %d requested for %s", integer, ne));
	}

	public nf(ne ne, Throwable throwable) {
		super(String.format("Error while parsing: %s", ne), throwable);
	}
}
