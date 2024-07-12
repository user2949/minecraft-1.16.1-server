package com.mojang.authlib;

import java.util.StringJoiner;

public interface Environment {
	String getAuthHost();

	String getAccountsHost();

	String getSessionHost();

	String getName();

	String asString();

	static Environment create(String auth, String account, String session, String name) {
		return new Environment() {
			@Override
			public String getAuthHost() {
				return auth;
			}

			@Override
			public String getAccountsHost() {
				return account;
			}

			@Override
			public String getSessionHost() {
				return session;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String asString() {
				return new StringJoiner(", ", "", "")
					.add("authHost='" + this.getAuthHost() + "'")
					.add("accountsHost='" + this.getAccountsHost() + "'")
					.add("sessionHost='" + this.getSessionHost() + "'")
					.add("name='" + this.getName() + "'")
					.toString();
			}
		};
	}
}
