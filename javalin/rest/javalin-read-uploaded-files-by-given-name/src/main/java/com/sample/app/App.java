package com.sample.app;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.sample.app.dto.FileUploadResponseDto;
import com.sample.app.util.JsonUtil;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;

public class App {

	public static void main(String[] args) {

		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});

		javalin.post("/echo-files-content", (ctx) -> {

			List<UploadedFile> uploadedFiles = ctx.uploadedFiles("myFile");
			List<FileUploadResponseDto> responseList = new ArrayList<>();

			for (UploadedFile uploadedFile : uploadedFiles) {
				String contentType = uploadedFile.contentType();
				InputStream inputStream = uploadedFile.content();
				String fileContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
				long fileSizeInBytes = uploadedFile.size();
				String fileName = uploadedFile.filename();

				FileUploadResponseDto fileUploadesponseDto = new FileUploadResponseDto();
				fileUploadesponseDto.setContent(fileContent);
				fileUploadesponseDto.setContentType(contentType);
				fileUploadesponseDto.setFileName(fileName);
				fileUploadesponseDto.setFileSizeInBytes(fileSizeInBytes);

				responseList.add(fileUploadesponseDto);
			}

			ctx.result(JsonUtil.marshal(responseList)).status(HttpStatus.CREATED);
		});

		javalin.start(8080);
	}

}
