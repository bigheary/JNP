package internetAddress;

import java.net.*;
import java.util.*;

public class InterfaceLister {

	public static void main(String[] args) throws SocketException{
		// TODO Auto-generated method stub
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while(interfaces.hasMoreElements()){
			NetworkInterface in = interfaces.nextElement();
			System.out.print(in.getDisplayName());
			System.out.print("#");
			System.out.println(in);
		}
		

	}

}
