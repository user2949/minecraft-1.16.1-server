package com.mojang.authlib;

public interface GameProfileRepository {
	void findProfilesByNames(String[] arr, Agent agent, ProfileLookupCallback profileLookupCallback);
}
