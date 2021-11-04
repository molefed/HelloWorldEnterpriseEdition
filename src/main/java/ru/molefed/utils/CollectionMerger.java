package ru.molefed.utils;

import java.util.*;

public abstract class CollectionMerger<From, To> {

	protected abstract To create(From from);

	protected abstract boolean equals(From from, To to);

	protected void remove(To to) {

	}

	protected void update(From from, To to) {

	}

	public void merge(Collection<From> colFrom, Collection<To> colTo) {
		List<From> listForAdd = new ArrayList<>();
		Set<To> setForRemove = new HashSet<>(colTo);
		for (From from : colFrom) {
			To to = colTo.stream()
					.filter(to1 -> equals(from, to1))
					.findFirst()
					.orElse(null);

			if (to == null) {
				listForAdd.add(from);
			} else {
				update(from, to);
				setForRemove.remove(to);
			}
		}

		setForRemove.forEach(to -> {
			remove(to);
			colTo.remove(to);
		});

		listForAdd.forEach(from -> colTo.add(create(from)));
	}
}
