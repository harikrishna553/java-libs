package com.sample.app;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import com.sample.app.util.FileUtil;

public class GitCommitAll {

	private static void addFile(final Git git, final String fileName, String data) {
		final String repoFolder = git.getRepository().getDirectory().getParent();
		final String filePath = repoFolder + File.separator + fileName;
		FileUtil.write(filePath, data);
		System.out.println("\nCreated new file '" + fileName + "' in the repository");

	}

	public static void main(String[] args) throws IllegalStateException, GitAPIException, IOException {

		System.out.println("Creating new repository");
		final String folderPath = "/Users/Shared/github_examples/hello-world";
		final Git git = Git.init().setDirectory(new File(folderPath)).call();
		System.out.println("branch : " + git.getRepository().getBranch());

		addFile(git, "file1.txt", "Hello World");
		addFile(git, "file2.txt", "Hello World");

		/* Add files to the git repository */
		System.out.println("Add file1.txt, file2.txt to the repository");
		git.add().addFilepattern("*.txt").call();

		System.out.println("Commit file1.txt, file2.txt to the repository");
		git.commit().setMessage("Commit the files file1.txt and file2.txt ").call();

		System.out.println("Update file1.txt content");
		final String repoFolder = git.getRepository().getDirectory().getParent();
		final String filePath = repoFolder + File.separator + "file1.txt";
		try(PrintWriter writer = new PrintWriter(filePath)) {
            writer.append("Hello, world!!!!!");
        }
		
		final String fileToRemove = git.getRepository().getDirectory().getParent() + File.separator + "file2.txt";

		if (new File(fileToRemove).delete()) {
			System.out.println(fileToRemove + " is deleted");
		}

		System.out.println("add file3.txt content");
		addFile(git, "file3.txt", "Welcome!!!!!");

		RevCommit revCommit = git.commit().setAll(true).setMessage("Commit all the modified and deleted files").call();

		System.out.println("Committed all the modified and deleted files");
		System.out.println(revCommit);
	}

}
