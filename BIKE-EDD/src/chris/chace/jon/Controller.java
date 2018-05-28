package chris.chace.jon;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class Controller {

	static final int THREAD_SLEEP_LENGTH = 100, DIRECTIONAL_DURATION = 400, DIRECTIONAL_LIFESPAN = 15000;
	static int PIN_TRIGGER_DIRECTIONAL_LEFT = 02, PIN_TRIGGER_DIRECTIONAL_RIGHT = 03, PIN_TRIGGER_HEADLIGHT = 04, PIN_DIRECTIONAL_LEFT = 05, PIN_DIRECTIONAL_RIGHT = 06, PIN_HEADLIGHT = 07;
	static Pin[] PINS;
	static GpioController gpio;
	static boolean running;

	static {
		PINS = RaspiPin.allPins();
		gpio = GpioFactory.getInstance();
		running = true;
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("DEBUG: Starting bike program");
		System.out.println(PINS);
		if(args.length > 0) {
			PIN_TRIGGER_DIRECTIONAL_LEFT = Integer.valueOf(args[0]);
			PIN_TRIGGER_DIRECTIONAL_RIGHT = Integer.valueOf(args[1]);
			PIN_DIRECTIONAL_LEFT = Integer.valueOf(args[2]);
			PIN_DIRECTIONAL_RIGHT = Integer.valueOf(args[3]);
		}
		
		Directional.init();
		
		while(running) {
			Thread.sleep(Controller.THREAD_SLEEP_LENGTH);
		}
		
		gpio.shutdown();
		System.out.println("Program Termininated");

	}
	
}
