 import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Controller {
	public static final long PAYLOAD = 3276525677777L;
	public static final int SIGNALS_SENT = 5;
	// - IN MILLIS - \\
	public static final long AUDIO_OUTPUT_LENGTH = 2000L;
	public static final int THREAD_SLEEP_LENGTH = 500, DELAY_BETWEEN_SIGNALS = 1000;
	
	public static final Pin PIN_SPEAKER = RaspiPin.GPIO_07, PIN_BUTTON = RaspiPin.GPIO_01;

	public static void main(String[] args) throws InterruptedException {
		if (args[0].contains("PAGER")) {
			Pager.start();
		} else if (args[0].contains("RECEIVER")) {
			Receiver.start();
		} else if (args[0].contains("DEV")) {
			
			final GpioController gpio = GpioFactory.getInstance();
			final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(Controller.PIN_BUTTON,
					PinPullResistance.PULL_DOWN);
			button.addListener(new GpioPinListenerDigital() {
				@Override
				public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
					if (event.getState().isHigh()) {
						System.out.println("Object Found");
						gpio.shutdown();
					}
				}
			});
			
			gpio.provisionDigitalOutputPin(Controller.PIN_SPEAKER).blink(Controller.AUDIO_OUTPUT_LENGTH);
			while (true) {
				Thread.sleep(Controller.THREAD_SLEEP_LENGTH);
			}
		}	
		System.out.println("JEPS Program Termininated");
	}
}
