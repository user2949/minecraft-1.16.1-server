package io.netty.handler.codec.spdy;

import java.util.Set;

public interface SpdySettingsFrame extends SpdyFrame {
	int SETTINGS_MINOR_VERSION = 0;
	int SETTINGS_UPLOAD_BANDWIDTH = 1;
	int SETTINGS_DOWNLOAD_BANDWIDTH = 2;
	int SETTINGS_ROUND_TRIP_TIME = 3;
	int SETTINGS_MAX_CONCURRENT_STREAMS = 4;
	int SETTINGS_CURRENT_CWND = 5;
	int SETTINGS_DOWNLOAD_RETRANS_RATE = 6;
	int SETTINGS_INITIAL_WINDOW_SIZE = 7;
	int SETTINGS_CLIENT_CERTIFICATE_VECTOR_SIZE = 8;

	Set<Integer> ids();

	boolean isSet(int integer);

	int getValue(int integer);

	SpdySettingsFrame setValue(int integer1, int integer2);

	SpdySettingsFrame setValue(int integer1, int integer2, boolean boolean3, boolean boolean4);

	SpdySettingsFrame removeValue(int integer);

	boolean isPersistValue(int integer);

	SpdySettingsFrame setPersistValue(int integer, boolean boolean2);

	boolean isPersisted(int integer);

	SpdySettingsFrame setPersisted(int integer, boolean boolean2);

	boolean clearPreviouslyPersistedSettings();

	SpdySettingsFrame setClearPreviouslyPersistedSettings(boolean boolean1);
}
