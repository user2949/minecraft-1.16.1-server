package com.mojang.authlib.minecraft;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.net.InetAddress;
import java.util.Map;

public interface MinecraftSessionService {
	void joinServer(GameProfile gameProfile, String string2, String string3) throws AuthenticationException;

	GameProfile hasJoinedServer(GameProfile gameProfile, String string, InetAddress inetAddress) throws AuthenticationUnavailableException;

	Map<Type, MinecraftProfileTexture> getTextures(GameProfile gameProfile, boolean boolean2);

	GameProfile fillProfileProperties(GameProfile gameProfile, boolean boolean2);
}
