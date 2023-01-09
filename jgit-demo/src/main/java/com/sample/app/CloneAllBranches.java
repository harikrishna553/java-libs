package com.sample.app;

import java.io.File;

import org.eclipse.jgit.api.Git;

public class CloneAllBranches {

	public static void main(final String[] args) throws Exception {
		final String folderPath = "/Users/Shared/github_examples/jgit-demo";
		final String repoUrl = "https://github.com/harikrishna553/jgit-demo.git";

		final Git git = Git.cloneRepository().setURI(repoUrl).setDirectory(new File(folderPath))
				.setCloneAllBranches(true)
				.setBranch("refs/heads/branch2").call();

		System.out.println("branch : " + git.getRepository().getBranch());
	}

}
