package org.apache.logging.log4j.core.jmx;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import javax.management.ObjectName;

public interface LoggerContextAdminMBean {
	String PATTERN = "org.apache.logging.log4j2:type=%s";
	String NOTIF_TYPE_RECONFIGURED = "com.apache.logging.log4j.core.jmx.config.reconfigured";

	ObjectName getObjectName();

	String getStatus();

	String getName();

	String getConfigLocationUri();

	void setConfigLocationUri(String string) throws URISyntaxException, IOException;

	String getConfigText() throws IOException;

	String getConfigText(String string) throws IOException;

	void setConfigText(String string1, String string2);

	String getConfigName();

	String getConfigClassName();

	String getConfigFilter();

	Map<String, String> getConfigProperties();
}
