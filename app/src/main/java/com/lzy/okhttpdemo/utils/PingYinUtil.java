package com.lzy.okhttpdemo.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <pre>
 *     desc  : 拼音管理类 ，需要pinyin4j包
 * </pre>
 */

public class PingYinUtil {

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     * 
     * @param inputString
     * @return
     */
    public static String getPinYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";

        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output += temp[0];
                } else
                    output += Character.toString(input[i]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
        	LogUtil.i("tag", "getPinYin exception");
            e.printStackTrace();
        }
        return output;
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     * 
     * @param chines
     *            汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines) {
        String pinyinName = "";
//        chines = chines.replaceAll("[\" `~!@#$%^*()+=|_{}\\[\\]:;',//[//].<>/?~！!@#￥%……&＆*（）——+|【】‘；：”“’。，、？?]", "").trim();
        LogUtil.i("pinyin", chines);
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                	String[] bufstr = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                	if (bufstr!=null&&bufstr.length>0) {
                		pinyinName += bufstr[0].charAt(0);
					}
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                	LogUtil.i("tag", "converterToFirstSpell BadHanyuPinyinOutputFormatCombination");
                	e.printStackTrace();
                }catch (Exception e) {
                	LogUtil.i("tag", "converterToFirstSpell exception");
                	e.printStackTrace();
				}
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    // 将汉字转换为全拼
    public static String getPingYin(String src) {

        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else
                    t4 += Character.toString(t1[i]);
            }
            // System.out.println(t4);
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
        	LogUtil.i("tag", "getPingYin exception");
            e1.printStackTrace();
        }
        return t4;
    }

    public static String LowerToUpperChange(String str) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isLowerCase(c)) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }
            }
        }

        return sb.toString();
    }
}
