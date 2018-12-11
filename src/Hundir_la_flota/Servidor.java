package Hundir_la_flota;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import javafx.util.Pair;

public class Servidor {

	private static enum RESPUESTA {
		ATAQUE, DEFENSA
	};

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			Properties pr = Propiedades.getProp();
			ServerSocketStream socket = new ServerSocketStream(pr.getProperty("server"),
					Integer.parseInt(pr.getProperty("puerto")), false);
			Tablero tl = new Tablero(Integer.parseInt(pr.getProperty("ancho")),
					Integer.parseInt(pr.getProperty("alto")), Integer.parseInt(pr.getProperty("nBarcos")), true);
			int nBarcos = Integer.parseInt(pr.getProperty("nBarcos"));
			socket.accept();
			while (nBarcos > 0) {
				Pair<Integer, Integer> cordenadas = escucharRespuesta(RESPUESTA.DEFENSA, socket);
				System.out.println("Te atacaron en " + cordenadas.getKey() + "-" + cordenadas.getValue());
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
				System.out.println("Este es tu tablero \n" + tl);
				System.out.println(
						"Introduce cordenadas para atacar al rival, las cordenadas deben seguir el formato x-x ");
				socket.sendMsg(sc.nextLine());
				escucharRespuesta(RESPUESTA.ATAQUE, socket);
			}
			System.out.println("Has perdido");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Pair<Integer, Integer> escucharRespuesta(RESPUESTA rs, ServerSocketStream socket)
			throws IOException {
		Pair<Integer, Integer> result = null;
		if (rs == RESPUESTA.ATAQUE) {
			String tmpData = socket.listen().trim();
			if (tmpData.equals("Dado")) {
				System.out.println("Le has golpeado");
			} else if (tmpData.equals("Terminado")) {
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
