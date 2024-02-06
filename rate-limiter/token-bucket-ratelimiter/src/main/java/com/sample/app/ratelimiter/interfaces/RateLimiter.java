package com.sample.app.ratelimiter.interfaces;

import com.sample.app.ratelimiter.exceptions.RateLimiterException;

/**
 * Interface for defining a rate-limiting algorithm.
 */
public interface RateLimiter {

	/**
	 * Checks if access to a resource is allowed based on the rate-limiting
	 * algorithm.
	 * 
	 * @param resourceIdentifier The identifier of the resource (e.g., URI, file
	 *                           path, connection).
	 * @return True if access is allowed, false otherwise.
	 */
	boolean allowAccess(String resourceIdentifier) throws RateLimiterException;

	/**
	 * Resets the rate-limiting state for a given resource.
	 * 
	 * The reset method in the RateLimiter interface serves the purpose of resetting
	 * the rate-limiting state for a specific resource. Let's delve into why this
	 * method is useful:
	 * 
	 * <ol>
	 * 
	 * <li>Clearing state: The rate-limiting algorithms often maintain internal
	 * state information for each resource they are controlling access to. This
	 * state may include variables such as the current token count in a token
	 * bucket, the count of requests within a fixed window, or the state of a
	 * priority queue. The reset method allows you to clear this state, essentially
	 * resetting the algorithm's tracking for that particular resource.</li>
	 * 
	 * <li>Handling State Changes: In dynamic systems, the characteristics of
	 * resources may change over time. For example, the rate of access to a specific
	 * URI might need adjustment due to changes in usage patterns or system
	 * conditions. By providing a reset method, you enable users of your
	 * rate-limiting library to dynamically modify the rate-limiting parameters or
	 * reset the state of a resource when necessary.</li>
	 * 
	 * <li>Error Recovery: If an error condition occurs while processing requests
	 * for a resource, it may be necessary to reset the rate-limiting state to
	 * recover from that error gracefully. For instance, if there's an unexpected
	 * surge in traffic that causes the rate limiter to behave erroneously,
	 * resetting the state can help restore normal operation.
	 * 
	 * <li>Testing and Debugging: During testing and debugging of applications using
	 * the rate-limiting library, being able to reset the rate-limiting state for
	 * specific resources can be invaluable. It allows developers to start with a
	 * clean slate for each test scenario and ensures consistent behavior across
	 * multiple test runs.</li>
	 * 
	 * </ol>
	 * 
	 * @param resourceIdentifier The identifier of the resource (e.g., URI, file
	 *                           path, connection).
	 */
	void reset(String resourceIdentifier) throws RateLimiterException;
}
