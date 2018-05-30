package chris.chace.jon;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Headlight {

	private static boolean state;
	private static GpioPinDigitalInput HEADLIGHT_IO;
	private static GpioPinDigitalOutput PIN_HEADLIGHT;
	
	public static void init() {
		System.out.println("DEBUG: Initiating Headlight");
		PIN_HEADLIGHT = Controller.gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(Controller.PIN_HEADLIGHT), "pin_headlight", PinState.LOW);
		HEADLIGHT_IO = Controller.gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(Controller.PIN_TRIGGER_HEADLIGHT),PinPullResistance.PULL_DOWN);
		PIN_HEADLIGHT.setShutdownOptions(true, PinState.LOW);
		HEADLIGHT_IO.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if (event.getState().isHigh()) {
					toggle();
					System.out.println("DEBUG: Headlight toggled");
				}
					
			}
		});
		System.out.println("DEBUG: Headlight Initiated");
	}
	
	public static void toggle() {
		state = !state;
		PIN_HEADLIGHT.setState(state);
	}
}
