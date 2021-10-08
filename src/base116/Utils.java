package base116;

import java.util.Random;

public class Utils {
	public static byte[] getRandomBytes(int maxLength) {
		Random rand = new Random();
		int numBytes = rand.nextInt(maxLength);
		byte[] bytes = new byte[numBytes];
		for (int i=0; i<numBytes; i++) {
			bytes[i] = (byte)rand.nextInt(256);
		}
		return bytes;
	}
	
	public static byte[] getBytes(int... values) {
		byte[] result = new byte[values.length];
		for (int i=0; i<values.length; i++) {
			result[i] = (byte)values[i];
		}
		return result;
	}
	
	public static byte[] getBytesFromAscii(String asciiText) {
		byte[] result = new byte[asciiText.length()];
		for (int i=0; i<asciiText.length(); i++) {
			result[i] = (byte)asciiText.charAt(i);
		}
		return result;
	}
	
	public static String getAsciiFromBytes(byte[] bytes) {
		String result = "";
		for (int i=0; i<bytes.length; i++) {
			result += (char)bytes[i];
		}
		return result;
	}
	
	public static void assureEqual(byte[] bytes1, byte[] bytes2) {
		if (bytes1.length != bytes2.length) {
			throw new RuntimeException("Length "+bytes1.length+" is not equal "+bytes2.length);
		}
		for (int i=0; i<bytes1.length; i++) {
			if (bytes1[i] != bytes2[i]) {
				throw new RuntimeException("Mismatch at index "+i+", "+bytes1[i]+" is not equal "+bytes2[i]);
			}
		}
	}
}
