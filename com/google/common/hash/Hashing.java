package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.annotation.Nullable;
import javax.crypto.spec.SecretKeySpec;

@Beta
public final class Hashing {
	private static final int GOOD_FAST_HASH_SEED = (int)System.currentTimeMillis();

	public static HashFunction goodFastHash(int minimumBits) {
		int bits = checkPositiveAndMakeMultipleOf32(minimumBits);
		if (bits == 32) {
			return Hashing.Murmur3_32Holder.GOOD_FAST_HASH_FUNCTION_32;
		} else if (bits <= 128) {
			return Hashing.Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
		} else {
			int hashFunctionsNeeded = (bits + 127) / 128;
			HashFunction[] hashFunctions = new HashFunction[hashFunctionsNeeded];
			hashFunctions[0] = Hashing.Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
			int seed = GOOD_FAST_HASH_SEED;

			for (int i = 1; i < hashFunctionsNeeded; i++) {
				seed += 1500450271;
				hashFunctions[i] = murmur3_128(seed);
			}

			return new Hashing.ConcatenatedHashFunction(hashFunctions);
		}
	}

	public static HashFunction murmur3_32(int seed) {
		return new Murmur3_32HashFunction(seed);
	}

	public static HashFunction murmur3_32() {
		return Hashing.Murmur3_32Holder.MURMUR3_32;
	}

	public static HashFunction murmur3_128(int seed) {
		return new Murmur3_128HashFunction(seed);
	}

	public static HashFunction murmur3_128() {
		return Hashing.Murmur3_128Holder.MURMUR3_128;
	}

	public static HashFunction sipHash24() {
		return Hashing.SipHash24Holder.SIP_HASH_24;
	}

	public static HashFunction sipHash24(long k0, long k1) {
		return new SipHashFunction(2, 4, k0, k1);
	}

	public static HashFunction md5() {
		return Hashing.Md5Holder.MD5;
	}

	public static HashFunction sha1() {
		return Hashing.Sha1Holder.SHA_1;
	}

	public static HashFunction sha256() {
		return Hashing.Sha256Holder.SHA_256;
	}

	public static HashFunction sha384() {
		return Hashing.Sha384Holder.SHA_384;
	}

	public static HashFunction sha512() {
		return Hashing.Sha512Holder.SHA_512;
	}

	public static HashFunction hmacMd5(Key key) {
		return new MacHashFunction("HmacMD5", key, hmacToString("hmacMd5", key));
	}

	public static HashFunction hmacMd5(byte[] key) {
		return hmacMd5(new SecretKeySpec(Preconditions.checkNotNull(key), "HmacMD5"));
	}

	public static HashFunction hmacSha1(Key key) {
		return new MacHashFunction("HmacSHA1", key, hmacToString("hmacSha1", key));
	}

	public static HashFunction hmacSha1(byte[] key) {
		return hmacSha1(new SecretKeySpec(Preconditions.checkNotNull(key), "HmacSHA1"));
	}

	public static HashFunction hmacSha256(Key key) {
		return new MacHashFunction("HmacSHA256", key, hmacToString("hmacSha256", key));
	}

	public static HashFunction hmacSha256(byte[] key) {
		return hmacSha256(new SecretKeySpec(Preconditions.checkNotNull(key), "HmacSHA256"));
	}

	public static HashFunction hmacSha512(Key key) {
		return new MacHashFunction("HmacSHA512", key, hmacToString("hmacSha512", key));
	}

	public static HashFunction hmacSha512(byte[] key) {
		return hmacSha512(new SecretKeySpec(Preconditions.checkNotNull(key), "HmacSHA512"));
	}

	private static String hmacToString(String methodName, Key key) {
		return String.format("Hashing.%s(Key[algorithm=%s, format=%s])", methodName, key.getAlgorithm(), key.getFormat());
	}

	public static HashFunction crc32c() {
		return Hashing.Crc32cHolder.CRC_32_C;
	}

	public static HashFunction crc32() {
		return Hashing.Crc32Holder.CRC_32;
	}

	public static HashFunction adler32() {
		return Hashing.Adler32Holder.ADLER_32;
	}

	private static HashFunction checksumHashFunction(Hashing.ChecksumType type, String toString) {
		return new ChecksumHashFunction(type, type.bits, toString);
	}

	public static HashFunction farmHashFingerprint64() {
		return Hashing.FarmHashFingerprint64Holder.FARMHASH_FINGERPRINT_64;
	}

	public static int consistentHash(HashCode hashCode, int buckets) {
		return consistentHash(hashCode.padToLong(), buckets);
	}

	public static int consistentHash(long input, int buckets) {
		Preconditions.checkArgument(buckets > 0, "buckets must be positive: %s", buckets);
		Hashing.LinearCongruentialGenerator generator = new Hashing.LinearCongruentialGenerator(input);
		int candidate = 0;

		while (true) {
			int next = (int)((double)(candidate + 1) / generator.nextDouble());
			if (next < 0 || next >= buckets) {
				return candidate;
			}

			candidate = next;
		}
	}

	public static HashCode combineOrdered(Iterable<HashCode> hashCodes) {
		Iterator<HashCode> iterator = hashCodes.iterator();
		Preconditions.checkArgument(iterator.hasNext(), "Must be at least 1 hash code to combine.");
		int bits = ((HashCode)iterator.next()).bits();
		byte[] resultBytes = new byte[bits / 8];

		for (HashCode hashCode : hashCodes) {
			byte[] nextBytes = hashCode.asBytes();
			Preconditions.checkArgument(nextBytes.length == resultBytes.length, "All hashcodes must have the same bit length.");

			for (int i = 0; i < nextBytes.length; i++) {
				resultBytes[i] = (byte)(resultBytes[i] * 37 ^ nextBytes[i]);
			}
		}

		return HashCode.fromBytesNoCopy(resultBytes);
	}

	public static HashCode combineUnordered(Iterable<HashCode> hashCodes) {
		Iterator<HashCode> iterator = hashCodes.iterator();
		Preconditions.checkArgument(iterator.hasNext(), "Must be at least 1 hash code to combine.");
		byte[] resultBytes = new byte[((HashCode)iterator.next()).bits() / 8];

		for (HashCode hashCode : hashCodes) {
			byte[] nextBytes = hashCode.asBytes();
			Preconditions.checkArgument(nextBytes.length == resultBytes.length, "All hashcodes must have the same bit length.");

			for (int i = 0; i < nextBytes.length; i++) {
				resultBytes[i] += nextBytes[i];
			}
		}

		return HashCode.fromBytesNoCopy(resultBytes);
	}

	static int checkPositiveAndMakeMultipleOf32(int bits) {
		Preconditions.checkArgument(bits > 0, "Number of bits must be positive");
		return bits + 31 & -32;
	}

	public static HashFunction concatenating(HashFunction first, HashFunction second, HashFunction... rest) {
		List<HashFunction> list = new ArrayList();
		list.add(first);
		list.add(second);

		for (HashFunction hashFunc : rest) {
			list.add(hashFunc);
		}

		return new Hashing.ConcatenatedHashFunction((HashFunction[])list.toArray(new HashFunction[0]));
	}

	public static HashFunction concatenating(Iterable<HashFunction> hashFunctions) {
		Preconditions.checkNotNull(hashFunctions);
		List<HashFunction> list = new ArrayList();

		for (HashFunction hashFunction : hashFunctions) {
			list.add(hashFunction);
		}

		Preconditions.checkArgument(list.size() > 0, "number of hash functions (%s) must be > 0", list.size());
		return new Hashing.ConcatenatedHashFunction((HashFunction[])list.toArray(new HashFunction[0]));
	}

	private Hashing() {
	}

	private static class Adler32Holder {
		static final HashFunction ADLER_32 = Hashing.checksumHashFunction(Hashing.ChecksumType.ADLER_32, "Hashing.adler32()");
	}

	static enum ChecksumType implements Supplier<Checksum> {
		CRC_32(32) {
			@Override
			public Checksum get() {
				return new CRC32();
			}
		},
		ADLER_32(32) {
			@Override
			public Checksum get() {
				return new Adler32();
			}
		};

		private final int bits;

		private ChecksumType(int bits) {
			this.bits = bits;
		}

		public abstract Checksum get();
	}

	private static final class ConcatenatedHashFunction extends AbstractCompositeHashFunction {
		private final int bits;

		private ConcatenatedHashFunction(HashFunction... functions) {
			super(functions);
			int bitSum = 0;

			for (HashFunction function : functions) {
				bitSum += function.bits();
				Preconditions.checkArgument(function.bits() % 8 == 0, "the number of bits (%s) in hashFunction (%s) must be divisible by 8", function.bits(), function);
			}

			this.bits = bitSum;
		}

		@Override
		HashCode makeHash(Hasher[] hashers) {
			byte[] bytes = new byte[this.bits / 8];
			int i = 0;

			for (Hasher hasher : hashers) {
				HashCode newHash = hasher.hash();
				i += newHash.writeBytesTo(bytes, i, newHash.bits() / 8);
			}

			return HashCode.fromBytesNoCopy(bytes);
		}

		@Override
		public int bits() {
			return this.bits;
		}

		public boolean equals(@Nullable Object object) {
			if (object instanceof Hashing.ConcatenatedHashFunction) {
				Hashing.ConcatenatedHashFunction other = (Hashing.ConcatenatedHashFunction)object;
				return Arrays.equals(this.functions, other.functions);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return Arrays.hashCode(this.functions) * 31 + this.bits;
		}
	}

	private static class Crc32Holder {
		static final HashFunction CRC_32 = Hashing.checksumHashFunction(Hashing.ChecksumType.CRC_32, "Hashing.crc32()");
	}

	private static final class Crc32cHolder {
		static final HashFunction CRC_32_C = new Crc32cHashFunction();
	}

	private static class FarmHashFingerprint64Holder {
		static final HashFunction FARMHASH_FINGERPRINT_64 = new FarmHashFingerprint64();
	}

	private static final class LinearCongruentialGenerator {
		private long state;

		public LinearCongruentialGenerator(long seed) {
			this.state = seed;
		}

		public double nextDouble() {
			this.state = 2862933555777941757L * this.state + 1L;
			return (double)((int)(this.state >>> 33) + 1) / 2.1474836E9F;
		}
	}

	private static class Md5Holder {
		static final HashFunction MD5 = new MessageDigestHashFunction("MD5", "Hashing.md5()");
	}

	private static class Murmur3_128Holder {
		static final HashFunction MURMUR3_128 = new Murmur3_128HashFunction(0);
		static final HashFunction GOOD_FAST_HASH_FUNCTION_128 = Hashing.murmur3_128(Hashing.GOOD_FAST_HASH_SEED);
	}

	private static class Murmur3_32Holder {
		static final HashFunction MURMUR3_32 = new Murmur3_32HashFunction(0);
		static final HashFunction GOOD_FAST_HASH_FUNCTION_32 = Hashing.murmur3_32(Hashing.GOOD_FAST_HASH_SEED);
	}

	private static class Sha1Holder {
		static final HashFunction SHA_1 = new MessageDigestHashFunction("SHA-1", "Hashing.sha1()");
	}

	private static class Sha256Holder {
		static final HashFunction SHA_256 = new MessageDigestHashFunction("SHA-256", "Hashing.sha256()");
	}

	private static class Sha384Holder {
		static final HashFunction SHA_384 = new MessageDigestHashFunction("SHA-384", "Hashing.sha384()");
	}

	private static class Sha512Holder {
		static final HashFunction SHA_512 = new MessageDigestHashFunction("SHA-512", "Hashing.sha512()");
	}

	private static class SipHash24Holder {
		static final HashFunction SIP_HASH_24 = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);
	}
}
