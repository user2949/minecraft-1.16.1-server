package org.apache.commons.lang3.time;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.commons.lang3.exception.ExceptionUtils;

class CalendarReflection {
	private static final Method IS_WEEK_DATE_SUPPORTED = getCalendarMethod("isWeekDateSupported");
	private static final Method GET_WEEK_YEAR = getCalendarMethod("getWeekYear");

	private static Method getCalendarMethod(String methodName, Class<?>... argTypes) {
		try {
			return Calendar.class.getMethod(methodName, argTypes);
		} catch (Exception var3) {
			return null;
		}
	}

	static boolean isWeekDateSupported(Calendar calendar) {
		try {
			return IS_WEEK_DATE_SUPPORTED != null && (Boolean)IS_WEEK_DATE_SUPPORTED.invoke(calendar);
		} catch (Exception var2) {
			return ExceptionUtils.<Boolean>rethrow(var2);
		}
	}

	public static int getWeekYear(Calendar calendar) {
		try {
			if (isWeekDateSupported(calendar)) {
				return (Integer)GET_WEEK_YEAR.invoke(calendar);
			}
		} catch (Exception var2) {
			return ExceptionUtils.<Integer>rethrow(var2);
		}

		int year = calendar.get(1);
		if (IS_WEEK_DATE_SUPPORTED == null && calendar instanceof GregorianCalendar) {
			switch (calendar.get(2)) {
				case 0:
					if (calendar.get(3) >= 52) {
						year--;
					}
					break;
				case 11:
					if (calendar.get(3) == 1) {
						year++;
					}
			}
		}

		return year;
	}
}
