package com.mojang.bridge;

import com.mojang.bridge.launcher.Launcher;
import com.mojang.bridge.launcher.LauncherProvider;
import java.util.ServiceLoader;

public class Bridge {
	private static boolean INITIALIZED;
	private static Launcher LAUNCHER;

	private Bridge() {
	}

	public static Launcher getLauncher() {
		if (!INITIALIZED) {
			synchronized (Bridge.class) {
				if (!INITIALIZED) {
					LAUNCHER = createLauncher();
					INITIALIZED = true;
				}
			}
		}

		return LAUNCHER;
	}

	private static Launcher createLauncher() {
		for (LauncherProvider provider : ServiceLoader.load(LauncherProvider.class)) {
			Launcher launcher = provider.createLauncher();
			if (launcher != null) {
				return launcher;
			}
		}

		return null;
	}
}
