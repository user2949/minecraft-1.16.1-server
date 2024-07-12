package org.apache.logging.log4j.message;

import java.io.Serializable;

public class DefaultFlowMessageFactory implements FlowMessageFactory, Serializable {
	private static final String EXIT_DEFAULT_PREFIX = "Exit";
	private static final String ENTRY_DEFAULT_PREFIX = "Enter";
	private static final long serialVersionUID = 8578655591131397576L;
	private final String entryText;
	private final String exitText;

	public DefaultFlowMessageFactory() {
		this("Enter", "Exit");
	}

	public DefaultFlowMessageFactory(String entryText, String exitText) {
		this.entryText = entryText;
		this.exitText = exitText;
	}

	public String getEntryText() {
		return this.entryText;
	}

	public String getExitText() {
		return this.exitText;
	}

	@Override
	public EntryMessage newEntryMessage(Message message) {
		return new DefaultFlowMessageFactory.SimpleEntryMessage(this.entryText, this.makeImmutable(message));
	}

	private Message makeImmutable(Message message) {
		return (Message)(!(message instanceof ReusableMessage) ? message : new SimpleMessage(message.getFormattedMessage()));
	}

	@Override
	public ExitMessage newExitMessage(EntryMessage message) {
		return new DefaultFlowMessageFactory.SimpleExitMessage(this.exitText, message);
	}

	@Override
	public ExitMessage newExitMessage(Object result, EntryMessage message) {
		return new DefaultFlowMessageFactory.SimpleExitMessage(this.exitText, result, message);
	}

	@Override
	public ExitMessage newExitMessage(Object result, Message message) {
		return new DefaultFlowMessageFactory.SimpleExitMessage(this.exitText, result, message);
	}

	private static class AbstractFlowMessage implements FlowMessage {
		private static final long serialVersionUID = 1L;
		private final Message message;
		private final String text;

		AbstractFlowMessage(String text, Message message) {
			this.message = message;
			this.text = text;
		}

		@Override
		public String getFormattedMessage() {
			return this.message != null ? this.text + " " + this.message.getFormattedMessage() : this.text;
		}

		@Override
		public String getFormat() {
			return this.message != null ? this.text + ": " + this.message.getFormat() : this.text;
		}

		@Override
		public Object[] getParameters() {
			return this.message != null ? this.message.getParameters() : null;
		}

		@Override
		public Throwable getThrowable() {
			return this.message != null ? this.message.getThrowable() : null;
		}

		@Override
		public Message getMessage() {
			return this.message;
		}

		@Override
		public String getText() {
			return this.text;
		}
	}

	private static final class SimpleEntryMessage extends DefaultFlowMessageFactory.AbstractFlowMessage implements EntryMessage {
		private static final long serialVersionUID = 1L;

		SimpleEntryMessage(String entryText, Message message) {
			super(entryText, message);
		}
	}

	private static final class SimpleExitMessage extends DefaultFlowMessageFactory.AbstractFlowMessage implements ExitMessage {
		private static final long serialVersionUID = 1L;
		private final Object result;
		private final boolean isVoid;

		SimpleExitMessage(String exitText, EntryMessage message) {
			super(exitText, message.getMessage());
			this.result = null;
			this.isVoid = true;
		}

		SimpleExitMessage(String exitText, Object result, EntryMessage message) {
			super(exitText, message.getMessage());
			this.result = result;
			this.isVoid = false;
		}

		SimpleExitMessage(String exitText, Object result, Message message) {
			super(exitText, message);
			this.result = result;
			this.isVoid = false;
		}

		@Override
		public String getFormattedMessage() {
			String formattedMessage = super.getFormattedMessage();
			return this.isVoid ? formattedMessage : formattedMessage + ": " + this.result;
		}
	}
}
