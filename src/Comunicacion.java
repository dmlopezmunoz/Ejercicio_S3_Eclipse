import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Observable;

public class Comunicacion extends Observable implements Runnable {
	MulticastSocket miCast;

	private final int PUERTO= 2227;
	private boolean duracion = true;

	
	public Comunicacion() {

		try {
			miCast = new MulticastSocket(PUERTO);

			InetAddress maGrup = InetAddress.getByName("226.24.30.8");
			miCast.joinGroup(maGrup);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override

	public void run() {
		while (duracion) {
			if (miCast != null) {
				DatagramPacket miPaquete = recibido();
				if(miPaquete != null){
					setChanged();
					notifyObservers(miPaquete);
					clearChanged();
				}
			}
		}
	}

	private DatagramPacket recibido() {

		byte[] arreglito = new byte[1024];
		DatagramPacket miPaquete = new DatagramPacket(arreglito, arreglito.length);

		try {
			miCast.receive(miPaquete);

			return miPaquete;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void enviar(String message, String ipAddress, int puerto) {

		byte[] arreglito2 = message.getBytes();

		try {
			InetAddress host = InetAddress.getByName(ipAddress);
			DatagramPacket miPaqueteSend = new DatagramPacket(arreglito2, arreglito2.length, host, puerto);
			miCast.send(miPaqueteSend);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

}
