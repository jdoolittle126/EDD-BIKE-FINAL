import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import com.github.stanleyseow.RF24;

public class Pager {

	public static void start() throws InterruptedException {

		RF24 radio = new RF24((short) 22, (short) 8, (long) 32);
		BigInteger pipes[] = { new BigInteger("110010101100100011011110100111000110001", 2),
				new BigInteger("110010101100100011011110100111000110010", 2) };

		radio.begin();
		radio.setRetries((short) 15, (short) 15);
		radio.printDetails();
		radio.openWritingPipe(pipes[0]);
		radio.openReadingPipe((short) 1, pipes[1]);
		ByteBuffer buf = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		for (int i = 0; i <= Controller.SIGNALS_SENT; i++) {
			radio.stopListening();
			buf.clear();
			buf.putLong(Controller.PAYLOAD);
			if (radio.write(buf.array(), (short) (buf.capacity())))
				System.out.println("SIGNAL SENT");
			else
				System.out.println("SIGNAL FAILED TO SEND");
			Thread.sleep(Controller.DELAY_BETWEEN_SIGNALS);
		}
	}
}
