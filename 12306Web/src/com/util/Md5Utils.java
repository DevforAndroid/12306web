package com.util;

import java.security.MessageDigest;
/*
 * 功能：M5密码加密实现类
 *  
 */
public class Md5Utils {

	public final static String md5(String s) {
		//转换后存放到数据库USER表中密码字段使用的字符
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			byte[] btInput = s.getBytes();
			//使用Java的MessageDigest类创建一个M5的对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			//生成新的MD5字节数组
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			//完成MD5算法的计算步骤
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
