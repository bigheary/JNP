package urls;

import java.io.IOException;
import java.io.*;
import java.net.URL;

public class SourceViewer {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			URL u = new URL("http://www.lolcats.com");
			InputStream in = u.openStream();
			in = new BufferedInputStream(in);
			Reader r = new InputStreamReader(in);
			int c ;
			while((c = in.read())!= -1)	
				System.out.print(c);
			in.close();
		}catch(IOException ex){
			System.err.println(ex);
		}
	}

}
