package Hundir_la_flota;

import java.util.Random;

public class Tablero {
	boolean[][] tablero;
	int totalBarcos;

	public Tablero(int ancho, int alto, int totalBarcos, boolean randStart) {
		tablero = new boolean[ancho][alto];
		this.totalBarcos = totalBarcos;
		if (randStart) {
			this.randomizer();
		}
	}

	private void randomizer() {
		Random rand = new Random();
		int nBarcos = 0;
		for (int i = 0; i < tablero.length; i++) {
			for (int y = 0; y < tablero[i].length; y++) {
				if (nBarcos < totalBarcos) {
					tablero[y][i] = rand.nextBoolean();
					totalBarcos++;
				} else {
					tablero[y][i] = false;
				}

			}

		}
	}
	
	@Override
	public String toString() {
		return null;
	}
}
