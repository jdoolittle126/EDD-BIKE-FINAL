package chris.chace.jon;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Controller {
	// - IN MILLIS - \\
	//02 and 03
	
	
	public static final int THREAD_SLEEP_LENGTH = 500, BLINK_DURATION = 500;
	
	public static final Pin[] PINS = RaspiPin.allPins();
	public static final int PIN_DIRECTIONAL_LEFT = 02, PIN_DIRECTIONAL_RIGHT = 03;
	final static GpioController gpio = GpioFactory.getInstance();

	public static void main(String[] args) throws InterruptedException {
		
		final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(Controller.PINS[02],
				PinPullResistance.PULL_DOWN);
		button.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if (event.getState().isHigh()) {
					//THING
					gpio.shutdown();
				}
			}
		});
		
	}
	
	public static void blink(int pin) {
		gpio.provisionDigitalOutputPin(Controller.PINS[pin]).blink(Controller.BLINK_DURATION);
	}
	
}
