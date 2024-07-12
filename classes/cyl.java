public enum cyl implements cyo {
	NORMAL,
	FUZZY {
		@Override
		protected int a(cxm<?> cxm, int integer2, int integer3, int integer4, int integer5) {
			return cxm.a(integer2, integer3, integer4, integer5);
		}
	};

	private cyl() {
	}

	@Override
	public int a(int integer) {
		return integer >> 1;
	}

	@Override
	public int b(int integer) {
		return integer >> 1;
	}

	@Override
	public int a(cxm<?> cxm, cxi cxi, int integer3, int integer4) {
		int integer6 = cxi.a(this.a(integer3), this.b(integer4));
		cxm.a((long)(integer3 >> 1 << 1), (long)(integer4 >> 1 << 1));
		int integer7 = integer3 & 1;
		int integer8 = integer4 & 1;
		if (integer7 == 0 && integer8 == 0) {
			return integer6;
		} else {
			int integer9 = cxi.a(this.a(integer3), this.b(integer4 + 1));
			int integer10 = cxm.a(integer6, integer9);
			if (integer7 == 0 && integer8 == 1) {
				return integer10;
			} else {
				int integer11 = cxi.a(this.a(integer3 + 1), this.b(integer4));
				int integer12 = cxm.a(integer6, integer11);
				if (integer7 == 1 && integer8 == 0) {
					return integer12;
				} else {
					int integer13 = cxi.a(this.a(integer3 + 1), this.b(integer4 + 1));
					return this.a(cxm, integer6, integer11, integer9, integer13);
				}
			}
		}
	}

	protected int a(cxm<?> cxm, int integer2, int integer3, int integer4, int integer5) {
		if (integer3 == integer4 && integer4 == integer5) {
			return integer3;
		} else if (integer2 == integer3 && integer2 == integer4) {
			return integer2;
		} else if (integer2 == integer3 && integer2 == integer5) {
			return integer2;
		} else if (integer2 == integer4 && integer2 == integer5) {
			return integer2;
		} else if (integer2 == integer3 && integer4 != integer5) {
			return integer2;
		} else if (integer2 == integer4 && integer3 != integer5) {
			return integer2;
		} else if (integer2 == integer5 && integer3 != integer4) {
			return integer2;
		} else if (integer3 == integer4 && integer2 != integer5) {
			return integer3;
		} else if (integer3 == integer5 && integer2 != integer4) {
			return integer3;
		} else {
			return integer4 == integer5 && integer2 != integer3 ? integer4 : cxm.a(integer2, integer3, integer4, integer5);
		}
	}
}
