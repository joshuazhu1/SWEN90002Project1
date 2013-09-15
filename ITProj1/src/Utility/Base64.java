package Utility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Base64 {	
	/** *//**����MD5���м���
     * @param str  �����ܵ��ַ���
     * @return  ���ܺ���ַ���
     * @throws NoSuchAlgorithmException  û�����ֲ�����ϢժҪ���㷨
     * @throws UnsupportedEncodingException  
     */
	public static String EncoderBase64(String plainText){
    	BASE64Encoder encode = new BASE64Encoder();
		System.out.println("plainText: " +plainText);
        String cipher = encode.encode(plainText.getBytes());

        System.out.println("base64: "+ cipher);
		return cipher;
    }
	
    
    /** *//**�ж��û������Ƿ���ȷ
     * @param newpasswd  �û����������
     * @param oldpasswd  ���ݿ��д洢�����룭���û������ժҪ
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
