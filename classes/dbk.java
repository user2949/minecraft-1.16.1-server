public class dbk extends dbh {
	dbk(dbo[] arr, ddm[] arr) {
		super(arr, arr);
	}

	@Override
	public dbp a() {
		return dbm.h;
	}

	@Override
	protected dbg a(dbg[] arr) {
		switch (arr.length) {
			case 0:
				return b;
			case 1:
				return arr[0];
			case 2:
				dbg dbg3 = arr[0];
				dbg dbg4 = arr[1];
				return (dat, consumer) -> {
					dbg3.expand(dat, consumer);
					dbg4.expand(dat, consumer);
					return true;
				};
			default:
				return (dat, consumer) -> {
					for (dbg dbg7 : arr) {
						dbg7.expand(dat, consumer);
					}

					return true;
				};
		}
	}
}
