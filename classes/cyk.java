public enum cyk implements cyt {
	INSTANCE;

	@Override
	public int a(cxn cxn, int integer2, int integer3, int integer4, int integer5, int integer6) {
		boolean boolean8 = integer3 == integer5;
		boolean boolean9 = integer2 == integer4;
		if (boolean8 == boolean9) {
			if (boolean8) {
				return cxn.a(2) == 0 ? integer5 : integer2;
			} else {
				return integer6;
			}
		} else {
			return boolean8 ? integer5 : integer2;
		}
	}
}
