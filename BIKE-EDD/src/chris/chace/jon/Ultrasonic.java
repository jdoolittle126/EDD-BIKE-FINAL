package chris.chace.jon;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.*;

public class Ultrasonic {
    private static long REJECTION_START = 1000, REJECTION_TIME = 23529411;
    private static GpioPinDigitalMultipurpose PIN_DOU;

    public static void init(){
    	System.out.println("DEBUG: Initiating Rangefinder");
    	
    	PIN_DOU = Controller.gpio.provisionDigitalMultipurposePin(RaspiPin.getPinByAddress(Controller.PIN_RANGEFINDER), PinMode.DIGITAL_OUTPUT);
        PIN_DOU.low(); busyWaitMicros(2);
        PIN_DOU.high(); busyWaitMicros(10);
        PIN_DOU.low(); 
        
        System.out.println("DEBUG: Rangefinder Initiated");
    }

    public static int getDistance() throws Exception {    	
    	PIN_DOU.setMode(PinMode.DIGITAL_INPUT);
    	PIN_DOU.setPullResistance(PinPullResistance.PULL_DOWN);
        
        int distance=0; 
        long start_time, end_time,rejection_start=0,rejection_time=0;

           
            while(PIN_DOU.isLow()) {
                busyWaitNanos(1);
                rejection_start++;
                if(rejection_start==REJECTION_START) return -1;
            }
            
            start_time=System.nanoTime();
            
            while(PIN_DOU.isHigh()) {
                busyWaitNanos(1);
                rejection_time++;
                if(rejection_time == REJECTION_TIME) return -1;
            }
            
            end_time=System.nanoTime();
            distance=(int) ((end_time-start_time)/5882.35294118);
            return distance;
    }

    public static void busyWaitMicros(long micros) {
        long waitUntil = System.nanoTime() + (micros * 1_000);
        while(waitUntil > System.nanoTime()) {
        	;
        }
    }

    public static void busyWaitNanos(long nanos) {
        long waitUntil = System.nanoTime() + nanos;
        while(waitUntil > System.nanoTime()){
            ;
        }
    }

}
