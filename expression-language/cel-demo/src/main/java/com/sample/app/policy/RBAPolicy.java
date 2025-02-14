package com.sample.app.policy;

import java.util.List;

public class RBAPolicy {
	private String title;
	private List<String> adGroups;
	private List<String> location;
	private List<String> country;
	private String manager;
	private String userId;

	public RBAPolicy(String title, List<String> adGroups, List<String> location, List<String> country, String manager,
			String userId) {
		this.title = title;
		this.adGroups = adGroups;
		this.location = location;
		this.country = country;
		this.manager = manager;
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getAdGroups() {
		return adGroups;
	}

	public List<String> getLocation() {
		return location;
	}

	public List<String> getCountry() {
		return country;
	}

	public String getManager() {
		return manager;
	}

	public String getUserId() {
		return userId;
	}

}
