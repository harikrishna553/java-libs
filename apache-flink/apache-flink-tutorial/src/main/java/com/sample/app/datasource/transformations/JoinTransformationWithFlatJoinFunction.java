package com.sample.app.datasource.transformations;

import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

import com.sample.app.dto.Author;
import com.sample.app.dto.Post;

public class JoinTransformationWithFlatJoinFunction {

	public static class PostAuthorTagsJoiningFuntion
			implements FlatJoinFunction<Post, Author, Tuple3<String, String, String>> {

		@Override
		public void join(Post post, Author author, Collector<Tuple3<String, String, String>> out) throws Exception {
			String postName = post.getName();
			String authorName = author.getName();

			String tagIds = post.getTagIds();
			for (String tagName : tagIds.split(",")) {
				out.collect(new Tuple3(postName, authorName, tagName));
			}

		}

	}

	public static void main(String[] args) throws Exception {

		Author author1 = new Author(1, "Krishna");
		Author author2 = new Author(2, "Ram");
		Author author3 = new Author(3, "Sailu");
		Author author4 = new Author(4, "Rajendra");

		Post post1 = new Post(1, "How to create a thread In Java", "java,threads", 1);
		Post post2 = new Post(2, "Debug Java distributed applications", "java,threads,advanced", 3);
		Post post3 = new Post(3, "Advanced Collections in Java", "java,collections,advanced", 4);

		ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

		DataSource<Author> authorsDataSource = executionEnvironment.fromElements(author1, author2, author3, author4);
		DataSource<Post> postsDataSource = executionEnvironment.fromElements(post1, post2, post3);

		postsDataSource.join(authorsDataSource)
		.where("authorId") // key of the first input dataset
		.equalTo("id") // key of the second input dataset
		.with(new PostAuthorTagsJoiningFuntion())
		.collect()
		.forEach(System.out::println);
	}

}
