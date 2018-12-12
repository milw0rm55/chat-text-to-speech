package Chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Emisor {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		voce.SpeechInterface.synthesize("hello World");
		
		try {
			InetSocketAddress addr2 = new InetSocketAddress("localhost", 5556);
			DatagramSocket datagramSocket1 = new DatagramSocket(addr2);
			DatagramSocket datagramSocket = new DatagramSocket();
			System.out.println("Elige Nombre");
			String nombre = sc.nextLine();
			boolean bucle = true;
			voce.SpeechInterface.init("../../../lib", true, false, "", "");
			while (bucle == true){
				byte[] respuesta = new byte[1024];
				String mensaje = nombre + " says "+" : "+ sc.nextLine();
				InetAddress addr = InetAddress.getByName("localhost");
				DatagramPacket datagrama2 = new DatagramPacket(respuesta, 1024);
				DatagramPacket datagrama = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, addr, 5555);
				datagramSocket.send(datagrama);
				datagramSocket1.receive(datagrama2);
				System.out.println(new String(respuesta));
				voce.SpeechInterface.synthesize(new String(respuesta));
		        try { Thread.sleep(100000); } catch(Exception ex) {}
		        voce.SpeechInterface.destroy();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}