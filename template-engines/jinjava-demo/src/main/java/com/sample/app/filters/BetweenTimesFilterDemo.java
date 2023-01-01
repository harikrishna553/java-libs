package com.sample.app.filters;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.hubspot.jinjava.Jinjava;
import com.sample.app.util.FileUtil;

public class BetweenTimesFilterDemo {

	public static void main(String[] args) throws IOException {

		final Jinjava jinjava = new Jinjava();

		final Map<String, Object> data = new HashMap<>();

		final LocalDateTime localDateTime1 = LocalDateTime.of(2017, Month.FEBRUARY, 19, 15, 26);
		final LocalDateTime localDateTime2 = LocalDateTime.of(2022, Month.JANUARY, 5, 5, 5);

		final ZoneId india = ZoneId.of("Asia/Kolkata");
		final ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime1, india);
		final ZonedDateTime zonedDateTime2 = ZonedDateTime.of(localDateTime2, india);

		data.put("startTime", zonedDateTime1);
		data.put("endTime", zonedDateTime2);

		final String template = FileUtil.resourceAsString("filters/betweenTimesFilter.jinja");

		final String finalDocument = jinjava.render(template, data);
		System.out.println(finalDocument);
	}
}
