
package com.ncom.bpwb.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//org.apache.commons.lang3.StringUtils; 참고해야함.
/**
 * <p>문자열 처리</p>
 * 
 * @since 2010-04-06
 */
public class StrUtil
{

  	/**
  	 * 정수변환 : 문자열 입력을 정수로 변환하여 반환
  	 * @param str String
  	 * @return int
  	 */
 	public  static int str2int(String str)  {
 		
 		int li = 0 ;

     	try {
     		if (str == null || str.equals("")) return 0;
     		li = Integer.parseInt(str);
     
     	} catch (Exception e) {
     		System.out.println(e);
     	}
     
     	return li ;
 	}

  	/**
  	 * 정수변환 : Object 입력을 정수로 변환하여 반환
  	 * @param Object str
  	 * @return int
  	 */
 	public  static int obj2int(Object obj)  {
 		
 		int  li = 0 ;

 		if(obj instanceof Double ) li= ((Double)obj).intValue();
 		else if(obj instanceof Float ) li= ((Float)obj).intValue();
 		else if(obj instanceof Long ) li= ((Long)obj).intValue();
 		else if(obj instanceof Integer ) li= ((Integer)obj).intValue();
 		else if(obj instanceof BigDecimal ) li= ((BigDecimal)obj).intValue();
 		else if(obj instanceof String ) li= str2int((String)obj);

     
     	return li ;
 	}		
 	

 	/**
 	 * long 변환 : 문자열 입력을 long으로 변환하여 반환
 	 * @param str String
 	 * @return long
 	 */
 	public static long str2long(String str)  {
 		long li = 0 ;

     	try {
     		if (str == null || str.equals("")) return 0;
     		li = Long.parseLong(str);
     
     	} catch (Exception e) {
     		System.out.println(e);
     	}

 		return li;
 	}
 	
 	
 	/**
 	 * double 변환 : 문자열 입력을 double으로 변환하여 반환
 	 * @param str String
 	 * @return long
 	 */
 	public static double str2double(String str)  {
 		double li = 0 ;

     	try {
     		if (str == null || str.equals("")) return 0;
     		li = Double.parseDouble(str);
     
     	} catch (Exception e) {
     		System.out.println(e);
     	}

 		return li;
 	}
 	
 	/**
 	 * 
 	 * @param str
 	 * @return
 	 */
 	public static float str2float(String str)  {
 		float li = 0 ;

     	try {
     		if (str == null || str.equals("")) return 0;
     		li = Float.parseFloat(str);
     
     	} catch (Exception e) {
     		System.out.println(e);
     	}

 		return li;
 	}
 	
  	/**
  	 * 정수변환 : Object 입력을 long로 변환하여 반환
  	 * @param Object obj
  	 * @return long
  	 */
 	public  static long obj2long(Object obj)  {
 		
 		long  li = 0 ;

 		if(obj instanceof Double ) li= ((Double)obj).longValue();
 		else if(obj instanceof Float ) li= ((Float)obj).longValue();
 		else if(obj instanceof Long ) li= ((Long)obj).longValue();
 		else if(obj instanceof Integer ) li= ((Integer)obj).longValue();
 		else if(obj instanceof String ) li= str2long((String)obj);

     
 		return li ;
 	}	
 	

	/**
	 * array 변환 : 문자열 입력을 구분자로 구분하여 배열로 변환하여 반환
	 * @param str   String
	 * @param delim String
	 * @return String[]
	 */
	public static String[] str2strs(String str, String delim) {
        String[] strs= null;
        
        if(!isEmpty(str)){ //배열에 담는다.
            StringTokenizer st= new StringTokenizer(str,delim);
            int size= st.countTokens();
            int idx= 0;
            strs= new String[size];
            while(st.hasMoreTokens()){
                strs[idx]= st.nextToken().trim();
                idx++;
            }
        }

        return strs;
	}
	
    /**
     * 구분자로 문자열을 잘라서 List로 반환
     * @param str
     * @param delim
     * @return
     */
    public static List<String> str2List(String str, String delim) 
	{
    	List<String> list = null;
        String[] arr = str2strs(str, delim);
        if(arr != null){
        	list = Arrays.asList(arr);
        }
        return list;
	}
    
	/**
	 * Key값을 boolean으로 리턴한다.
     * true/false의 대소문자는 고려하지 않는다.
	 * @param srt String
	 * @return boolean
	 */
    public  static boolean str2Boolean(String srt) {

       if("TRUE".equalsIgnoreCase(srt) == true)
           return true;
       else
           return false;
   }


    /**
     * Key값 Y,N 을 boolean으로 리턴한다.
     * Y,N 의 대소문자는 고려하지 않는다.
     * @param strYN String
     * @return boolean
     */
    public static boolean yn2Boolean(String strYN) {

       if("Y".equalsIgnoreCase(strYN) == true)
           return true;
       else
           return false;
   }
    
	/**
     * 수학의 차집합을 배열로 반환
     * @param String[] firstArray, String[] secondArray
     * @return   String[]
     * @throws Exception
     */
    public static String[] getDifferenceOfSets(String[] firstArray, String[] secondArray)  {

        Vector temp=new Vector();
        for (int i=0; i < firstArray.length ; i++){
            boolean isSame=false;
            if (secondArray !=null ){
                for (int j=0;j< secondArray.length; j++){
                    if (firstArray[i].equals(secondArray[j])){
                        isSame=true;
                        break ;
                    }
                }
            }

            if (!isSame){
                temp.add(firstArray[i]);
            }
        }//end for (int i=0; i < firstArray.length ; i++)
        String[] result= (String[])temp.toArray(new String[0]);
        return result;

    }	
	
	/**
     * <p>문자열이 not empty ("") and not null 체크.</p>
     *
     * <pre>
     * UtilStr.isNotEmpty(null)      = false
     * UtilStr.isNotEmpty("")        = false
     * UtilStr.isNotEmpty(" ")       = true
     * UtilStr.isNotEmpty("bob")     = true
     * UtilStr.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
    public static boolean isNotEmpty(Object obj)
    {
        return !isEmpty(obj);
    }
    
	 public static boolean isEmpty(Object obj){
	     if(obj ==null)
	         return true;
	     if( obj instanceof String && "".equals(((String)obj).trim())  )
	         return true;
	     return false;
	 }
    /**
    * <p> empty인 경우 defaultStr로 대체하기. </p>
    *
    * <pre>
    * defaultIfEmpty(null, "NULL")  = "NULL"
    * defaultIfEmpty("", "NULL")    = "NULL"
    * defaultIfEmpty("bat", "NULL") = "bat"
    * defaultIfEmpty("", null)      = null
    * </pre>
    *
    * @param str
    * @param defaultStr
    * @return
    */    
    public static String defaultIfEmpty(Object str, String defaultStr) 
    {
        return (String) (isEmpty(str) ? defaultStr : str);
    }
    
    /**
     * <p> empty인 경우 ""로 대체하기. </p>
     * 
     * @author jupiter_d
     * @since 2017. 6. 27.
     * @param str
     * @return
     */
    public static String defaultEmpty( Object str) 
    {
    	 
        return  isEmpty(str) ? "" : str.toString();
    }
        
    
    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * UtilStr.isBlank(null)      = true
     * UtilStr.isBlank("")        = true
     * UtilStr.isBlank(" ")       = true
     * UtilStr.isBlank("bob")     = false
     * UtilStr.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <pre>
     * UtilStr.isNotBlank(null)      = false
     * UtilStr.isNotBlank("")        = false
     * UtilStr.isNotBlank(" ")       = false
     * UtilStr.isNotBlank("bob")     = true
     * UtilStr.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     *  not empty and not null and not whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !StrUtil.isBlank(cs);
    }  
    
    
    /**
     * <p>Returns either the passed in CharSequence, or if the CharSequence is
     * whitespace, empty ("") or {@code null}, the value of {@code defaultStr}.</p>
     *
     * <pre>
     * UtilStr.defaultIfBlank(null, "NULL")  = "NULL"
     * UtilStr.defaultIfBlank("", "NULL")    = "NULL"
     * UtilStr.defaultIfBlank(" ", "NULL")   = "NULL"
     * UtilStr.defaultIfBlank("bat", "NULL") = "bat"
     * UtilStr.defaultIfBlank("", null)      = null
     * </pre>
     * @param <T> the specific kind of CharSequence
     * @param str the CharSequence to check, may be null
     * @param defaultStr  the default CharSequence to return
     *  if the input is whitespace, empty ("") or {@code null}, may be null
     * @return the passed in CharSequence, or the default
     * @see StrUtil#defaultIfEmpty(String, String)
     */
    public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
        return StrUtil.isBlank(str) ? defaultStr : str;
    }
    
    
	/**
	 * <p>정규식과 일치하는 문자검색.</p>
	 * 
	 * @param regxExpress 정규표현식
	 * @param content 내용
	 * @return 일치하는 첫번째 문자열
	 */
	public static String getMatch(String regxExpress, String content)
	{
		String result = "";
		Pattern pattern = Pattern.compile(regxExpress, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(content);
	    while(matcher.find()){
	        result = matcher.group();
	        break;
	    }
	    return result;
	}
	
	/**
	 * <p>정규식과 일치하는 문자배열 가져오기</p>
	 * 
	 * @param regxExpress
	 * @param content
	 * @return
	 * @since 2014-07-17
	 */
	public static String[] getMatches(String regxExpress, String content)
	{
		
		Pattern pattern = Pattern.compile(regxExpress, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(content);
	    ArrayList<String> machList = new ArrayList<String>();
	    while(matcher.find()){
	    	machList.add(matcher.group());
	    }
	    
	    String[] result = new String[machList.size()];
	    machList.toArray(result);
		
	    return result;
	}
	
	  
    
	
	/**
	 * <p>Ajax로 넘어오는 문자열 한글로 변환.</p>
	 * 
	 * @param val 문자열
	 * @return
	 * @throws Exception
	 */
	public static String toKorAjax(String str) throws Exception
	{
		return URLDecoder.decode(str, "UTF-8");
	}
	
    /**
     * <p>왼쪽 자리수 채우기.</p>
     * 
     * <pre>
     * UtilStr.leftPad(null, *, *)     = null
     * UtilStr.leftPad("", 3, 'z')     = "zzz"
     * UtilStr.leftPad("bat", 3, 'z')  = "bat"
     * UtilStr.leftPad("bat", 5, 'z')  = "zzbat"
     * UtilStr.leftPad("bat", 1, 'z')  = "bat"
     * UtilStr.leftPad("bat", -1, 'z') = "bat"
     * </pre>
     * @param str
     * @param size
     * @param padChar
     * @return
     */
    public static String lpad(String str, int size, char padChar) 
    {
    	if(str == null){
            return null;
        }
        int pads = size - str.length();
        if(pads <= 0){
            return str;
        }
        return _padding(pads, padChar).concat(str);
    }	
    
    private static String _padding(int repeat, char padChar) throws IndexOutOfBoundsException 
    {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }
    
    /**
     * <p>왼쪽 자리수 채우기.</p>
     * 
     * @param num
     * @param size
     * @param padChar
     * @return
     */
    public static String lpad(int num, int size, char padChar) 
    {
    	return lpad(String.valueOf(num), size, padChar);
    }


    
    /**
     * <p> String to HEX. </p>
     * 
     * @param s
     * @return
     */
    public static String strToHex(String s) 
    {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
          result += String.format("%02X ", (int) s.charAt(i));
        }

        return result;
    }
    
    /**
     * <p> 문자열 자르기. </p>
     * 
     * @param str
     * @param len
     * @return
     */
    public static String trimLength(String str, int len)
    {
    	return trimLength(str, len, "...");
    }
    
    /**
     * <p> 문자열 자르기. </p>
     * 
     * @param str
     * @param len
     * @param tail
     * @return
     */
    public static String trimLength(String str, int len, String tail)
    {
    	if(str == null) return "";
    	
        String result = "";
        if(str.length() > len){
        	result = str.substring(0, len) + tail;
        }else{
        	result = str;
        }
    	return result;
    }

    /**
     * <p> 문자열 바이트로 자르기.</p>
     * 
     * @param str
     * @param len
     * @return
     */
    public static String trimByte(String str, int len)
    {
    	return trimByte(str, len, "...");
    }
    
    /**
     * <p> 문자열 바이트로 자르기. </p>
     * 
     * @param str
     * @param len
     * @param tail
     * @return
     */
    public static String trimByte(String str, int len, String tail)
    {
    	if(str == null) return "";
    	
    	StringBuffer result = new StringBuffer(len);
    	
    	int total = 0;
    	char[] chars = str.toCharArray();
    	for(int i = 0; i < chars.length; i++){
    		total += String.valueOf(chars[i]).getBytes().length;
    		if(total > len) break;
    		result.append(chars[i]);
    	}
    	
    	if(total > len) return result.toString() + tail;
    	else return result.toString();
    }
    
    /**
     * <p>개행문자 변환.</p>
     * 
     * @param content 내용
     * @param toStr 개행문자 대체할 문자열
     * @return
     */
    public static String replaceNewLine(String content, String toStr)
    {
    	content = content.replaceAll(System.getProperty("line.separator"), toStr);
    	content = content.replaceAll("\n", toStr);
    	return content;
    }
    
    /**
     * 문자열 캐렉터셋 확인
     * @param value
     * @param charset
     * @return
     */
    public static boolean isCharset(String value, String charset)
    {
    	boolean result = false;
    	try{
    		byte bytes[] = value.getBytes(charset);
    		String value2 = new String(bytes, charset);
    		if(value.equals(value2)) result = true;
    	}catch(Exception err){}
    	return result;
    }
    
    /**
     * 문자열 캐렉터셋 변경
     * @param value
     * @param fromCharset
     * @param toCharset
     * @return
     */
    public static String convertCharset(String value, String fromCharset, String toCharset)
    {
    	String result = value;
    	try{
    		result = new String(value.getBytes(fromCharset), toCharset);
    	}catch(Exception err){}
    	return result;
    }
    
    /**
     * <p>디폴트 Charset 가져오기</p>
     * 
     * @return
     */
    public static String getDefaultChartset()
	{
		return Charset.defaultCharset().toString();
	}
    
//    /**
//     * <p>HTML decode</p>
//     * 
//     * str을 html 포맷으로 변환시킨다 즉 <는 &lt;, >는 &gt; 등으로 변환시킨다
//     */
//    public static String escapeHtml(String content)
//    {
//    	return StringEscapeUtils.escapeHtml(content);
//    }
//    
//    /**
//     * <p>HTML 인코딩</p>
//     * 
//     * escapeHtml 메소드와 역기능
//     */
//    public static String unescapeJava(String content)
//    {
//    	return StringEscapeUtils.unescapeJava(content);
//    }
//    

    
    /**
     * <p>GUID생성.</p>
     * 
     * @since 2015-02-12
     * @return
     */
    public static String getUUID()
    {
    	return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 일치하는 문자열 앞뒤로 설정된 길이만큼 짜른다.
     *
     * @param string
     * @param keyword
     * @param frontLength
     * @param backLength
     * @return
     */
	public static String truncate(String string, String keyword, int frontLength, int backLength) {

		//System.out.println(string);
		int string_length= string.length();
		int keyword_index =string.indexOf(keyword);
		
		int beginIndex= keyword_index-frontLength;
		int endIndex= keyword_index+keyword.length()+backLength;
		if(frontLength > keyword_index){
			beginIndex=0;
		}
		
		if(string_length < endIndex){
			endIndex =string_length;
		}
		
		string =string.substring(beginIndex, endIndex);
		
		//System.out.println(string);
		

		return string;
	}
    
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String ltrim(String s) {

		if(s ==null ){
			return null;
		}
	    int i = 0;
	    while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
	        i++;
	    }
	    return s.substring(i);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String rtrim(String s) {
		if(s ==null ){
			return null;
		}
	    int i = s.length()-1;
	    while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
	        i--;
	    }
	    return s.substring(0,i+1);
	}    
	
	/**
	 * text ->html
	 * @param s
	 * @return
	 */
	public static String textToHtml(String s) {
	    StringBuilder builder = new StringBuilder();
	    boolean previousWasASpace = false;
	    for( char c : s.toCharArray() ) {
	        if( c == ' ' ) {
	            if( previousWasASpace ) {
	                builder.append("&nbsp;");
	                previousWasASpace = false;
	                continue;
	            }
	            previousWasASpace = true;
	        } else {
	            previousWasASpace = false;
	        }
	        switch(c) {
	            case '<': builder.append("&lt;"); break;
	            case '>': builder.append("&gt;"); break;
	            case '&': builder.append("&amp;"); break;
	            case '"': builder.append("&quot;"); break;
	            case '\'': builder.append("&apos;"); break;
	            case '\n': builder.append("<br>"); break;
	            // We need Tab support here, because we print StackTraces as HTML
	            case '\t': builder.append("&nbsp; &nbsp; &nbsp;"); break;  
	            default:
	                if( c < 128 ) {
	                    builder.append(c);
	                } else {
	                    builder.append("&#").append((int)c).append(";");
	                }    
	        }
	    }
	    return builder.toString();
	}

	
	/**
	 * html -> text
	 * @param s
	 * @return
	 */
	public static String htmlToText(String s) {
		
		if(s ==null)  return null;		
		
		return s.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
		
	}
	

	/**
	 * ' -> &apos;
	 * @param s
	 * @return
	 */
	public static String escapeApos(String s) {

		if(s ==null)  return null;	
		
	    return s.replaceAll("'", "&apos;");
	}

	
	/**
	 *  &apos; -> '
	 * @param s
	 * @return
	 */
	public static String unEscapeApos(String s) {
		
		if(s ==null)  return null;		
		
		return s.replaceAll("&apos;", "'" );
		
	}
	
	
	/**
	 * 모든 HTML 태그를 제거하고 반환한다.
	 * 
	 * @param html
	 * @throws Exception  
	 */
	public static String removeHtmlTag(String html)  {
		return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
	}	
	
	/**
	 * 천단위 콤마
	 * @param obj
	 * @return
	 */
	public static String  toMoney( Object obj){
		
		if(obj == null){
			return null;
		}

		return NumberFormat.getInstance().format(obj);
	}
	
}
