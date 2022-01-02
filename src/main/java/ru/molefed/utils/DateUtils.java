package ru.molefed.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

	public static final ZoneId UTC = ZoneId.of("UTC");

	public static LocalDateTime now() {
		return LocalDateTime.now(UTC);
	}
}
