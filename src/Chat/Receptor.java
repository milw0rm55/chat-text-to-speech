package Chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Receptor {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
		//	System.out.println("Creando el socket datagram");
			InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
			DatagramSocket datagramSocket = new DatagramSocket(addr);
			System.out.println("Elige Nombre");
			String name = sc.nextLine();
			boolean bucle = true;
			voce.SpeechInterface.init("../../../lib", true, false, "", "");
			while(bucle == true) {
				byte[] mensaje = new byte[1024];
				DatagramPacket datagrama1 = new DatagramPacket(mensaje, 1024);
				datagramSocket.receive(datagrama1);
				System.out.println(new String(mensaje));
				System.out.println("Respuesta");
				String respuesta = name +" says " + " : "+ sc.nextLine();;
				InetAddress addr2 = InetAddress.getByName("localhost");
				DatagramPacket datagrama2 = new DatagramPacket(respuesta.getBytes(), respuesta.getBytes().length, addr2, 5556);
				datagramSocket.send(datagrama2);
				System.out.println("Mensaje enviado");
				voce.SpeechInterface.synthesize(new String(mensaje));
		        try { Thread.sleep(100000); } catch(Exception ex) {}
		        voce.SpeechInterface.destroy();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}