package guiBuscaminas;

public class Buscamin {
	int tamamF;
	int tamamC;
	boolean[][] arrayBusca;
	boolean[][] descubierta;

	public Buscamin(int tamanf, int tamanc) {
		// TODO Auto-generated constructor stub
		this.tamamF = tamanf;
		this.tamamC = tamanc;
		arrayBusca = new boolean[tamanf][tamanc];
		descubierta = new boolean[tamanf][tamanc];
	}

	void generarTablero(double ratio) {
		int f = 0;
		int c = 0;
		int ratioN = (int) ((tamamF * tamamC) * ratio);
		int contador = 0;
		while (f < tamamF) {
			c = 0;
			while (c < tamamC) {
				if (contador >= ratioN)
					break;
				if (Math.abs((Math.random() * 1)) <= ratio) {
					if (arrayBusca[f][c] != true) {
						contador++;
						arrayBusca[f][c] = true;
					}
				}
				c++;
			}
			f++;
			if ((contador != ratioN) && (f == tamamF) && (c == tamamC)) {
				f = 0;
			}
		}
	}

	boolean descubrirRecursivo(int f, int c) {
		if (!descubierta[f][c]) {
			descubierta[f][c] = true;
			if (arrayBusca[f][c])
				return false;
			else if (minasColindantes(f, c) == 0) {
//		 Horizontales y verticales
				if (f + 1 < tamamF)
					descubrirRecursivo(f + 1, c);
				if (f - 1 >= 0)
					descubrirRecursivo(f - 1, c);
				if (c + 1 < tamamC)
					descubrirRecursivo(f, c + 1);
				if (c - 1 >= 0)
					descubrirRecursivo(f, c - 1);
//		 Diagonales
				if ((c + 1 < tamamC) && (f + 1 < tamamF))
					descubrirRecursivo(f + 1, c + 1);
				if ((c - 1 >= 0) && (f + 1 < tamamF))
					descubrirRecursivo(f + 1, c - 1);
				if ((c + 1 < tamamC) && (f - 1 >= 0))
					descubrirRecursivo(f - 1, c + 1);
				if ((c - 1 >= 0) && (f - 1 >= 0))
					descubrirRecursivo(f - 1, c - 1);
			}
		}
		return true;
	}

	int minasColindantes(int f, int c) {
		int contV = 0, contH = 0, contD = 0;
		if (arrayBusca[f][c] == false) {
//			 Horizontales y verticales
			if ((f + 1 < tamamF) && (arrayBusca[f + 1][c] == true)) {
				contV++;
			}
			if ((f - 1 >= 0) && (arrayBusca[f - 1][c] == true)) {
				contV++;
			}
			if ((c + 1 < tamamC) && (arrayBusca[f][c + 1] == true)) {
				contH++;
			}
			if ((c - 1 >= 0) && (arrayBusca[f][c - 1] == true)) {
				contH++;
			}
//			 Diagonales
			if ((c + 1 < tamamC) && (f + 1 < tamamF) && (arrayBusca[f + 1][c + 1] == true)) {
				contD++;
			}
			if ((c - 1 >= 0) && (f + 1 < tamamF) && (arrayBusca[f + 1][c - 1] == true)) {
				contD++;
			}
			if ((c + 1 < tamamC) && (f - 1 >= 0) && (arrayBusca[f - 1][c + 1] == true)) {
				contD++;
			}
			if ((c - 1 >= 0) && (f - 1 >= 0) && (arrayBusca[f - 1][c - 1] == true)) {
				contD++;
			}
		}
		return contV + contH + contD;
	}

	public boolean elTableroEstaDescubierto() {
		boolean result = true;
		for (int f = 0; f < tamamF; f++) {
			for (int c = 0; c < tamamF; c++) {
				if ((arrayBusca[f][c] == false) && descubierta[f][c] == false)
					return false;
			}
		}
		return result;
	}
}