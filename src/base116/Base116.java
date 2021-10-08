package base116;

public class Base116 {
	public static int[] Symbol = new int[] {
			0x01,
			0x02,
			0x03,
			0x04,
			0x05,
			0x06,
			0x07,
			0x0e,
			0x0f,
			0x10,
			0x11,
			0x12,
			0x13,
			0x14,
			0x15,
			0x16,
			0x17,
			0x18,
			0x19,
			0x1a,
			0x1b,
			0x1c,
			0x1d,
			0x1e,
			0x1f,
			0x21,
			0x24,
			0x25,
			0x26,
			0x27,
			0x28,
			0x29,
			0x2a,
			0x2b,
			0x2c,
			0x2d,
			0x2e,
			0x2f,
			0x30,
			0x31,
			0x32,
			0x33,
			0x34,
			0x35,
			0x36,
			0x37,
			0x38,
			0x39,
			0x3a,
			0x3b,
			0x3c,
			0x3d,
			0x3e,
			0x3f,
			0x40,
			0x41,
			0x42,
			0x43,
			0x44,
			0x45,
			0x46,
			0x47,
			0x48,
			0x49,
			0x4a,
			0x4b,
			0x4c,
			0x4d,
			0x4e,
			0x4f,
			0x50,
			0x51,
			0x52,
			0x53,
			0x54,
			0x55,
			0x56,
			0x57,
			0x58,
			0x59,
			0x5a,
			0x5b,
			0x5c,
			0x5d,
			0x5e,
			0x5f,
			0x60,
			0x61,
			0x62,
			0x63,
			0x64,
			0x65,
			0x66,
			0x67,
			0x68,
			0x69,
			0x6a,
			0x6b,
			0x6c,
			0x6d,
			0x6e,
			0x6f,
			0x70,
			0x71,
			0x72,
			0x73,
			0x74,
			0x75,
			0x76,
			0x77,
			0x78,
			0x79,
			0x7a,
			0x7b,
			0x7d,
			0x7e
		};
	
	public static long[] Index = new long[] {
			-1,
			0,
			1,
			2,
			3,
			4,
			5,
			6,
			-1,
			-1,
			-1,
			-1,
			-1,
			-1,
			7,
			8,
			9,
			10,
			11,
			12,
			13,
			14,
			15,
			16,
			17,
			18,
			19,
			20,
			21,
			22,
			23,
			24,
			-1,
			25,
			-1,
			-1,
			26,
			27,
			28,
			29,
			30,
			31,
			32,
			33,
			34,
			35,
			36,
			37,
			38,
			39,
			40,
			41,
			42,
			43,
			44,
			45,
			46,
			47,
			48,
			49,
			50,
			51,
			52,
			53,
			54,
			55,
			56,
			57,
			58,
			59,
			60,
			61,
			62,
			63,
			64,
			65,
			66,
			67,
			68,
			69,
			70,
			71,
			72,
			73,
			74,
			75,
			76,
			77,
			78,
			79,
			80,
			81,
			82,
			83,
			84,
			85,
			86,
			87,
			88,
			89,
			90,
			91,
			92,
			93,
			94,
			95,
			96,
			97,
			98,
			99,
			100,
			101,
			102,
			103,
			104,
			105,
			106,
			107,
			108,
			109,
			110,
			111,
			112,
			113,
			-1,
			114,
			115,
			-1
		};
	
	public static String encode(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		sb.append("B116|");
		
		int blocks = bytes.length/6;
		for (int i=0; i<blocks; i++) {
			long b1 = bytes[i*6] & 0xff;
			long b2 = bytes[i*6+1] & 0xff;
			long b3 = bytes[i*6+2] & 0xff;
			long b4 = bytes[i*6+3] & 0xff;
			long b5 = bytes[i*6+4] & 0xff;
			long b6 = bytes[i*6+5] & 0xff;
			long l = ( b6 << 40 ) + ( b5 << 32 ) + ( b4 << 24 ) + ( b3 << 16 ) + ( b2 << 8 ) + b1;
			
			long c1 = l % 116;
			l /= 116;
			long c2 = l % 116;
			l /= 116;
			long c3 = l % 116;
			l /= 116;
			long c4 = l % 116;
			l /= 116;
			long c5 = l % 116;
			l /= 116;
			long c6 = l % 116;
			l /= 116;
			long c7 = l;
			sb.append((char)Symbol[(int)c1]);
			sb.append((char)Symbol[(int)c2]);
			sb.append((char)Symbol[(int)c3]);
			sb.append((char)Symbol[(int)c4]);
			sb.append((char)Symbol[(int)c5]);
			sb.append((char)Symbol[(int)c6]);
			sb.append((char)Symbol[(int)c7]);
		}
		int rest = bytes.length % 6;
		if (rest != 0) {
			long b1 = 0;
			long b2 = 0;
			long b3 = 0;
			long b4 = 0;
			long b5 = 0;
			
			int startIndex = blocks*6;
			b1 = bytes[startIndex] & 0xff;
			if (rest>=2) b2 = bytes[startIndex+1] & 0xff;
			if (rest>=3) b3 = bytes[startIndex+2] & 0xff;
			if (rest>=4) b4 = bytes[startIndex+3] & 0xff;
			if (rest==5) b5 = bytes[startIndex+4] & 0xff;
			long l = ( b5 << 32 ) + ( b4 << 24 ) + ( b3 << 16 ) + ( b2 << 8 ) + b1;
			
			long c1 = l % 116;
			l /= 116;
			long c2 = l % 116;
			l /= 116;
			long c3 = l % 116;
			l /= 116;
			long c4 = l % 116;
			l /= 116;
			long c5 = l % 116;
			l /= 116;
			long c6 = l % 116;
			
			sb.append((char)Symbol[(int)c1]);
			sb.append((char)Symbol[(int)c2]);
			if (rest>=2) sb.append((char)Symbol[(int)c3]);
			if (rest>=3) sb.append((char)Symbol[(int)c4]);
			if (rest>=4) sb.append((char)Symbol[(int)c5]);
			if (rest==5) sb.append((char)Symbol[(int)c6]);
		}
		sb.append('|');
		return sb.toString();
	}
	
	public static byte[] decode(String str) {
		if (!str.startsWith("B116|") || !str.endsWith("|")) {
			throw new Base116DecodingException("Invalid Base116 prefix/suffix");
		}
		String content = str.substring(5,str.length()-1);
		int rest = content.length() % 7;
		int missing = 0;
		if (rest > 0) {
			if (rest == 1) {
				throw new Base116DecodingException("Invalid Base116 length");
			}
			missing = 7 - rest;
			for (int i=0; i<missing; i++) {
				content += (char)Symbol[0];
			}
		}
		int numBlocks = content.length() / 7;
		byte[] bytes = new byte[numBlocks*6-missing];
		int numFullBlocks = numBlocks;
		if (missing > 0) {
			numFullBlocks -= 1;
		}
		for (int i=0; i<numFullBlocks; i++) {
			int s1 = content.charAt(i*7);
			int s2 = content.charAt(i*7+1);
			int s3 = content.charAt(i*7+2);
			int s4 = content.charAt(i*7+3);
			int s5 = content.charAt(i*7+4);
			int s6 = content.charAt(i*7+5);
			int s7 = content.charAt(i*7+6);
			if (s1 > 0x7e || s2 > 0x7e || s3 > 0x7e || s4 > 0x7e || s5 > 0x7e || s6 > 0x7e || s7 > 0x7e) {
				throw new Base116DecodingException("Invalid Base116 char");
			}
			long c1 = Index[s1];
			long c2 = Index[s2];
			long c3 = Index[s3];
			long c4 = Index[s4];
			long c5 = Index[s5];
			long c6 = Index[s6];
			long c7 = Index[s7];
			if (c1 < 0 || c2 < 0 || c3 < 0 || c4 < 0 || c5 < 0 || c6 < 0 || c7 < 0) {
				throw new Base116DecodingException("Invalid Base116 char");
			}
			
			long l = c7 * 116 * 116 * 116 * 116 * 116 * 116 + 
					c6 * 116 * 116 * 116 * 116 * 116 + 
					c5 * 116 * 116 * 116 * 116 + 
					c4 * 116 * 116 * 116 + 
					c3 * 116 * 116 + 
					c2 * 116 + 
					c1;
			
			long b1 = l & (0xFFL);
			long b2 = ( l & (0xFFL << 8) ) >> 8;
			long b3 = ( l & (0xFFL << 16) ) >> 16;
			long b4 = ( l & (0xFFL << 24) ) >> 24;
			long b5 = ( l & (0xFFL << 32) ) >> 32;
			long b6 = ( l & (0xFFL << 40) ) >> 40;
			
			bytes[i*6] = (byte)b1;
			bytes[i*6+1] = (byte)b2;
			bytes[i*6+2] = (byte)b3;
			bytes[i*6+3] = (byte)b4;
			bytes[i*6+4] = (byte)b5;
			bytes[i*6+5] = (byte)b6;
		}
		if (missing > 0) {
			int startIndex = numFullBlocks * 7;
			int s1 = content.charAt(startIndex);
			int s2 = content.charAt(startIndex+1);
			int s3 = content.charAt(startIndex+2);
			int s4 = content.charAt(startIndex+3);
			int s5 = content.charAt(startIndex+4);
			int s6 = content.charAt(startIndex+5);
			int s7 = content.charAt(startIndex+6);
			if (s1 > 0x7e || s2 > 0x7e || s3 > 0x7e || s4 > 0x7e || s5 > 0x7e || s6 > 0x7e || s7 > 0x7e) {
				throw new Base116DecodingException("Invalid Base116 char");
			}
			long c1 = Index[s1];
			long c2 = Index[s2];
			long c3 = Index[s3];
			long c4 = Index[s4];
			long c5 = Index[s5];
			long c6 = Index[s6];
			long c7 = Index[s7];
			if (c1 < 0 || c2 < 0 || c3 < 0 || c4 < 0 || c5 < 0 || c6 < 0 || c7 < 0) {
				throw new Base116DecodingException("Invalid Base116 char");
			}
			
			long l = c7 * 116 * 116 * 116 * 116 * 116 * 116 + 
					c6 * 116 * 116 * 116 * 116 * 116 + 
					c5 * 116 * 116 * 116 * 116 + 
					c4 * 116 * 116 * 116 + 
					c3 * 116 * 116 + 
					c2 * 116 + 
					c1;
			
			long b1 = l & (0xFFL);
			long b2 = ( l & (0xFFL << 8) ) >> 8;
			long b3 = ( l & (0xFFL << 16) ) >> 16;
			long b4 = ( l & (0xFFL << 24) ) >> 24;
			long b5 = ( l & (0xFFL << 32) ) >> 32;
			
			bytes[numFullBlocks*6] = (byte)b1;
			if (missing <= 4) bytes[numFullBlocks*6+1] = (byte)b2;
			if (missing <= 3) bytes[numFullBlocks*6+2] = (byte)b3;
			if (missing <= 2) bytes[numFullBlocks*6+3] = (byte)b4;
			if (missing <= 1) bytes[numFullBlocks*6+4] = (byte)b5;
		}
		return bytes;
	}
}
