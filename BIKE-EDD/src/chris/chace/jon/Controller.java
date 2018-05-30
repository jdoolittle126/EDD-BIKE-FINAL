package chris.chace.jon;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class Controller {

	static final int THREAD_SLEEP_LENGTH = 100, DIRECTIONAL_DURATION = 400, DIRECTIONAL_LIFESPAN = 15000, DISTANCE_MIN = 1000; //distance in mm
	static int PIN_TRIGGER_DIRECTIONAL_LEFT = 2, PIN_TRIGGER_DIRECTIONAL_RIGHT = 3, 
			PIN_TRIGGER_HEADLIGHT = 4, PIN_TRIGGER_RANGEFINDER = 5, PIN_DIRECTIONAL_LEFT = 6, PIN_DIRECTIONAL_RIGHT = 7, 
			PIN_HEADLIGHT = 8, PIN_RANGEFINDER = 9;
	
	static GpioController gpio;
	static volatile boolean running;

	public static void main(String[] args) throws InterruptedException {
		System.out.println("DEBUG: Starting bike program V15");
		System.out.println("ARGS ORDER: BUTTON LEFT, BUTTON RIGHT, BUTTON HEADLIGHT, RANGEFINDER TRIGGER, DIRECTIONAL LEFT, DIRECTIONAL RIGHT, HEADLIGHT, RANGEFINDER\nCTRL+C to Terminiate");
		gpio = GpioFactory.getInstance();
		running = true;
		
		if(args.length > 0) {
			PIN_TRIGGER_DIRECTIONAL_LEFT = Integer.valueOf(args[0]);
			PIN_TRIGGER_DIRECTIONAL_RIGHT = Integer.valueOf(args[1]);
			PIN_TRIGGER_HEADLIGHT = Integer.valueOf(args[2]);
			PIN_TRIGGER_RANGEFINDER = Integer.valueOf(args[3]);
			PIN_DIRECTIONAL_LEFT = Integer.valueOf(args[4]);
			PIN_DIRECTIONAL_RIGHT = Integer.valueOf(args[5]);
			PIN_HEADLIGHT = Integer.valueOf(args[6]);
			PIN_RANGEFINDER = Integer.valueOf(args[7]);
		}
		
		Headlight.init();
		Directional.init();
		//Ultrasonic.init();
		
		while(running) {
			try {
				//System.out.println(Ultrasonic.getDistance());
				//if(Ultrasonic.getDistance() < Controller.DISTANCE_MIN)
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			Thread.sleep(Controller.THREAD_SLEEP_LENGTH);
		}
		
		gpio.shutdown();
		System.out.println("Program Termininated");

	}
	
}
