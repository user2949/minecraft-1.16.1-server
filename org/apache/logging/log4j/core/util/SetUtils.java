package org.apache.logging.log4j.core.util;

import java.util.HashSet;
import java.util.Set;

public final class SetUtils {
	private SetUtils() {
	}

	public static String[] prefixSet(Set<String> set, String prefix) {
		Set<String> prefixSet = new HashSet();

		for (String str : set) {
			if (str.startsWith(prefix)) {
				prefixSet.add(str);
			}
		}

		return (String[])prefixSet.toArray(new String[prefixSet.size()]);
	}
}
