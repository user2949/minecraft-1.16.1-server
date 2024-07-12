package com.mojang.authlib;

public interface ProfileLookupCallback {
	void onProfileLookupSucceeded(GameProfile gameProfile);

	void onProfileLookupFailed(GameProfile gameProfile, Exception exception);
}
