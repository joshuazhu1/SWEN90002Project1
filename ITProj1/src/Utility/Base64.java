package Utility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Base64 {	
	/** *//**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException  
     */
	public static String EncoderBase64(String plainText){
    	BASE64Encoder encode = new BASE64Encoder();
		System.out.println("plainText: " +plainText);
        String cipher = encode.encode(plainText.getBytes());

        System.out.println("base64: "+ cipher);
		return cipher;
    }
	
    
    /** *//**判断用户密码是否正确
     * @param newpasswd  用户输入的密码
     * @param oldpasswd  数据库中存储的密码－－用户密码的摘要
     * @return
     */
	 public static String DecoderBase64(String cipher){
	    	BASE64Decoder decode = new BASE64Decoder();
	    	String result= null;
	    	//System.out.println("cipher: " +cipher);
		    byte[] b;
			try {
				b = decode.decodeBuffer(cipher);
			
		    result = new String(b);
		    //System.out.println("result: "+ result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		    return result;
	    }
    
}
