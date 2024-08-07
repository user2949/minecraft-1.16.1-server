package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

final class MacHashFunction extends AbstractStreamingHashFunction {
	private final Mac prototype;
	private final Key key;
	private final String toString;
	private final int bits;
	private final boolean supportsClone;

	MacHashFunction(String algorithmName, Key key, String toString) {
		this.prototype = getMac(algorithmName, key);
		this.key = Preconditions.checkNotNull(key);
		this.toString = Preconditions.checkNotNull(toString);
		this.bits = this.prototype.getMacLength() * 8;
		this.supportsClone = supportsClone(this.prototype);
	}

	@Override
	public int bits() {
		return this.bits;
	}

	private static boolean supportsClone(Mac mac) {
		try {
			mac.clone();
			return true;
		} catch (CloneNotSupportedException var2) {
			return false;
		}
	}

	private static Mac getMac(String algorithmName, Key key) {
		try {
			Mac mac = Mac.getInstance(algorithmName);
			mac.init(key);
			return mac;
		} catch (NoSuchAlgorithmException var3) {
			throw new IllegalStateException(var3);
		} catch (InvalidKeyException var4) {
			throw new IllegalArgumentException(var4);
		}
	}

	@Override
	public Hasher newHasher() {
		if (this.supportsClone) {
			try {
				return new MacHashFunction.MacHasher((Mac)this.prototype.clone());
			} catch (CloneNotSupportedException var2) {
			}
		}

		return new MacHashFunction.MacHasher(getMac(this.prototype.getAlgorithm(), this.key));
	}

	public String toString() {
		return this.toString;
	}

	private static final class MacHasher extends AbstractByteHasher {
		private final Mac mac;
		private boolean done;

		private MacHasher(Mac mac) {
			this.mac = mac;
		}

		@Override
		protected void update(byte b) {
			this.checkNotDone();
			this.mac.update(b);
		}

		@Override
		protected void update(byte[] b) {
			this.checkNotDone();
			this.mac.update(b);
		}

		@Override
		protected void update(byte[] b, int off, int len) {
			this.checkNotDone();
			this.mac.update(b, off, len);
		}

		private void checkNotDone() {
			Preconditions.checkState(!this.done, "Cannot re-use a Hasher after calling hash() on it");
		}

		@Override
		public HashCode hash() {
			this.checkNotDone();
			this.done = true;
			return HashCode.fromBytesNoCopy(this.mac.doFinal());
		}
	}
}
