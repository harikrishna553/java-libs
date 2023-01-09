package com.sample.app;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.PushResult;

import com.sample.app.util.FileUtil;

public class CheckoutAndPushTheBranch {

	public static void main(final String[] args) throws Exception {
		final String folderPath = "/Users/Shared/github_examples/jgit-demo";
		final String repoUrl = "https://github.com/harikrishna553/jgit-demo.git";
		final String userName = "supply username here";
		final String token = "supply token here";
		final String featureBranchName = "feature1";
		final String fileName = "demo1.txt";

		// Clone the repository
		final Git git = Git.cloneRepository().setURI(repoUrl).setCloneAllBranches(true)
				.setDirectory(new File(folderPath)).call();

		System.out.println("Cloned branch : " + git.getRepository().getBranch());

		// checkout and create new branch
		System.out.println("Creating new branch " + featureBranchName);
		Ref featureBranch = git.checkout().setCreateBranch(true).setName(featureBranchName).call();
		System.out.println("\nbranch : " + featureBranch + ", new branch name" + featureBranch.getName());

		// Create files in the repository
		System.out.println("Create " + fileName + " file in the repository");
		final String gitRepoPath = git.getRepository().getDirectory().getParent();
		final String file1Path = gitRepoPath + File.separator + fileName;
		FileUtil.write(file1Path, "Hello World");

		// Add file to the branch
		System.out.println("Add " + fileName + " to git repository");
		git.add().addFilepattern(fileName).call();

		// Commit the files
		System.out.println("Commit changes to " + featureBranchName);
		git.commit().setMessage("Add " + fileName).call();

		// Push result
		System.out.println("\npushResult:");
		Iterable<PushResult> pushResults = git.push()
				.setCredentialsProvider(
						new org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider(userName, token))
				.add(featureBranch).call();
		for (PushResult pushResult : pushResults) {
			System.out.println(pushResult.getMessages());
		}

	}

}
