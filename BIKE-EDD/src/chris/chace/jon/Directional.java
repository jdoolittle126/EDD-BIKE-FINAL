package chris.chace.jon;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.trigger.GpioBlinkStateTrigger;

public class Directional {
	private static boolean left, right;
	private static GpioPinDigitalInput PIN_BUTTON_LEFT, PIN_BUTTON_RIGHT;
	private static GpioPinDigitalOutput PIN_LEFT, PIN_RIGHT;

	public static void init() {
		System.out.println("DEBUG: Initiating Directional");
		PIN_BUTTON_LEFT = Controller.gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(Controller.PIN_TRIGGER_DIRECTIONAL_LEFT), PinPullResistance.PULL_DOWN);
		PIN_BUTTON_RIGHT = Controller.gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(Controller.PIN_TRIGGER_DIRECTIONAL_RIGHT), PinPullResistance.PULL_DOWN);
		PIN_LEFT = Controller.gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(Controller.PIN_DIRECTIONAL_LEFT));
		PIN_RIGHT = Controller.gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(Controller.PIN_DIRECTIONAL_RIGHT));
		
		PIN_LEFT.setShutdownOptions(true, PinState.LOW);
		PIN_RIGHT.setShutdownOptions(true, PinState.LOW);
		
		//PIN_BUTTON_LEFT.addTrigger(new GpioBlinkStateTrigger(PinState.HIGH, PIN_LEFT, Controller.DIRECTIONAL_LIFESPAN));
		
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
		System.out.println("DEBUG: Directional Initiated");
	}

	private static void off() {
		left = right = false;
		PIN_LEFT.blink(0);
		PIN_RIGHT.blink(0);
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
			//PIN_RIGHT.blink(Controller.DIRECTIONAL_DURATION, Controller.DIRECTIONAL_LIFESPAN);
		} else if (right)
			off();
		else {
			right = true;
			//PIN_RIGHT.blink(Controller.DIRECTIONAL_DURATION, Controller.DIRECTIONAL_LIFESPAN);
		}
			
	}

}
