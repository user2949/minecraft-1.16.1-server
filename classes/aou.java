public enum aou {
	LEFT(new ne("options.mainHand.left")),
	RIGHT(new ne("options.mainHand.right"));

	private final mr c;

	private aou(mr mr) {
		this.c = mr;
	}

	public String toString() {
		return this.c.getString();
	}
}
