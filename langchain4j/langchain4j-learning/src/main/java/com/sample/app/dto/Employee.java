package com.sample.app.dto;

import dev.langchain4j.model.output.structured.Description;

@Description("An Employee")
public record Employee(@Description("Employee's first and last name, for example: Krishna") String name,
		@Description("Employee's age, for example: 42") int age,
		@Description("Employee's height in meters, for example: 1.78") double height,
		@Description("is Employee married or not, for example: false") boolean married) {
}
