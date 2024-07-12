public class dbs extends dbh {
	dbs(dbo[] arr, ddm[] arr) {
		super(arr, arr);
	}

	@Override
	public dbp a() {
		return dbm.g;
	}

	@Override
	protected dbg a(dbg[] arr) {
		switch (arr.length) {
			case 0:
				return b;
			case 1:
				return arr[0];
			case 2:
				return arr[0].a(arr[1]);
			default:
				return (dat, consumer) -> {
					for (dbg dbg7 : arr) {
						if (!dbg7.expand(dat, consumer)) {
							return false;
						}
					}

					return true;
				};
		}
	}
}
