package com.yi.core.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author xuyh
 *
 */
public class LocalDateUtil extends org.apache.commons.lang3.time.DateUtils {

	/**
	 * 根据年月日 比较日期
	 * 
	 * @param thisDate
	 * @param anotherDate
	 * @return if thisDate &lgt anotherDate then return -1</br>
	 * 
	 */
	public boolean beforeByDate(Date thisDate, Date anotherDate) {
		Instant instant = thisDate.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate localDate1 = instant.atZone(zoneId).toLocalDate();
		// DateTimeFormatter
		return false;
	}

	/**
	 * 根据年月日 比较日期
	 * 
	 * @param thisDate
	 * @param anotherDate
	 * @return if thisDate &lgt anotherDate then return -1</br>
	 * 
	 */
	public boolean beforeByDateTime(Date thisDate, Date anotherDate) {
		return true;
	}

}
