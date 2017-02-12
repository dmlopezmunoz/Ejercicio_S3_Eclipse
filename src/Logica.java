import java.awt.Color;
import java.net.DatagramPacket;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.*;

public class Logica implements Observer {

	private PApplet app;
	private Comunicacion cm;
	private int x, y, vel;
	private boolean limite;
	private String ipAddress;
	private int puerto;
	private int id;

	public Logica(PApplet app) {
		this.app = app;

		cm = new Comunicacion();

		// Creo un hilo
		Thread miHilo = new Thread(cm);

		// Desarrollo el hilo
		miHilo.start();

		// se agrega un observer
		cm.addObserver(this);

		//ipAddress = "226.24.30.8";
		ipAddress = "228.2.2.2";
		id = 3;
		x = 10;
		y = 75;
		vel = 0;
		limite = false;

	}

	public void ejecutableLogica() {
		app.background(255, 255, 255);

		app.noStroke();
		app.fill(255, 20, 147);
		app.ellipse(x, y, 50, 50);

		// mov ellipse
		if (!limite && x >= app.width - 10) {
			llego();
			limite = true;
			vel = 0;
		}
		x += vel;
	}

	public void llego() {
		String miMensaje = "DEVUELVETE, NO PASES MAS";
		cm.enviar(miMensaje, ipAddress, puerto);
	}

	@Override
	public void update(Observable o, Object arg) {
		DatagramPacket miPaquete = (DatagramPacket) arg;

		String enviarDatos = new String(miPaquete.getData(), 0, miPaquete.getLength());

		// recibir ip
		ipAddress = miPaquete.getAddress().toString();
		ipAddress = ipAddress.replaceAll("/", "");
		System.out.println(ipAddress);
		// recibir puerto
		puerto = miPaquete.getPort();

		
		//movimiento de mi ellipse
		if(enviarDatos.contains("corre")){
			vel = (int) app.random(1,5);
		}
		
		if(enviarDatos.contains("pare")){
			vel = 0;
		}


	}
	
	public void enviarMensaje(){
		cm.enviar("corre", ipAddress, 2227);
	}
}
