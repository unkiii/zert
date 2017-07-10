package zert;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import control.Teclado;

public class ppal extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	// Tamaño de la ventana
	private static final int ANCHO = 800;
	private static final int ALTO = 600;

	// Nos dirá si se está ejecutando o no
	private static volatile boolean enFuncionamiento = false;

	// Nombre de la ventana
	private static final String NOMFINESTRA = "Zert";

	private static int aps = 0; // Actualizaciones por segundo.
	private static int fps = 0; // Fotogramas por segundo.

	//
	private static JFrame ventana;
	private static Thread thread;
	private static Teclado teclado;

	private ppal() {

		/**
		 * Configuración de la ventana
		 */
		setPreferredSize(new Dimension(ANCHO, ALTO));

		teclado = new Teclado();
		addKeyListener(teclado);

		ventana = new JFrame(NOMFINESTRA);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
		ventana.setLayout(new BorderLayout());
		ventana.add(this, BorderLayout.CENTER);
		ventana.pack();
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	/**
	 * Metodo Main que ejecuta la APP
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ppal zert = new ppal();
		zert.iniciar();
	}

	private synchronized void iniciar() {
		enFuncionamiento = true;

		thread = new Thread(this, "Graficos");
		thread.start();
	}

	private synchronized void detener() {
		enFuncionamiento = false;

		try {
			thread.join(); // Join es mejor que stop, ya que Join espera a que
							// se terminen las ejecuciones activas, en cambio
							// stop para todos los procesos de golpe.
		} catch (InterruptedException e) {
			System.out.println("Error en thread.join: " + e);
			// e.printStackTrace();
		}
	}

	private void actualizar() {
		teclado.actualizar();

		if (teclado.arriba) {
			System.out.println("Movimiento hacia arriba");
		}

		if (teclado.abajo) {
			System.out.println("Movimiento hacia abajo");
		}

		if (teclado.izquierda) {
			System.out.println("Movimiento hacia izquierda");
		}

		if (teclado.derecha) {
			System.out.println("Movimiento hacia derecha");
		}

		aps++;
	}

	private void mostrar() {
		fps++;
	}

	public void run() {
		final int NS_POR_SEGUNDO = 1000000000; // cuantos nanosegundos hay en 1
												// segundo.
		final byte APS_OBJETIVO = 60; // fps, 30 "recomendable"

		/**
		 * 1º el fin es saber cuantos nano segundos tienen que transcurrir para
		 * que sigamos el objetivo de actualizar 60 veces por segundo "60fps".
		 */
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO; // 1º

		long referenciaActualizacion = System.nanoTime();
		long referenciaContador = System.nanoTime();

		double tiempoTranscurrido;
		double delta = 0; // es la cantidad de tiempo que a transcurrido hasta
							// que se realiza una actualizacion

		requestFocus();

		while (enFuncionamiento) {
			final long inicioBucle = System.nanoTime(); // =+- a iniciar el
														// cronometro

			/**
			 * 2º cuanto tiempo a pasado entre los 2 cronometros.
			 */
			tiempoTranscurrido = inicioBucle - referenciaActualizacion; // 2º
			referenciaActualizacion = inicioBucle;

			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

			while (delta >= 1) {
				actualizar();
				delta--;
			}

			mostrar();

			/**
			 * Sirve para actualizar el contador visual de los FPS
			 */
			if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
				ventana.setTitle(NOMFINESTRA + " || APS: " + aps + " || fps: " + fps);
				aps = 0;
				fps = 0;
				referenciaContador = System.nanoTime();
			}
		}
	}
}

// System.nanoTime(); // Requiere java 1.5 o superior.

// System.currentTimeMillis(); // Apartir de la versión 1, pero es menos
// precisa, no se recomienda.