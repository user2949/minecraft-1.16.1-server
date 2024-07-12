package org.apache.logging.log4j.message;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.util.StringBuilderFormattable;

@AsynchronouslyFormattable
public class ThreadDumpMessage implements Message, StringBuilderFormattable {
	private static final long serialVersionUID = -1103400781608841088L;
	private static final ThreadDumpMessage.ThreadInfoFactory FACTORY;
	private volatile Map<ThreadInformation, StackTraceElement[]> threads;
	private final String title;
	private String formattedMessage;

	public ThreadDumpMessage(String title) {
		this.title = title == null ? "" : title;
		this.threads = FACTORY.createThreadInfo();
	}

	private ThreadDumpMessage(String formattedMsg, String title) {
		this.formattedMessage = formattedMsg;
		this.title = title == null ? "" : title;
	}

	public String toString() {
		return this.getFormattedMessage();
	}

	@Override
	public String getFormattedMessage() {
		if (this.formattedMessage != null) {
			return this.formattedMessage;
		} else {
			StringBuilder sb = new StringBuilder(255);
			this.formatTo(sb);
			return sb.toString();
		}
	}

	@Override
	public void formatTo(StringBuilder sb) {
		sb.append(this.title);
		if (this.title.length() > 0) {
			sb.append('\n');
		}

		for (Entry<ThreadInformation, StackTraceElement[]> entry : this.threads.entrySet()) {
			ThreadInformation info = (ThreadInformation)entry.getKey();
			info.printThreadInfo(sb);
			info.printStack(sb, (StackTraceElement[])entry.getValue());
			sb.append('\n');
		}
	}

	@Override
	public String getFormat() {
		return this.title == null ? "" : this.title;
	}

	@Override
	public Object[] getParameters() {
		return null;
	}

	protected Object writeReplace() {
		return new ThreadDumpMessage.ThreadDumpMessageProxy(this);
	}

	private void readObject(ObjectInputStream stream) throws InvalidObjectException {
		throw new InvalidObjectException("Proxy required");
	}

	@Override
	public Throwable getThrowable() {
		return null;
	}

	static {
		Method[] methods = ThreadInfo.class.getMethods();
		boolean basic = true;

		for (Method method : methods) {
			if (method.getName().equals("getLockInfo")) {
				basic = false;
				break;
			}
		}

		FACTORY = (ThreadDumpMessage.ThreadInfoFactory)(basic ? new ThreadDumpMessage.BasicThreadInfoFactory() : new ThreadDumpMessage.ExtendedThreadInfoFactory());
	}

	private static class BasicThreadInfoFactory implements ThreadDumpMessage.ThreadInfoFactory {
		private BasicThreadInfoFactory() {
		}

		@Override
		public Map<ThreadInformation, StackTraceElement[]> createThreadInfo() {
			Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
			Map<ThreadInformation, StackTraceElement[]> threads = new HashMap(map.size());

			for (Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
				threads.put(new BasicThreadInformation((Thread)entry.getKey()), entry.getValue());
			}

			return threads;
		}
	}

	private static class ExtendedThreadInfoFactory implements ThreadDumpMessage.ThreadInfoFactory {
		private ExtendedThreadInfoFactory() {
		}

		@Override
		public Map<ThreadInformation, StackTraceElement[]> createThreadInfo() {
			ThreadMXBean bean = ManagementFactory.getThreadMXBean();
			ThreadInfo[] array = bean.dumpAllThreads(true, true);
			Map<ThreadInformation, StackTraceElement[]> threads = new HashMap(array.length);

			for (ThreadInfo info : array) {
				threads.put(new ExtendedThreadInformation(info), info.getStackTrace());
			}

			return threads;
		}
	}

	private static class ThreadDumpMessageProxy implements Serializable {
		private static final long serialVersionUID = -3476620450287648269L;
		private final String formattedMsg;
		private final String title;

		ThreadDumpMessageProxy(ThreadDumpMessage msg) {
			this.formattedMsg = msg.getFormattedMessage();
			this.title = msg.title;
		}

		protected Object readResolve() {
			return new ThreadDumpMessage(this.formattedMsg, this.title);
		}
	}

	private interface ThreadInfoFactory {
		Map<ThreadInformation, StackTraceElement[]> createThreadInfo();
	}
}
