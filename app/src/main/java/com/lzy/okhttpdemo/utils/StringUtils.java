package com.lzy.okhttpdemo.utils;


/**
 * <pre>
 *     desc  : 字符串相关工具类
 * </pre>
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }
    
 // 去小数点及多余的零
 	public static String cutZero(String s) {
 		if (s.indexOf(".") > 0) {
 			// 正则表达
 			s = s.replaceAll("0+?$", "");// 去掉后面无用的零
 			s = s.replaceAll("[.]$", "");// 如小数点后面全是零则去掉小数点
 		}
 		return s;
 	}

 	// 去零保证小数点后两位，固定传入六位小数
 	public static String cutZeroLeastTwo(String s) {
 		for (int i = 0; i < 4; i++) {
 			if (s.lastIndexOf("0") == s.length() - 1) {
 				s = s.substring(0, s.length() - 1);
 			} else {
 				break;
 			}
 		}
 		return s;
 	}

 	// 保证小数点后两位
 	public static String cutZeroLeastTwo2(String s) {
 		if (s.indexOf(".") > 0) {
 			s = s.replaceAll("0+?$", "");// 去掉后面无用的零
 			if (s.indexOf(".") == s.length() - 1) {
 				return s + "00";
 			} else if (s.indexOf(".") == s.length() - 2) {
 				return s + "0";
 			} else {
 				return s;
 			}
 		} else {
 			return s + ".00";
 		}
 	}

 	// 每三位数字前加“，”
 	public static String addCommaBeforePoint(String s) {
 		// 将传进数字反转
 		if ( s == null||"null".equals(s)||"".equals(s)) {
 			return "";
 		}
 		String minus;
 		if (s.contains("-")) {
 			minus = "-";
 		} else {
 			minus = "";
 		}
 		s = s.replace("-", "");
 		if (s.contains(".")) {

 			String reverseStr = new StringBuilder(
 					s.substring(0, s.indexOf("."))).reverse().toString();
 			String strTemp = "";
 			for (int i = 0; i < reverseStr.length(); i++) {
 				if (i * 3 + 3 > reverseStr.length()) {
 					strTemp += reverseStr.substring(i * 3, reverseStr.length());
 					break;
 				}
 				strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
 			}
 			// 将[789,456,] 中最后一个[,]去除
 			if (strTemp.endsWith(",")) {
 				strTemp = strTemp.substring(0, strTemp.length() - 1);
 			}
 			// 将数字重新反转
 			String resultStr = new StringBuilder(strTemp).reverse().toString();
 			return minus + resultStr + s.substring(s.indexOf("."), s.length());
 		} else {
 			String reverseStr = new StringBuilder(s).reverse().toString();
 			String strTemp = "";
 			for (int i = 0; i < reverseStr.length(); i++) {
 				if (i * 3 + 3 > reverseStr.length()) {
 					strTemp += reverseStr.substring(i * 3, reverseStr.length());
 					break;
 				}
 				strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
 			}
 			// 将[789,456,] 中最后一个[,]去除
 			if (strTemp.endsWith(",")) {
 				strTemp = strTemp.substring(0, strTemp.length() - 1);
 			}
 			// 将数字重新反转
 			String resultStr = new StringBuilder(strTemp).reverse().toString();
 			return minus + resultStr;
 		}
 	}

 	
 	public static String transferXMLSpecial(String str) {
 		str = str.replaceAll("<", "&lt;");
 		str = str.replaceAll(">", "&gt;");
 		str = str.replaceAll("&", "&amp;");
 		str = str.replaceAll("'", "&apos;");
 		str = str.replaceAll("\"", "&quot;");
 		return str;
 	}

 	public static String transferXMLSpecialBack(String str) {
 		str = str.replaceAll("&lt;", "<");
 		str = str.replaceAll("&gt;", ">");
 		str = str.replaceAll("&amp;", "&");
 		str = str.replaceAll("&apos;", "'");
 		str = str.replaceAll("&quot;", "\"");
 		return str;
 	}
}