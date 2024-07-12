package net.minecraft.util;

public class CubicSampler {
	private static final double[] a = new double[]{0.0, 1.0, 4.0, 6.0, 4.0, 1.0, 0.0};

	public interface Vec3Fetcher {
		dem fetch(int integer1, int integer2, int integer3);
	}
}
