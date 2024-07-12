package org.apache.logging.log4j.message;

public interface FlowMessageFactory {
	EntryMessage newEntryMessage(Message message);

	ExitMessage newExitMessage(Object object, Message message);

	ExitMessage newExitMessage(EntryMessage entryMessage);

	ExitMessage newExitMessage(Object object, EntryMessage entryMessage);
}
