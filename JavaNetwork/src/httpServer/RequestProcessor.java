package httpServer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;
import java.util.logging.Logger;

public class RequestProcessor implements Runnable{
	private static Logger logger = Logger.getLogger(RequestProcessor.class.getCanonicalName());
	
	private File rootDirectory;
	private String indexFileName = "index.html";
	private Socket connection;
	
	public RequestProcessor(String indexFileName, File rootDirectory, Socket connection){
		if(rootDirectory.isFile()){
			throw new IllegalArgumentException(
					"rootDirectory must be a directory, not a file");
		}
		try {
			this.rootDirectory = rootDirectory.getCanonicalFile();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(indexFileName != null) this.indexFileName = indexFileName;
		this.connection = connection;
	}
	@Override
	public void run(){
		String root = rootDirectory.getPath();
		try {
			OutputStream raw = new BufferedOutputStream(
					connection.getOutputStream());
			Writer out = new OutputStreamWriter(raw);
			Reader in = new InputStreamReader(
					new BufferedInputStream(
					connection.getInputStream()),"US-ASCII");
			StringBuilder sb = new StringBuilder();
			while(true){
				int c = in.read();
				if(c == '\r' || c == '\n') break;
				sb.append((char)c);
			}
			
			String get = sb.toString();
			
			String[] tokens = get.split("\\s+");
			String method = tokens[0];
			String version = "";
			if(method == "GET"){
				String fileName = tokens[1];
				if (fileName.endsWith("/")) {
					fileName += indexFileName;
				}
		        String contentType = 
		                URLConnection.getFileNameMap().getContentTypeFor(fileName);
				if (tokens.length > 2) {
					version = tokens[2];
				}
				File file = new File(rootDirectory, fileName.substring(1, fileName.length()));	
				
				if(file.canRead()
						&& file.getCanonicalPath().startsWith(root)){
					byte[] data = Files.readAllBytes(file.toPath());
			          if (version.startsWith("HTTP/")) { // send a MIME header
			              sendHeader(out, "HTTP/1.0 200 OK", contentType, data.length);
			            } 
					raw.write(data);
					raw.flush();
				}
				else{
					//can not read
					String body = new StringBuilder("<html>\r\n")
					.append("<HEAD><TITLE>File Not Found</TITLE>\r\n")
					.append("</HEAD>\r\n")
					.append("<BODY>")
					.append("<H1>HTTP Error 404: File Not Found</H1>\r\n")
					.append("</BODY></HTML>\r\n")
					.toString();
					if(version.startsWith("HTTP/")){
						
					}
					out.write(body);
					out.flush();
				}
			}
			else {
				//is not GET
				String body = new StringBuilder("<HTML>\r\n")
	            .append("<HEAD><TITLE>Not Implemented</TITLE>\r\n")
	            .append("</HEAD>\r\n")
	            .append("<BODY>")
	            .append("<H1>HTTP Error 501: Not Implemented</H1>\r\n")
	            .append("</BODY></HTML>\r\n").toString();
	        if (version.startsWith("HTTP/")) { // send a MIME header
	          sendHeader(out, "HTTP/1.0 501 Not Implemented", 
	                    "text/html; charset=utf-8", body.length());
	        }
	        out.write(body);
	        out.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				connection.close();
			} catch (IOException e) {
				// TODO: handle exception
			}
		}
	}
	
	  private void sendHeader(Writer out, String responseCode,
		      String contentType, int length)
		      throws IOException {
		    out.write(responseCode + "\r\n");
		    Date now = new Date();
		    out.write("Date: " + now + "\r\n");
		    out.write("Server: JHTTP 2.0\r\n");
		    out.write("Content-length: " + length + "\r\n");
		    out.write("Content-type: " + contentType + "\r\n\r\n");
		    out.flush();
		  }
	

}
