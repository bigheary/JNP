package internetAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ReverseTest {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		InetAddress address = InetAddress.getByName("208.201.239.100");
		System.out.println(address);
		String name = address.getHostAddress();
		System.out.println(name);
		}catch(UnknownHostException ex){
			System.out.println("could not find this computer's address");
		}
	}

}
