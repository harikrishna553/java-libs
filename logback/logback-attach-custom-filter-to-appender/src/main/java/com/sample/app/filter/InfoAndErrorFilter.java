package com.sample.app.filter;

import java.util.Arrays;
import java.util.List;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;

public class InfoAndErrorFilter extends AbstractMatcherFilter {

	@Override
	public FilterReply decide(Object event) {
		if (!isStarted()) {
			return FilterReply.NEUTRAL;
		}

		LoggingEvent loggingEvent = (LoggingEvent) event;

		List<Level> eventsToKeep = Arrays.asList(Level.INFO, Level.ERROR);
		if (eventsToKeep.contains(loggingEvent.getLevel())) {
			return FilterReply.ACCEPT;
		} else {
			return FilterReply.DENY;
		}
	}

}
