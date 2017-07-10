package graficos;

public final class Pantalla {

	private final int ancho;
	private final int alto;

	public final int[] pixeles;

	public Pantalla(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		pixeles = new int[ancho * alto];

	}

	/**
	 * 1º En cada actualizacion limpiamos la pantalla y hacerlo la pintamos toda
	 * entera, esto se realizada unas 60 veces cada segundo dependiendo de que
	 * pongamos en ppal.
	 */
	public void limpiar() { // 1º
		for (int i = 0; i < pixeles.length; i++) {

			pixeles[i] = 0; // color del fondo 0 "Negro"

		}
	}

	public void mostrar(final int compensacionX, final int compensacionY) {

		for (int y = 0; y < pixeles.length; y++) { // 2º

			int posicionY = y + compensacionY;
			/**
			 * 2º Controlamos que no nos salgamos de la pantalla, tanto por
			 * arriba y por abajo.
			 */
			if (posicionY < 0 || posicionY >= alto) {
				continue;
			}
			for (int x = 0; x < pixeles.length; x++) { // 2º
				int posicionX = x + compensacionX;
				if (posicionX < 0 || posicionX >= ancho) {
					continue;
				}

				// Codigo para redibujar

			}
		}
	}
}
