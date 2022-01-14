package com.sample.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import com.sample.app.connection.MySQLConnection;
import com.sample.app.model.DatabaseMetadataDto;
import com.sample.app.util.JsonUtil;

public class App {

	public static void main(String args[]) {

		String user = "";
		String password = "";
		String connectionURL = "jdbc:mysql://{ERVER}:3306/{DATABASE}";

		try (MySQLConnection mySqlConnection = new MySQLConnection(connectionURL, user, password)) {
			DatabaseMetadataDto databaseMetadataDto = mySqlConnection.getMetadata();
			String json = JsonUtil.getPrettyJson(databaseMetadataDto);
			System.out.println(json);
			
			 Files.write(Paths.get("log.json"), json.getBytes());

		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
