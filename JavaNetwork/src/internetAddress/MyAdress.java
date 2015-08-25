package internetAddress;

import java.net.*;

public class MyAdress {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		InetAddress address = InetAddress.getLocalHost();
		System.out.println(address);
		String name = address.getHostName();
		System.out.println(name);
		}catch(UnknownHostException ex){
			System.out.println("could not find this computer's address");
		}
	}

}
