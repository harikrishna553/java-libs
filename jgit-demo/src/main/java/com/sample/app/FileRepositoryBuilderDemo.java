package com.sample.app;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class FileRepositoryBuilderDemo {

	public static void main(String[] args) throws IOException {
		final String folderPath = "/Users/Shared/hello-world";

		Repository repository = FileRepositoryBuilder.create(new File(folderPath));
		repository.create();

		System.out.println("branch : " + repository.getBranch());
		System.out.println("local metadata directory " + repository.getDirectory());

	}

}
