package chris.chace.jon;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Directional {
	private static boolean left, right;
	private static GpioPinDigitalInput PIN_BUTTON_LEFT, PIN_BUTTON_RIGHT;
	private static GpioPinDigitalOutput PIN_LEFT, PIN_RIGHT;

	public static void init() {
		System.out.println("DEBUG: Directional initiated");
		PIN_BUTTON_LEFT = Controller.gpio.provisionDigitalInputPin(Controller.PINS[Controller.PIN_TRIGGER_DIRECTIONAL_LEFT], PinPullResistance.PULL_DOWN);
		PIN_BUTTON_RIGHT = Controller.gpio.provisionDigitalInputPin(Controller.PINS[Controller.PIN_TRIGGER_DIRECTIONAL_RIGHT],PinPullResistance.PULL_DOWN);
		PIN_LEFT = Controller.gpio.provisionDigitalOutputPin(Controller.PINS[Controller.PIN_DIRECTIONAL_LEFT]);
		PIN_RIGHT = Controller.gpio.provisionDigitalOutputPin(Controller.PINS[Controller.PIN_DIRECTIONAL_RIGHT]);
		
		
		PIN_BUTTON_LEFT.addListener(new GpioPinListenerDigital() {
					@Override
					public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
						if (event.getState().isHigh())
							left();
					}
				});

		PIN_BUTTON_RIGHT.addListener(new GpioPinListenerDigital() {
					@Override
					public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
						if (event.getState().isHigh())
							right();
					}
				});
	}

	private static void off() {
		left = right = false;
		PIN_LEFT.setState(false);
		PIN_RIGHT.setState(false);
	}

	private static void left() {
		System.out.println("DEBUG: Left directional triggered");
		if (right) {
			off();
			left = true;
			PIN_LEFT.blink(Controller.DIRECTIONAL_DURATION, Controller.DIRECTIONAL_LIFESPAN);
		} else if (left)
			off();
		else {
			left = true;
			PIN_LEFT.blink(Controller.DIRECTIONAL_DURATION, Controller.DIRECTIONAL_LIFESPAN);
		}
					
	
	}

	private static void right() {
		System.out.println("DEBUG: Right directional triggered");
		if (left) {
			off();
			right = true;
			PIN_RIGHT.blink(Controller.DIRECTIONAL_DURATION, Controller.DIRECTIONAL_LIFESPAN);
		} else if (right)
			off();
		else {
			right = true;
			PIN_RIGHT.blink(Controller.DIRECTIONAL_DURATION, Controller.DIRECTIONAL_LIFESPAN);
		}
			
	}

}
