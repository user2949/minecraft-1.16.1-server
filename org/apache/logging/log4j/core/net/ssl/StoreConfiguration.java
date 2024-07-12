package org.apache.logging.log4j.core.net.ssl;

import org.apache.logging.log4j.status.StatusLogger;

public class StoreConfiguration<T> {
	protected static final StatusLogger LOGGER = StatusLogger.getLogger();
	private String location;
	private String password;

	public StoreConfiguration(String location, String password) {
		this.location = location;
		this.password = password;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPassword() {
		return this.password;
	}

	public char[] getPasswordAsCharArray() {
		return this.password == null ? null : this.password.toCharArray();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	protected T load() throws StoreConfigurationException {
		return null;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + (this.location == null ? 0 : this.location.hashCode());
		return 31 * result + (this.password == null ? 0 : this.password.hashCode());
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (!(obj instanceof StoreConfiguration)) {
			return false;
		} else {
			StoreConfiguration<?> other = (StoreConfiguration<?>)obj;
			if (this.location == null) {
				if (other.location != null) {
					return false;
				}
			} else if (!this.location.equals(other.location)) {
				return false;
			}

			if (this.password == null) {
				if (other.password != null) {
					return false;
				}
			} else if (!this.password.equals(other.password)) {
				return false;
			}

			return true;
		}
	}
}
