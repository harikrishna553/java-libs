package com.sample.app;

import java.io.File;

import org.eclipse.jgit.api.Git;

public class CloneGitRepo {

	public static void main(final String[] args) throws Exception {
		final String folderPath = "/Users/Shared/github_examples/server-health-dashboard";
		final String repoUrl = "https://github.com/harikrishna553/server-health-dashboard.git";

		final Git git = Git.cloneRepository().setURI(repoUrl).setDirectory(new File(folderPath)).call();

		System.out.println("branch : " + git.getRepository().getBranch());
	}

}
