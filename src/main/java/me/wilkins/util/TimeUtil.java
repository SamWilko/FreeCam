package me.wilkins.util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

	public static TimeUnit getNextTimeUnit(TimeUnit timeUnit) {
		List<TimeUnit> timeUnitList = Arrays.asList(TimeUnit.SECONDS, TimeUnit.MINUTES, TimeUnit.HOURS, TimeUnit.DAYS);

		int timeUnitIndex = timeUnitList.indexOf(timeUnit);

		if(timeUnitIndex + 1 >= timeUnitList.size())
			timeUnitIndex = 0;

		return TimeUnit.valueOf(timeUnitList.get(timeUnitIndex + 1).toString());
	}

	public static TimeUnit getLastTimeUnit(TimeUnit timeUnit) {
		List<TimeUnit> timeUnitList = Arrays.asList(TimeUnit.SECONDS, TimeUnit.MINUTES, TimeUnit.HOURS, TimeUnit.DAYS);

		int timeUnitIndex = timeUnitList.indexOf(timeUnit);

		if(timeUnitIndex - 1 < 0)
			timeUnitIndex = timeUnitList.size() - 1;

		return TimeUnit.valueOf(timeUnitList.get(timeUnitIndex - 1).toString());
	}

}
