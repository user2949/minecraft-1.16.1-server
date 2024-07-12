package org.apache.logging.log4j.message;

import java.io.Serializable;
import org.apache.logging.log4j.util.PerformanceSensitive;

@PerformanceSensitive({"allocation"})
public final class ReusableMessageFactory implements MessageFactory2, Serializable {
	public static final ReusableMessageFactory INSTANCE = new ReusableMessageFactory();
	private static final long serialVersionUID = -8970940216592525651L;
	private static ThreadLocal<ReusableParameterizedMessage> threadLocalParameterized = new ThreadLocal();
	private static ThreadLocal<ReusableSimpleMessage> threadLocalSimpleMessage = new ThreadLocal();
	private static ThreadLocal<ReusableObjectMessage> threadLocalObjectMessage = new ThreadLocal();

	private static ReusableParameterizedMessage getParameterized() {
		ReusableParameterizedMessage result = (ReusableParameterizedMessage)threadLocalParameterized.get();
		if (result == null) {
			result = new ReusableParameterizedMessage();
			threadLocalParameterized.set(result);
		}

		return result.reserved ? new ReusableParameterizedMessage().reserve() : result.reserve();
	}

	private static ReusableSimpleMessage getSimple() {
		ReusableSimpleMessage result = (ReusableSimpleMessage)threadLocalSimpleMessage.get();
		if (result == null) {
			result = new ReusableSimpleMessage();
			threadLocalSimpleMessage.set(result);
		}

		return result;
	}

	private static ReusableObjectMessage getObject() {
		ReusableObjectMessage result = (ReusableObjectMessage)threadLocalObjectMessage.get();
		if (result == null) {
			result = new ReusableObjectMessage();
			threadLocalObjectMessage.set(result);
		}

		return result;
	}

	public static void release(Message message) {
		if (message instanceof ReusableParameterizedMessage) {
			((ReusableParameterizedMessage)message).reserved = false;
		}
	}

	@Override
	public Message newMessage(CharSequence charSequence) {
		ReusableSimpleMessage result = getSimple();
		result.set(charSequence);
		return result;
	}

	@Override
	public Message newMessage(String message, Object... params) {
		return getParameterized().set(message, params);
	}

	@Override
	public Message newMessage(String message, Object p0) {
		return getParameterized().set(message, p0);
	}

	@Override
	public Message newMessage(String message, Object p0, Object p1) {
		return getParameterized().set(message, p0, p1);
	}

	@Override
	public Message newMessage(String message, Object p0, Object p1, Object p2) {
		return getParameterized().set(message, p0, p1, p2);
	}

	@Override
	public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3) {
		return getParameterized().set(message, p0, p1, p2, p3);
	}

	@Override
	public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
		return getParameterized().set(message, p0, p1, p2, p3, p4);
	}

	@Override
	public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		return getParameterized().set(message, p0, p1, p2, p3, p4, p5);
	}

	@Override
	public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		return getParameterized().set(message, p0, p1, p2, p3, p4, p5, p6);
	}

	@Override
	public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
		return getParameterized().set(message, p0, p1, p2, p3, p4, p5, p6, p7);
	}

	@Override
	public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
		return getParameterized().set(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
	}

	@Override
	public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
		return getParameterized().set(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
	}

	@Override
	public Message newMessage(String message) {
		ReusableSimpleMessage result = getSimple();
		result.set(message);
		return result;
	}

	@Override
	public Message newMessage(Object message) {
		ReusableObjectMessage result = getObject();
		result.set(message);
		return result;
	}
}
