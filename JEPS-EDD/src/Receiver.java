import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import com.github.stanleyseow.RF24;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Receiver {

	public static void start() throws InterruptedException {

		RF24 radio = new RF24((short) 22, (short) 8, (long) 32);
		BigInteger pipes[] = { new BigInteger("110010101100100011011110100111000110001", 2),
				new BigInteger("110010101100100011011110100111000110010", 2) };

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
		
		radio.begin();
		radio.setRetries((short) 15, (short) 15);
		radio.printDetails();
		radio.openWritingPipe(pipes[1]);
		radio.openReadingPipe((short) 1, pipes[0]);
		radio.startListening();

		ByteBuffer buf = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		
		while (true) {
			if (radio.available()) {
				buf.clear();
				radio.read(buf.array(), (short) (buf.capacity()));
				int payload = buf.getInt();
				radio.stopListening();
				buf.clear();
				buf.putInt(payload);
				radio.write(buf.array(), (short) (buf.capacity()));
				radio.startListening();
				if ((payload & 0xffffffffL) == Controller.PAYLOAD)
					break;
				Thread.sleep(Controller.THREAD_SLEEP_LENGTH);
			}
		}

		gpio.provisionDigitalOutputPin(Controller.PIN_SPEAKER).blink(Controller.AUDIO_OUTPUT_LENGTH);
		while (true) {
			Thread.sleep(Controller.THREAD_SLEEP_LENGTH);
		}
	}
}
