package ncomegf.bpw.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.ncom.core.util.DateUtil;

/**
 * <p>
 * Subsystem :
 * </p>
 * <p>
 * Title : 기본적인 유틸함수
 * </p>
 * <p>
 * Description : 기본적인 유틸함수를 구현하여 제공.
 * </p>
 * <p>
 * 관련 TABLE :
 * </p>
 * 
 * @author 김경덕
 * @version 1.0 2003.05.29 <br/>
 */
public class DateUtilExt extends DateUtil {

	/**
	 * Cisco 날짜 유틸 UDT 기준
	 * 
	 * @param timestamp
	 * @return
	 * @throws Exception
	 */
	public static String getCurDatetimeUTC() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+0"));
		return sdf.format(new java.util.Date());
	}

	/**
	 * @param intervalHour
	 * @return
	 * @throws Exception
	 */
	public static long getJobUnixTimeUTC(int intervalHour) throws Exception {

		String jobHour = (DateUtilExt.getCurDatetimeUTC()).substring(0, 10) + "0000";// 20210311140000

		Date dateJobHour = DateUtilExt.toDate(jobHour);

		long startSearchHourUTC = dateJobHour.getTime() / 1000 - (60 * 60 * intervalHour);// UTC 시간 - 조회 시간

		return startSearchHourUTC;
	}

	/**
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static Date toDate(String time) throws Exception {

		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.parse(time);
	}

	/**
	 * @param timestamp
	 * @return
	 * @throws Exception
	 */
	public static String toDateStringSeoul(long timestamp) throws Exception {

		Date date = new java.util.Date(timestamp * 1000L);
		// SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ");
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");

		sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));

		String formattedDate = sdf.format(date);

		return formattedDate;
	}

	public static long toUnixTimeSeoul(String time) throws Exception {

		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmms", Locale.ENGLISH);
		long unixTime = 0;
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+9:00"));
		unixTime = sdf.parse(time).getTime();
		unixTime = unixTime / 1000;

		return unixTime;
	}

	public static String toDateString(long timestamp) throws Exception {

		Date date = new java.util.Date(timestamp * 1000L);
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
		// 2021-03-12T01:02:56.869555Z

		// sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+0"));

		String formattedDate = sdf.format(date);

		return formattedDate;
	}

	public static String getCurDatetimeSeoul() {
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		return smf.format(new java.util.Date());
	}

}
