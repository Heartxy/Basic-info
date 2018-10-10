package com.oracle.jsp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
	//对密码进行MD5加密
	//全局数组
	private final static String [] strDigits={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"
	};
	//构造函数
	public MD5() {
		// TODO Auto-generated constructor stub
	}
	
	//返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte){
		int iRet=bByte;
		//使iRet不能超过256
		if(iRet<0){
			iRet+=256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}
	
	//返回形式只为数字
	@SuppressWarnings("unused")
	private static String byteToNum(byte bByte){
		int iRet = bByte;
		if (iRet < 0){
			iRet += 256;
		}
		return String.valueOf(iRet);
	}
	
	//转换字节数组为16进制子串
	private static String byteToString(byte[] bByte){
		//StringBuffer为字符串变量，可以后续变化
		StringBuffer sBuffer = new StringBuffer();
		for(int i = 0;i < bByte.length; i++){
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}
	
	public static String GetMD5Code(String strObj){
		
		//
		String resultString = null ;
		try{
			resultString = new String(strObj);
			//MessageDigest类为应用程序提供信息摘要算法的功能，比如说这里用到的MD算法
			//信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值
			MessageDigest md = MessageDigest.getInstance("MD5");
			//md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		}
		catch(NoSuchAlgorithmException ex){
			ex.printStackTrace();
		}
		return resultString;
	}
}
