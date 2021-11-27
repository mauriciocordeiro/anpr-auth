package br.org.mcord.alpr4j.security;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class Hash {
	
	public static final String MD5 		= "MD5";	
	public static final String SHA256 	= "SHA256";	
	
	private static Hash instance = new Hash();
	
	private Hash() { }
	
	public static Hash getInstance() {
		return instance;
	}
	
	public static String generateMD5(String source) {
		return getInstance().generateHash(source, MD5);
	}
	
	public static String generateSHA256(String source) {
		return getInstance().generateHash(source, SHA256);
	}
	
	/**
	 * @param source {@link String}
	 * @param algorithm {@link String} {@link MessageDigestAlgorithms} like
	 * @return hash string {@link String}
	 */
	private String generateHash(String source, String algorithm) {
		String result = null;
        try {
        	MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] bytes = digest.digest(source.getBytes("UTF-8"));
            return bytesToHex(bytes);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
	}	
	
	private String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }

}
