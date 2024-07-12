package org.apache.logging.log4j.util;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;

public final class LambdaUtil {
	private LambdaUtil() {
	}

	public static Object[] getAll(Supplier<?>... suppliers) {
		if (suppliers == null) {
			return null;
		} else {
			Object[] result = new Object[suppliers.length];

			for (int i = 0; i < result.length; i++) {
				result[i] = get(suppliers[i]);
			}

			return result;
		}
	}

	public static Object get(Supplier<?> supplier) {
		if (supplier == null) {
			return null;
		} else {
			Object result = supplier.get();
			return result instanceof Message ? ((Message)result).getFormattedMessage() : result;
		}
	}

	public static Message get(MessageSupplier supplier) {
		return supplier == null ? null : supplier.get();
	}

	public static Message getMessage(Supplier<?> supplier, MessageFactory messageFactory) {
		if (supplier == null) {
			return null;
		} else {
			Object result = supplier.get();
			return result instanceof Message ? (Message)result : messageFactory.newMessage(result);
		}
	}
}