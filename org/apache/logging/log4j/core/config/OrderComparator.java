package org.apache.logging.log4j.core.config;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class OrderComparator implements Comparator<Class<?>>, Serializable {
	private static final long serialVersionUID = 1L;
	private static final Comparator<Class<?>> INSTANCE = new OrderComparator();

	public static Comparator<Class<?>> getInstance() {
		return INSTANCE;
	}

	public int compare(Class<?> lhs, Class<?> rhs) {
		Order lhsOrder = (Order)((Class)Objects.requireNonNull(lhs, "lhs")).getAnnotation(Order.class);
		Order rhsOrder = (Order)((Class)Objects.requireNonNull(rhs, "rhs")).getAnnotation(Order.class);
		if (lhsOrder == null && rhsOrder == null) {
			return 0;
		} else if (rhsOrder == null) {
			return -1;
		} else {
			return lhsOrder == null ? 1 : Integer.signum(rhsOrder.value() - lhsOrder.value());
		}
	}
}