import java.util.Collection;

public interface ah {
	ah a = collection -> {
		String[][] arr2 = new String[collection.size()][];
		int integer3 = 0;

		for (String string5 : collection) {
			arr2[integer3++] = new String[]{string5};
		}

		return arr2;
	};
	ah b = collection -> new String[][]{(String[])collection.toArray(new String[0])};

	String[][] createRequirements(Collection<String> collection);
}
