package Hundir_la_flota;

import java.util.Random;

import javafx.util.Pair;

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
					if (tablero[y][i]) {
						nBarcos++;
					}
				} else {
					tablero[y][i] = false;
				}

			}

		}
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < tablero.length; i++) {
			if (i != 0) {
				result += "\n";
			}

			for (int y = 0; y < tablero[i].length; y++) {
				if (y != 0) {
					result += ((tablero[i][y]) ? "/1" : "/0");
				} else {
					result += ((tablero[i][y]) ? "1" : "0");
				}
			}
		}
		return result;
	}

	public boolean hit(Pair<Integer, Integer> cordenadas) {
		boolean result = false;
		if(tablero[cordenadas.getKey()-1][cordenadas.getValue()-1]) {
			result = true;
			tablero[cordenadas.getKey()-1][cordenadas.getValue()-1] = false;
		}
		return result;
	}
}
