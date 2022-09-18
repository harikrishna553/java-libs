package com.sample.app;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.toml.TomlFactory;
import com.sample.app.util.FileUtil;

public class ParseTomlString {

	public static void main(String[] args) throws IOException {

		TomlFactory tomlFactory = new TomlFactory();
		JsonParser parser = tomlFactory.createParser(new File(FileUtil.resourceFilePath("tomlParserDemo1.toml")));

		while (!parser.isClosed()) {
			JsonToken token = parser.nextToken();

			/* null indicates end-of-input */
			if (token == null)
				break;

			String fieldName = parser.getCurrentName();

			if ("id".equals(fieldName)) {
				parser.nextToken();
				System.out.println("id : " + parser.getText());
			} else if ("firstName".equals(fieldName)) {
				parser.nextToken();
				System.out.println("firstName : " + parser.getText());
			} else if ("lastName".equals(fieldName)) {
				parser.nextToken();
				System.out.println("lastName : " + parser.getText());
			} else if ("hobbies".equals(fieldName)) {
				System.out.println("Hobbies:");
				parser.nextToken();
				while (parser.nextToken() != JsonToken.END_ARRAY) {
					String field = parser.getText();
					System.out.println("\t" + field);
				}
			}
		}

		parser.close();

	}

}
