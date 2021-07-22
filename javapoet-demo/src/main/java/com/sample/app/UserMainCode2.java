package com.sample.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserMainCode2 {
	public int findPassword(int input1, int input2, int input3, int input4, int input5) {

		List<Integer> unStableNumbers = new ArrayList<>();
		List<Integer> numbers = Arrays.asList(input1, input2, input3, input4, input5);

		for (int num : numbers) {
			Map<Integer, Integer> map = new HashMap<>();

			int i = num;
			while (i != 0) {
				int remainder = i % 10;

				if (map.containsKey(remainder)) {
					int count = map.get(remainder);
					map.put(remainder, count + 1);
				} else {
					map.put(remainder, 0);
				}

				i = i / 10;
			}

			Collection<Integer> values = map.values();
			Set<Integer> set = new HashSet<>(values);

			if (set.size() != 1) {
				unStableNumbers.add(num);
			}
		}

		Collections.sort(unStableNumbers);

		int index = unStableNumbers.size() - 1;
		int max = unStableNumbers.get(index);
		int min = unStableNumbers.get(0);
		return max + min;

	}

}
