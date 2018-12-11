package Hundir_la_flota;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import javafx.util.Pair;

public class Cliente {

	private static enum RESPUESTA {
		ATAQUE, DEFENSA
	};

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Properties pr = null;
		try {
			pr = Propiedades.getProp();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ClientSocketStream socket = new ClientSocketStream(pr.getProperty("server"),
				Integer.parseInt(pr.getProperty("puerto")), true);
		Tablero tl = new Tablero(Integer.parseInt(pr.getProperty("ancho")), Integer.parseInt(pr.getProperty("alto")),
				Integer.parseInt(pr.getProperty("nBarcos")), true);
		int nBarcos = Integer.parseInt(pr.getProperty("nBarcos"));
		while (nBarcos > 0) {
			System.out.println("Este es tu tablero \n" + tl);
			System.out
					.println("Introduce cordenadas para atacar al rival, las cordenadas deben seguir el formato x-x ");
			Pair<Integer, Integer> cordenadas = null;
			try {
				socket.sendMsg(sc.nextLine());
				escucharRespuesta(RESPUESTA.ATAQUE, socket);
				cordenadas = escucharRespuesta(RESPUESTA.DEFENSA, socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Te atacaron en " + cordenadas.getKey() + "-" + cordenadas.getValue());
			try {
				if (tl.hit(cordenadas)) {
					System.out.println("Te han golpeado un barco");
					nBarcos--;
					System.out.println("Te quedan " + nBarcos);
					if (nBarcos > 0) {
						socket.sendMsg("Dado");
					} else {
						socket.sendMsg("Terminado");
					}
				} else {
					System.out.println("Han golpeado agua");
					socket.sendMsg("Fallaste");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Has perdido");
	}

	private static Pair<Integer, Integer> escucharRespuesta(RESPUESTA rs, ClientSocketStream socket)
			throws IOException {
		Pair<Integer, Integer> result = null;
		if (rs == RESPUESTA.ATAQUE) {
			String tmpRespuesta = socket.listen().trim();
			if (tmpRespuesta.equals("Dado")) {
				System.out.println("Le has golpeado");
			} else if (tmpRespuesta.equals("Terminado")) {
				System.out.println("Ganaste");
				System.exit(-1);
			} else {
				System.out.println("Fallaste");
			}
		} else {
			String tmpData = socket.listen();
			String[] tmpArray = tmpData.split("-");
			result = new Pair<Integer, Integer>(Integer.parseInt(tmpArray[0].trim()), Integer.parseInt(tmpArray[1].trim()));
		}
		return result;
	}

}
