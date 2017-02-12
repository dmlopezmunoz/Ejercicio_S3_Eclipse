import processing.core.PApplet;

public class Main extends PApplet {
	private Logica app;

	public void settings() {
		size(700, 150);

	}

	@Override
	public void setup() {
		app = new Logica(this);
	}

	public void draw() {

		app.ejecutableLogica();
		
	}

	public static void main(String[] args) {
		
		PApplet.main("Main");
	}

}
