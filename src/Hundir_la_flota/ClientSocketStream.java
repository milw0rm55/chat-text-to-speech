package Hundir_la_flota;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocketStream {
	private String server;
	private int puerto;
	private Socket clientSocket;
	private InputStream in;
	private OutputStream out;
	
	public ClientSocketStream(String server, int puerto,boolean inCon) {
		this.server = server;
		this.puerto = puerto;
		if(inCon) {
			try {
				this.connect();
			} catch (IOException e) {
				System.err.println("Fallo al bindear al puerto: "+e);
				System.exit(-1);
			}
		}
	}


	public void connect() throws IOException {
		this.bindCon();
		this.createStreams();
	}

	public void sendMsg(String msg) throws IOException {
		if(this.out != null) {
			out.write(msg.getBytes());
		}
	}
	
	public void connectAndSend(String msg) throws IOException {
		this.connect();
		this.sendMsg(msg);
	}
	
	public String listen() throws IOException {
		byte[] mensaje = new byte[1024];
		in.read(mensaje);
		String result = new String(mensaje).replaceFirst("\\s++$", "");
		return result;
	}
	
	public String connectAndListen() throws IOException {
		this.connect();
		return this.listen();
	}
	
	public void close() throws IOException {
		this.in.close();
		this.out.close();
		this.clientSocket.close();
	}
	
	private void bindCon() throws IOException {
		clientSocket = new Socket();
		clientSocket.connect(new InetSocketAddress(this.server, this.puerto));
	}
	
	private void createStreams() throws IOException {
		this.in = this.clientSocket.getInputStream();
		this.out = this.clientSocket.getOutputStream();
	}
	
}