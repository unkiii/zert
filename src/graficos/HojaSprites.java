package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HojaSprites {
	private final int ancho;
	private final int alto;
	public final int[] pixeles;

	/**
	 * pixeles de nuestra imagen
	 */
	public HojaSprites(final String ruta, final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		/**
		 * El array tiene que tener tantas posiciones como pixeles tenga nuestra
		 * imagen
		 */
		pixeles = new int[ancho * alto];

		BufferedImage imagen;
		try {
			imagen = ImageIO.read(HojaSprites.class.getResource(ruta));
			imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);
		} catch (IOException e) {
			System.out.println("Error en BufferedImage class HojaSprites: " + e);
			// e.printStackTrace();
		}

	}

	public int obtenAncho() {
		return ancho;

	}

}
