package com.fulan.common.utils;


import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * <p class="detail">字符处理</p>
 *
 * @ClassName: StringUtil 
 *      
 */
public class StringUtil {
	/**
	 * <p class="detail">UUID</p>
	 * @return
	 */
	public static String uuid(){
		return UUID.randomUUID().toString();
	}
	/** 
	 * <p class="detail">将 1,2,3 返回为 List &lt;Integer&gt; </p>
	 *
	 * @param <b>ids 1,2,3</b>
	 * @return List<Integer>    获得id,List
	 * @throws 
	 */
	public static List<Integer> getIdListInt(String ids){
		return getSplitIntList(ids, ",");
	}
	
	/**
	 * <p class="detail">分割字符串返回int ArrayList</p>
	 * @param s       原字符串
	 * @param split  分隔符
	 * @return
	 */
	public static List<Integer> getSplitIntList(String s,String split){
		String[] ss = s.split(split);
		List<Integer> criteriaIdList = new ArrayList<Integer>();
		for (String y : ss) {
			criteriaIdList.add(Integer.parseInt(y));
		}
		return criteriaIdList;
	}
	
	/**
	 * <p class="detail">分割字符串返回字符串ArrayList</p>
	 * @param s       原字符串
	 * @param split  分隔符
	 * @return
	 */
	public static List<String> getSplitStringList(String s,String split){
		String[] ss = s.split(split);
		List<String> criteriaIdList = new ArrayList<String>();
		for (String y : ss) {
			criteriaIdList.add(y);
		}
		return criteriaIdList;
	}
	
	/**
	 * 
	 * <p class="detail"> 获得字符串的集合 </p>
	 * @param strings
	 * @return
	 */
	public static List<String> getStringList(String strings){
		return getSplitStringList(strings,",");
	}
	
	/**
	 * 判断输入的字符串是否为纯汉字
	 * 
	 * @param str 传入的字符串
	 * @return 如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 
	 * <p class="detail">md5 加密</p>
	 *
	 * @author <a href="mailto:niukun@wokejia.com ">牛坤</a>  
	 * @param s <b>需要加密的字符串</b>
	 * @return String    md5加密之后的字符串
	 * @throws
	 */
	public static final String md5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 
	 * <p class="detail">将如下格式String i = 1,2,3,4 通过传入分隔符 ,返回Integer数组</p>
	 *
	 * @param <b>这里参数</b>
	 * @return Integer[]   
	 * @throws
	 */
	public static Integer[] parseIntegerArray(String value,String split){
		Integer[] result = null;
		
		String[] ss = value.split(split);
		result = new Integer[ss.length];
		int i = 0;
		for (String s : ss) {
			result[i++] = Integer.parseInt(s);
		}
		return result;
	}
	
	/**
	 * <p class="detail">将中文字符转换为拼音出来</p>
	 * <pre>
	 * 		Pinyin finder = Pinyin.getInstance();
	 *		finder.setResource("中文字符");
	 *		System.out.println(finder.getSpelling());
	 *		System.out.println(finder.getSelling("英文字符Eng"));
	 *	</pre>
	 * @param chinaCode
	 * @return
	 */
	/*public static String pinyin(String chinaCode){
		return Pinyin.getInstance().getSelling(chinaCode);
	}*/
	
	/**
	 * 
	  * <p>方法说明:获取中文首字母</p>
	  * @param chineseChar
	  * @return String
	 */
	/*public static String getFirstLetterForChinese(String chineseChar){
		String pinyin = pinyin(chineseChar);
		return pinyin.substring(0, 1).toUpperCase();
	}*/
	
	/**
	 * 将str List转换为string字符串
	 * @param list
	 * @param regex
	 * @return
	 */
	public static String listToStr(List<String> list, String regex){
		StringBuffer stringBuffer = new StringBuffer("");
		for(String str : list){
			if(stringBuffer.length() > 0){
				stringBuffer.append(regex);
			}
			stringBuffer.append(str);
		}
		return stringBuffer.toString();
	}
	
	/*public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(Utils.random(1, 2, 0));
		}
		System.out.println("123-123:1,".split(",").length);
	}*/
	
	public static Integer parseInt(String s){
		try {
			if(s != null && s.trim().length() > 0){
				return Integer.parseInt(s.trim());
			}
		} catch (Exception e) {
		}
		return null;
	}
}
