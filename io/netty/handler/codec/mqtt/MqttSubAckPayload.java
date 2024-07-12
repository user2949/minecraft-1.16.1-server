package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MqttSubAckPayload {
	private final List<Integer> grantedQoSLevels;

	public MqttSubAckPayload(int... grantedQoSLevels) {
		if (grantedQoSLevels == null) {
			throw new NullPointerException("grantedQoSLevels");
		} else {
			List<Integer> list = new ArrayList(grantedQoSLevels.length);

			for (int v : grantedQoSLevels) {
				list.add(v);
			}

			this.grantedQoSLevels = Collections.unmodifiableList(list);
		}
	}

	public MqttSubAckPayload(Iterable<Integer> grantedQoSLevels) {
		if (grantedQoSLevels == null) {
			throw new NullPointerException("grantedQoSLevels");
		} else {
			List<Integer> list = new ArrayList();

			for (Integer v : grantedQoSLevels) {
				if (v == null) {
					break;
				}

				list.add(v);
			}

			this.grantedQoSLevels = Collections.unmodifiableList(list);
		}
	}

	public List<Integer> grantedQoSLevels() {
		return this.grantedQoSLevels;
	}

	public String toString() {
		return StringUtil.simpleClassName(this) + '[' + "grantedQoSLevels=" + this.grantedQoSLevels + ']';
	}
}
