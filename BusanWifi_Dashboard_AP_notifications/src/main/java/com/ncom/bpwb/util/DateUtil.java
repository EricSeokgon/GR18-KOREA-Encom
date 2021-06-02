package com.ncom.bpwb.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
public class DateUtil {

	/**
	 * 날짜 입력을 문자열(년-월-일 시:분:초 1000/1 초)로 변환하여 반환
	 * 
	 * @param d
	 * @return String
	 */
	public static String date(Date d) {
		if (d == null)
			return null;
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		return smf.format(d);
	}

	/**
	 * 날짜 입력을 문자열(년-월-일)로 변환하여 반환
	 * 
	 * @param d
	 * @return String
	 */
	public static String dateOnly(Date d) {
		if (d == null)
			return null;
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
		return smf.format(d);
	}

	/**
	 * 날짜 입력을 문자열(년-월-일 시:분:초)로 변환하여 반환
	 * 
	 * @param d
	 * @return String
	 */
	public static String datetimeOnly(Date d) {
		if (d == null)
			return null;
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return smf.format(d);
	}

	/**
	 * 날짜 입력을 문자열(년-월-일 시:분:초)로 변환하여 반환
	 * 
	 * @return String
	 */
	public static String getCurDatetime() {
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		return smf.format(new java.util.Date());
	}

	/**
	 * 날짜 입력을 문자열(년-월-일 시:분:초)로 변환하여 반환
	 * 
	 * @param d
	 * @return String
	 */
	public static String getCurDatetimeString() {
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return smf.format(new java.util.Date());
	}

	/**
	 * 날짜 입력을 문자열을 패턴에 맞게 변환하여 반환
	 * 
	 * @param d
	 *            Date
	 * @param pattern
	 *            String
	 * @return String
	 */
	public static String getTimeAsPattern(Date d, String pattern) {
		if (d == null)
			return null;

		if (StrUtil.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}

		SimpleDateFormat smf = new SimpleDateFormat(pattern, new Locale("ko", "KOREA"));
		return smf.format(d);
	}

	/**
	 * 패턴에 따른 현재시간을 반환
	 * 
	 * @param pattern
	 * @return String
	 */
	public static String getCurTime(String pattern) {
		if (StrUtil.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}

		Date date = new java.util.Date();

		SimpleDateFormat formatter = new SimpleDateFormat(pattern, new Locale("ko", "KOREA"));
		String result = formatter.format(date);

		return result;
	}

	/**
	 * 현재시간을 java.util.Date 반환
	 * 
	 * @return Date
	 */
	public static java.util.Date getCurTime() {

		return new java.util.Date();
	}

	/**
	 * 날짜및 시간 : 문자열 입력을 Date(java.util) 변환하여 반환
	 * 
	 * @param s
	 *            String
	 * @param pattern
	 *            String
	 * @return Date
	 */
	public static Date str2date(String s, String pattern) {
		if (s == null || s.equals(""))
			return null;

		if (StrUtil.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}

		try {
			SimpleDateFormat smf = new SimpleDateFormat(pattern);
			return smf.parse(s, new ParsePosition(0));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 날짜및 시간 : 문자형식의 날짜를 입력 받아 특정 형태의 패턴으로 변환.
	 * 
	 * @param s
	 *            String
	 * @param dateFormat
	 *            String
	 * @param pattern
	 *            String
	 * @return String
	 */
	public static String getChangDatePattern(String s, String dateFormat, String pattern) {
		if (s == null || s.equals(""))
			return null;

		if (StrUtil.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}

		try {
			SimpleDateFormat smf = new SimpleDateFormat(dateFormat);
			return getTimeAsPattern(smf.parse(s), pattern);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 날짜및 시간 : 문자형식의 date를 입력 받아 년월일만 잘라서 반환.
	 * 
	 * @param date
	 *            String
	 * @return String
	 */
	public static String cutStrDate(String date) {
		if (date == null || date.equals("") || date.length() < 10)
			return date;
		return date.substring(0, 10);
	}

	/**
	 * 날짜 차이 구하기
	 * 
	 * @param s1
	 *            String
	 * @param s2
	 *            String
	 * @return int
	 */
	public static int getDaysDiff(String s1, String s2) {
		int y1 = getDaysFrom21Century(s1);
		int y2 = getDaysFrom21Century(s2);
		return y1 - y2;
	}

	/**
	 * 2000년 1월 1일 부터 지정한 년, 월, 일 까지의 날짜 수를 구한다.<br>
	 * 2000년 1월 1일 이전의 경우에는 음수를 리턴한다.
	 * 
	 * @param s
	 *            String
	 * @return int
	 */
	public static int getDaysFrom21Century(String s) {
		int d, m, y;
		if (s.length() == 8) {
			y = Integer.parseInt(s.substring(0, 4));
			m = Integer.parseInt(s.substring(4, 6));
			d = Integer.parseInt(s.substring(6));
			return getDaysFrom21Century(d, m, y);
		} else if (s.length() == 10) {
			y = Integer.parseInt(s.substring(0, 4));
			m = Integer.parseInt(s.substring(5, 7));
			d = Integer.parseInt(s.substring(8));
			return getDaysFrom21Century(d, m, y);
		} else if (s.length() == 11) {
			d = Integer.parseInt(s.substring(0, 2));
			String strM = s.substring(3, 6).toUpperCase();
			String[] monthNames = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
			m = 0;
			for (int j = 1; j <= 12; j++) {
				if (strM.equals(monthNames[j - 1])) {
					m = j;
					break;
				}
			}
			if (m < 1 || m > 12)
				throw new RuntimeException("Invalid month name: " + strM + " in " + s);
			y = Integer.parseInt(s.substring(7));
			return getDaysFrom21Century(d, m, y);
		} else
			throw new RuntimeException("Invalid date format: " + s);
	}

	/**
	 * 지정한 년도, 지정한 월의 총 날짜 수를 구한다.
	 * 
	 * @param m
	 *            int
	 * @param y
	 *            int
	 * @return int
	 */
	public static int getDaysInMonth(int m, int y) {
		if (m < 1 || m > 12)
			throw new RuntimeException("Invalid month: " + m);

		int[] b = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (m != 2 && m >= 1 && m <= 12 && y != 1582)
			return b[m - 1];
		if (m != 2 && m >= 1 && m <= 12 && y == 1582)
			if (m != 10)
				return b[m - 1];
			else
				return b[m - 1] - 10;

		if (m != 2)
			return 0;

		// m == 2 (즉 2월)
		if (y > 1582) {
			if (y % 400 == 0)
				return 29;
			else if (y % 100 == 0)
				return 28;
			else if (y % 4 == 0)
				return 29;
			else
				return 28;
		} else if (y == 1582)
			return 28;
		else if (y > 4) {
			if (y % 4 == 0)
				return 29;
			else
				return 28;
		} else if (y > 0)
			return 28;
		else
			throw new RuntimeException("Invalid year: " + y);
	}

	/**
	 * 지정한 년도의 지정한 월의 첫날 부터 지정한 날 까지의 날짜 수를 구한다.
	 * 
	 * @param d
	 *            int
	 * @param m
	 *            int
	 * @param y
	 *            int
	 * @return int
	 */
	public static int getDaysFromMonthFirst(int d, int m, int y) {
		if (m < 1 || m > 12)
			throw new RuntimeException("Invalid month " + m + " in " + d + "/" + m + "/" + y);

		int max = getDaysInMonth(m, y);
		if (d >= 1 && d <= max)
			return d;
		else
			throw new RuntimeException("Invalid date " + d + " in " + d + "/" + m + "/" + y);
	}

	/**
	 * 지정한 년도의 첫날 부터 지정한 월의 지정한 날 까지의 날짜 수를 구한다.
	 * 
	 * @param d
	 *            int
	 * @param m
	 *            int
	 * @param y
	 *            int
	 * @return int
	 */
	public static int getDaysFromYearFirst(int d, int m, int y) {
		if (m < 1 || m > 12)
			throw new RuntimeException("Invalid month " + m + " in " + d + "/" + m + "/" + y);

		int max = getDaysInMonth(m, y);
		if (d >= 1 && d <= max) {
			int sum = d;
			for (int j = 1; j < m; j++)
				sum += getDaysInMonth(j, y);
			return sum;
		} else
			throw new RuntimeException("Invalid date " + d + " in " + d + "/" + m + "/" + y);
	}

	/**
	 * 문자형식의 날짜를 입력 받아 특정 형태의 패턴으로 변환
	 * 
	 * @param format
	 *            String
	 * @param dateString
	 *            String
	 * @return String
	 */
	public static String getDateFormat(String format, String dateString) {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		String rtValue = null;
		try {
			Date d = sd.parse(dateString);
			rtValue = sd.format(d);
		} catch (ParseException e) {
		}
		return rtValue;
	}

	/**
	 * 지정한 년도의 첫날 부터 지정한 월의 지정한 날 까지의 날짜 수를 구한다.
	 * 
	 * @param s
	 *            String
	 * @return int
	 */
	public static int getDaysFromYearFirst(String s) {
		int d, m, y;
		if (s.length() == 8) {
			y = Integer.parseInt(s.substring(0, 4));
			m = Integer.parseInt(s.substring(4, 6));
			d = Integer.parseInt(s.substring(6));
			return getDaysFromYearFirst(d, m, y);
		} else if (s.length() == 10) {
			y = Integer.parseInt(s.substring(0, 4));
			m = Integer.parseInt(s.substring(5, 7));
			d = Integer.parseInt(s.substring(8));
			return getDaysFromYearFirst(d, m, y);
		} else if (s.length() == 11) {
			d = Integer.parseInt(s.substring(0, 2));
			String strM = s.substring(3, 6).toUpperCase();
			String[] monthNames = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
			m = 0;
			for (int j = 1; j <= 12; j++) {
				if (strM.equals(monthNames[j - 1])) {
					m = j;
					break;
				}
			}
			if (m < 1 || m > 12)
				throw new RuntimeException("Invalid month name: " + strM + " in " + s);
			y = Integer.parseInt(s.substring(7));
			return getDaysFromYearFirst(d, m, y);
		} else
			throw new RuntimeException("Invalid date format: " + s);
	}

	/**
	 * 2000년 1월 1일 부터 지정한 년, 월, 일 까지의 날짜 수를 구한다. 2000년 1월 1일 이전의 경우에는 음수를 리턴한다.
	 * 
	 * @param d
	 *            int
	 * @param m
	 *            int
	 * @param y
	 *            int
	 * @return int
	 */
	public static int getDaysFrom21Century(int d, int m, int y) {
		if (y >= 2000) {
			int sum = getDaysFromYearFirst(d, m, y);
			for (int j = y - 1; j >= 2000; j--)
				sum += getDaysInYear(j);
			return sum - 1;
		} else if (y > 0 && y < 2000) {
			int sum = getDaysFromYearFirst(d, m, y);
			for (int j = 1999; j >= y; j--)
				sum -= getDaysInYear(y);
			return sum - 1;
		} else
			throw new RuntimeException("Invalid year " + y + " in " + d + "/" + m + "/" + y);
	}

	/**
	 * 지정한 년도의 총 날짜 수를 구한다.
	 * 
	 * @param y
	 *            int
	 * @return int
	 */
	public static int getDaysInYear(int y) {
		if (y > 1582) {
			if (y % 400 == 0)
				return 366;
			else if (y % 100 == 0)
				return 365;
			else if (y % 4 == 0)
				return 366;
			else
				return 365;
		} else if (y == 1582)
			return 355;
		else if (y > 4) {
			if (y % 4 == 0)
				return 366;
			else
				return 365;
		} else if (y > 0)
			return 365;
		else
			return 0;
	}

	/**
	 * new 표시 관련 날짜 구하기
	 * 
	 * @return intNewDay String
	 * @return inDate String
	 * @throws Exception
	 */
	public static boolean isNew(int intNewDay, String inDate) throws Exception {

		Date mydate = new Date(); // 시스템 날짜를 사용할 mydate객체 생성
		Date indate = new Date(); // 게시물에 등록일을 사용할 indate객체 생성

		String today = ""; // 오늘 날짜
		String in_date = ""; // 등록 일자

		// 출력 형태를 가지고 있는 객체를 생성
		SimpleDateFormat myToday = new SimpleDateFormat("yyyy-MM-dd");

		indate = str2date(inDate, "yyyy-MM-dd");

		today = myToday.format(mydate);
		in_date = myToday.format(indate);

		// 등록일이 NEW_DAY일 이하 인경우 NEW 표시
		if (intNewDay >= DateUtil.getDaysDiff(today, in_date)) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 * 입력한 년월일의 익월 구하는 함수 getNextMonthDay(200601) ==> 200602
	 * 
	 * @param sThisYmd
	 * @return
	 */
	public static String getNextMonthDay(String sThisYm) {

		if (sThisYm == null || sThisYm.equals(""))
			return sThisYm;

		String sNextYm = ""; // 다음 년월

		try {

			// 년월은 6자리(YYYYMM)를 입력해야 한다...
			if (sThisYm.length() != 6) {
				return sNextYm;
			}

			int iThisYear = Integer.parseInt(sThisYm.substring(0, 4));
			int iThisMonth = Integer.parseInt(sThisYm.substring(4, 6));

			if (iThisMonth == 12) {
				sNextYm = (iThisYear + 1) + "01";

			} else {
				sNextYm = sThisYm.substring(0, 4) + getFillZero(String.valueOf(iThisMonth + 1), 2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sNextYm;
	}

	/**
	 * 입력한 년월의 00.00형식으로 포멧
	 * 
	 * @param s
	 * @return
	 */
	public static String getYearMonthForm(String s) {

		if (s == null || s.equals(""))
			return s;

		// 년월은 6자리(YYYYMM)를 입력해야 한다...
		if (s.length() != 6) {
			return s;
		}

		return s.substring(2, 4) + "." + s.substring(4, 6);
	}

	/**
	 * 숫자앞에 입력한 자리수 만큼 0을 붙여주는 함수 getFillZero("1",2) ==> 01
	 * 
	 * @param sInput
	 * @param iCount
	 * @return
	 */
	public static String getFillZero(String sInput, int iCount) {

		if (sInput == null || sInput.equals(""))
			return sInput;

		String strTemp = sInput.trim();

		int iLen = strTemp.length();

		int iRemain = iCount - iLen;

		if (iRemain > 0) {
			for (int j = 0; j < iRemain; j++) {
				strTemp = "0" + strTemp;
			}
		}

		return strTemp;
	}

	/**
	 * 특정 일에서 지정한 날수만큼 이전 이후의 날짜를 지정한 포맷으로 반환한다.
	 * 
	 * @param initDate
	 * @param addDate
	 * @param dataFormat
	 * @return
	 */
	public static String getDateInitAdd(String initDate, int addDate, String dataFormat) {
		int d = 0, m = 0, y = 0;

		if (initDate.length() == 8) {
			y = Integer.parseInt(initDate.substring(0, 4));
			m = Integer.parseInt(initDate.substring(4, 6)) - 1;
			d = Integer.parseInt(initDate.substring(6));

		} else if (initDate.length() == 10) {
			y = Integer.parseInt(initDate.substring(0, 4));
			m = Integer.parseInt(initDate.substring(5, 7)) - 1;
			d = Integer.parseInt(initDate.substring(8));

		}

		Calendar cal = Calendar.getInstance();

		cal.set(y, m, d);

		cal.add(cal.DATE, addDate);

		String rdate = getTimeAsPattern(cal.getTime(), dataFormat);

		return rdate;

	}

	/**
	 * 특정 일에서 지정한 월수만큼 이전 이후의 날짜를 지정한 포맷으로 반환한다.
	 * 
	 * @param initDate
	 * @param addMonth
	 * @param dataFormat
	 * @return
	 */
	public static String getDateInitAddMonth(String initDate, int addMonth, String dataFormat) {
		int d = 0, m = 0, y = 0;

		if (initDate.length() == 8) {
			y = Integer.parseInt(initDate.substring(0, 4));
			m = Integer.parseInt(initDate.substring(4, 6)) - 1;
			d = Integer.parseInt(initDate.substring(6));

		} else if (initDate.length() == 10) {
			y = Integer.parseInt(initDate.substring(0, 4));
			m = Integer.parseInt(initDate.substring(5, 7)) - 1;
			d = Integer.parseInt(initDate.substring(8));

		}

		Calendar cal = Calendar.getInstance();

		cal.set(y, m, d);

		cal.add(cal.MONTH, addMonth);

		String rdate = getTimeAsPattern(cal.getTime(), dataFormat);

		return rdate;

	}

	/**
	 * 입력 받은 날짜의 차이
	 * 
	 * @param begin
	 * @param end
	 * @param dataFormat
	 * @return
	 * @throws Exception
	 */
	public static long diffOfDate(String begin, String end, String dataFormat) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);

		Date beginDate = formatter.parse(begin);
		Date endDate = formatter.parse(end);

		long diff = endDate.getTime() - beginDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);

		return diffDays;
	}

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

		String jobHour = (DateUtil.getCurDatetimeUTC()).substring(0, 10)+"0000";//20210311140000
		
		Date dateJobHour = DateUtil.toDate(jobHour);
		
		long startSearchHourUTC = dateJobHour.getTime()/1000  -(60*60*2);// UTC 시간 - 조회 시간 
		

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
//		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ");
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
