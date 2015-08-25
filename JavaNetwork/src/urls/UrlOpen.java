package urls;

import java.io.*;
import java.net.*;

public class UrlOpen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			URL u = new URL("http://www.lolcats.com");
			InputStream in = u.openStream();
			int c ;
			while((c = in.read())!= -1)	
				System.out.write(c);
			in.close();
		}catch(IOException ex){
			System.err.println(ex);
		}
	}

}
