package com.sample.app;

import java.io.File;

import org.eclipse.jgit.api.Git;

public class HelloWorld {

	public static void main(final String[] args) throws Exception {
		final String folderPath = "/Users/Shared/github_examples/hello-world";
		final Git git = Git.init().setDirectory(new File(folderPath)).call();

		System.out.println("branch : " + git.getRepository().getBranch());
	}

}
