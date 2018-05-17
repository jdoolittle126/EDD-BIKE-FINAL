import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import com.github.stanleyseow.RF24;

public class Receiver {

    static {
        System.loadLibrary("rf24bcmjava");
    }

    public static void start() throws InterruptedException {
    	
        RF24 radio = new RF24((short) 22,(short) 8, (long) 32);
        BigInteger pipes[] = { new BigInteger("110010101100100011011110100111000110001",2), new BigInteger("110010101100100011011110100111000110010",2)};
        
        radio.begin();
        radio.setRetries((short) 15, (short) 15);
        radio.printDetails();
        radio.openWritingPipe(pipes[1]);
        radio.openReadingPipe((short) 1, pipes[0]);
        radio.startListening();
        ByteBuffer buf = ByteBuffer.allocate(Integer.SIZE/Byte.SIZE);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        while(true) {
           if (radio.available()) {
              int payload;
              buf.clear();
              radio.read(buf.array(), (short)(buf.capacity()));
              payload = buf.getInt();
              radio.stopListening();    
              buf.clear();
              buf.putInt(payload);
              radio.write(buf.array(), (short)(buf.capacity()));
              radio.startListening();
              if((payload & 0xffffffffL) == Controller.PAYLOAD) break;
              Thread.sleep(925);      
           }
        }
        
        while(true) {
        	System.out.println("BEEP");
        }
        
    } 
}

