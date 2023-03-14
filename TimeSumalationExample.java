import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeSumalationExample {

    
    static int counter = 0;
    
    public static void main(String[] args) {
        Timer timer = new Timer(100, new TimerListener());
        timer.start();
        while(true){
        	
        }
    }


    private static class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Tick "+counter);
            counter++;
            methodCall();
            
            
            
        }

        private void methodCall() {
            System.out.println("here");
        }
    }
}
