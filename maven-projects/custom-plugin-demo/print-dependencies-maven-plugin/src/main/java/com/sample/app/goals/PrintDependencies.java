package com.sample.app.goals;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "print-dependencies", defaultPhase = LifecyclePhase.COMPILE)
public class PrintDependencies extends AbstractMojo {

	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	MavenProject project;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		List<Dependency> dependencies = project.getDependencies();

		System.out.println("Executing the goal--->print-dependencies");
		System.out.println("_________________________________________");
		for (Dependency dependency : dependencies) {
			System.out.println("groupId : " + dependency.getGroupId());
			System.out.println("artifactId : " + dependency.getArtifactId());
			System.out.println("version : " + dependency.getVersion() + "\n");
		}

		System.out.println("_________________________________________");

	}

}
