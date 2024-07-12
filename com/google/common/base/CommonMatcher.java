package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class CommonMatcher {
	abstract boolean matches();

	abstract boolean find();

	abstract boolean find(int integer);

	abstract String replaceAll(String string);

	abstract int end();

	abstract int start();
}
