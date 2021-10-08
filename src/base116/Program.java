package base116;

import com.stenway.sml.SmlDocument;
import java.io.IOException;

public class Program {
	public static void main(String[] args) throws IOException {
		
		// save
		
		byte[] bytes = Utils.getBytesFromAscii("Hello world!");
		String base116Str = Base116.encode(bytes);
		String base85Str = Base85.encode(bytes);
		
		SmlDocument document = new SmlDocument("BinaryToTextTest");
		document.getRoot().addAttribute("Base116Value", base116Str);
		document.getRoot().addAttribute("Base85Value", base85Str);
		
		document.save("BinaryToTextTest.sml");
		
		// load
		
		SmlDocument loadedDocument = SmlDocument.load("BinaryToTextTest.sml");
		byte[] loadedBytesB116 = Base116.decode(loadedDocument.getRoot().getString("Base116Value"));
		byte[] loadedBytesB85 = Base85.decode(loadedDocument.getRoot().getString("Base85Value"));
		
		Utils.assureEqual(bytes, loadedBytesB116);
		Utils.assureEqual(bytes, loadedBytesB85);
		
		System.out.println(base116Str +" = "+Utils.getAsciiFromBytes(loadedBytesB116));
	}
	
}
