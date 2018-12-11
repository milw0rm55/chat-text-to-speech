package Hundir_la_flota;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketStream {
	private String server;
	private int puerto;
	private ServerSocket serverSocket;
	private InputStream in;
	private OutputStream out;
	private Socket comSocket = null;

	public ServerSocketStream(String server, int puerto, boolean inCon) throws IOException {
		this.server = server;
		this.puerto = puerto;
		serverSocket = new ServerSocket(puerto);
		if (inCon) {
			try {
				this.connect();
			} catch (IOException e) {
				System.err.println("Fallo al bindear al puerto: " + e);
				System.exit(-1);
			}
		}
	}

	public void connect() throws IOException {
		//this.bindCon();
		this.accept();
	}

	public void sendMsg(String msg) throws IOException {
		if (this.out != null) {
			out.write(msg.getBytes());
		}
	}

	public void accept() throws IOException {
		if (this.comSocket == null) {
			comSocket = serverSocket.accept();
			in = comSocket.getInputStream();
			out = comSocket.getOutputStream();
		}
	}

	public void connectAndSend(String msg) throws IOException {
		this.connect();
		this.sendMsg(msg);
	}

	public String listen() throws IOException {
		byte[] mensaje = new byte[1024];
		in.read(mensaje);
		return new String(mensaje);
	}

	public String connectAndListen() throws IOException {
		this.connect();
		return this.listen();
	}

	public void close() throws IOException {
		this.in.close();
		this.out.close();
		this.serverSocket.close();
	}

	private void bindCon() throws IOException {
		serverSocket.bind(new InetSocketAddress(this.server, this.puerto));
	}

}

