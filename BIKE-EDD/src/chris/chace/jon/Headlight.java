package chris.chace.jon;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Headlight {

	private static boolean state;
	private static GpioPinDigitalInput HEADLIGHT_IO;
	private static GpioPinDigitalOutput PIN_HEADLIGHT;
	
	public static void init() {
		HEADLIGHT_IO.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if (event.getState().isHigh()) {
					toggle();
				}
					
			}
		});
	}
	
	public static void toggle() {
		state = !state;
		PIN_HEADLIGHT.setState(state);
	}
}
