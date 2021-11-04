package ru.molefed.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CollectionMergerTest {

	@Test
	void merge() {

		List<String> from = List.of("1", "5");
		List<Integer> to = new ArrayList<>(List.of(8, 9, 5));

		new CollectionMerger<String, Integer>() {

			@Override
			protected Integer create(String s) {
				return Integer.valueOf(s);
			}

			@Override
			protected boolean equals(String s, Integer integer) {
				return integer.equals(create(s));
			}
		}.merge(from, to);

		assertIterableEquals(List.of(5, 1), to);
	}
}
