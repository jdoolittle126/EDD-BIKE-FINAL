
public class Controller {
	public static long PAYLOAD = 3276525677777L;
	
	public static void main(String[] args) {
		if(args[0].contains("PAGER")) {
			try {
				Pager.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if(args[0].contains("RECEIVER")) {
			try {
				Receiver.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
