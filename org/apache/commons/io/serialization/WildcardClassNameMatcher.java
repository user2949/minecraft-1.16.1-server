package org.apache.commons.io.serialization;

import org.apache.commons.io.FilenameUtils;

final class WildcardClassNameMatcher implements ClassNameMatcher {
	private final String pattern;

	public WildcardClassNameMatcher(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public boolean matches(String className) {
		return FilenameUtils.wildcardMatch(className, this.pattern);
	}
}
