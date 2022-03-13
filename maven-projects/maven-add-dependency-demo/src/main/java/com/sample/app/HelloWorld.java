package com.sample.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.app.model.AppDetails;

public class HelloWorld {

	public static void main(String[] args) {
		AppDetails appDetails = new AppDetails("Chat Server", "1.23.4");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(appDetails);

		System.out.println(json);

	}

}
