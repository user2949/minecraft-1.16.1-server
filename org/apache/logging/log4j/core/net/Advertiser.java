package org.apache.logging.log4j.core.net;

import java.util.Map;

public interface Advertiser {
	Object advertise(Map<String, String> map);

	void unadvertise(Object object);
}
