package base116;

public class Base85 {
	public static String encode(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		sb.append("B85|");
		
		int blocks = bytes.length/4;
		for (int i=0; i<blocks; i++) {
			long b1 = bytes[i*4] & 0xff;
			long b2 = bytes[i*4+1] & 0xff;
			long b3 = bytes[i*4+2] & 0xff;
			long b4 = bytes[i*4+3] & 0xff;
			long l = ( b4 << 24 ) + ( b3 << 16 ) + ( b2 << 8 ) + b1;
			
			long c1 = l % 85;
			l /= 85;
			long c2 = l % 85;
			l /= 85;
			long c3 = l % 85;
			l /= 85;
			long c4 = l % 85;
			l /= 85;
			long c5 = l;
			sb.append((char)(c1+0x0026));
			sb.append((char)(c2+0x0026));
			sb.append((char)(c3+0x0026));
			sb.append((char)(c4+0x0026));
			sb.append((char)(c5+0x0026));
			
		}
		int rest = bytes.length % 4;
		if (rest != 0) {
			long b1 = 0;
			long b2 = 0;
			long b3 = 0;
			
			int startIndex = blocks*4;
			b1 = bytes[startIndex] & 0xff;
			if (rest>=2) b2 = bytes[startIndex+1] & 0xff;
			if (rest==3) b3 = bytes[startIndex+2] & 0xff;
			long l = ( b3 << 16 ) + ( b2 << 8 ) + b1;
			
			long c1 = l % 85;
			l /= 85;
			long c2 = l % 85;
			l /= 85;
			long c3 = l % 85;
			l /= 85;
			long c4 = l % 85;
			
			sb.append((char)(c1+0x0026));
			sb.append((char)(c2+0x0026));
			if (rest>=2) sb.append((char)(c3+0x0026));
			if (rest==3) sb.append((char)(c4+0x0026));
		}
		sb.append('|');
		return sb.toString();
	}
	
	public static byte[] decode(String str) {
		if (!str.startsWith("B85|") || !str.endsWith("|")) {
			throw new Base85DecodingException("Invalid Base85 prefix/suffix");
		}
		String content = str.substring(4,str.length()-1);
		int rest = content.length() % 5;
		int missing = 0;
		if (rest > 0) {
			if (rest == 1) {
				throw new Base85DecodingException("Invalid Base85 length");
			}
			missing = 5 - rest;
			for (int i=0; i<missing; i++) {
				content += (char)0x0026;
			}
		}
		int numBlocks = content.length() / 5;
		byte[] bytes = new byte[numBlocks*4-missing];
		int numFullBlocks = numBlocks;
		if (missing > 0) {
			numFullBlocks -= 1;
		}
		for (int i=0; i<numFullBlocks; i++) {
			int s1 = content.charAt(i*5);
			int s2 = content.charAt(i*5+1);
			int s3 = content.charAt(i*5+2);
			int s4 = content.charAt(i*5+3);
			int s5 = content.charAt(i*5+4);
			if (s1 > 0x7a || s2 > 0x7a || s3 > 0x7a || s4 > 0x7a || s5 > 0x7a ||
					s1 < 0x26 || s2 < 0x26 || s3 < 0x26 || s4 < 0x26 || s5 < 0x26) {
				throw new Base116DecodingException("Invalid Base85 char");
			}
			long c1 = s1-0x0026;
			long c2 = s2-0x0026;
			long c3 = s3-0x0026;
			long c4 = s4-0x0026;
			long c5 = s5-0x0026;
			
			long l = c5 * 85 * 85 * 85 * 85 + 
					c4 * 85 * 85 * 85 + 
					c3 * 85 * 85 + 
					c2 * 85 + 
					c1;
			
			long b1 = l & (0xFFL);
			long b2 = ( l & (0xFFL << 8) ) >> 8;
			long b3 = ( l & (0xFFL << 16) ) >> 16;
			long b4 = ( l & (0xFFL << 24) ) >> 24;
			
			bytes[i*4] = (byte)b1;
			bytes[i*4+1] = (byte)b2;
			bytes[i*4+2] = (byte)b3;
			bytes[i*4+3] = (byte)b4;
		}
		if (missing > 0) {
			int startIndex = numFullBlocks * 5;
			int s1 = content.charAt(startIndex);
			int s2 = content.charAt(startIndex+1);
			int s3 = content.charAt(startIndex+2);
			int s4 = content.charAt(startIndex+3);
			int s5 = content.charAt(startIndex+4);
			if (s1 > 0x7a || s2 > 0x7a || s3 > 0x7a || s4 > 0x7a || s5 > 0x7a ||
					s1 < 0x26 || s2 < 0x26 || s3 < 0x26 || s4 < 0x26 || s5 < 0x26) {
				throw new Base116DecodingException("Invalid Base85 char");
			}
			long c1 = s1-0x0026;
			long c2 = s2-0x0026;
			long c3 = s3-0x0026;
			long c4 = s4-0x0026;
			long c5 = s5-0x0026;
			
			long l = c5 * 85 * 85 * 85 * 85 + 
					c4 * 85 * 85 * 85 + 
					c3 * 85 * 85 + 
					c2 * 85 + 
					c1;
			
			long b1 = l & (0xFFL);
			long b2 = ( l & (0xFFL << 8) ) >> 8;
			long b3 = ( l & (0xFFL << 16) ) >> 16;
			
			bytes[numFullBlocks*4] = (byte)b1;
			if (missing <= 2) bytes[numFullBlocks*4+1] = (byte)b2;
			if (missing <= 1) bytes[numFullBlocks*4+2] = (byte)b3;
		}
		return bytes;
	}
}
