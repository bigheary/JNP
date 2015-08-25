package urls;

import java.io.*;
import java.net.*;

public class SourceViewer2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			URL u = new URL("http://www.lolcats.com");
			
			URLConnection uc = u.openConnection();
			try (InputStream raw = uc.getInputStream()){
				InputStream buffer = new BufferedInputStream(raw);
				Reader reader = new InputStreamReader(buffer);
				int c;
				while((c = reader.read()) != -1){
					System.out.print((char)c);
				}
			} catch (MalformedURLException e) {
				// TODO: handle exception
				System.err.println(e);
			}
	}catch(Exception e){
		System.err.println(e);
	}
}
}

