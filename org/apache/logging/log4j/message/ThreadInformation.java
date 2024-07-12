package org.apache.logging.log4j.message;

interface ThreadInformation {
	void printThreadInfo(StringBuilder stringBuilder);

	void printStack(StringBuilder stringBuilder, StackTraceElement[] arr);
}
