package com.sample.app;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import com.sample.app.util.FileUtil;

public class GitCommitDemo {

	public static void main(String[] args) throws IllegalStateException, GitAPIException, IOException {

		System.out.println("Creating new repository");
		final String folderPath = "/Users/Shared/github_examples/hello-world";
		final Git git = Git.init().setDirectory(new File(folderPath)).call();
		System.out.println("branch : " + git.getRepository().getBranch());

		final String repoFolder = git.getRepository().getDirectory().getParent();
		final String fileToAdd = "hello.txt";
		final String filePath = repoFolder + File.separator + fileToAdd;
		FileUtil.write(filePath, "hello.txt");
		System.out.println("\nCreated new file '" + fileToAdd + "' in the repository");

		/* Add file to the git repository */
		git.add().addFilepattern(fileToAdd).call();
		System.out.println("Added the file '" + fileToAdd + "' to the repository");

		git.commit().setMessage("Commit the file " + fileToAdd).call();
		System.out.println("Commited the file '" + fileToAdd + "' to the repository");

	}

}
