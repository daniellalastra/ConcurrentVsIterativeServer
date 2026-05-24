// common/CountdownProtocol.java
package common;

public class CountdownProtocol {
    private int currentValue;
    private boolean sessionActive;
    
    public CountdownProtocol() {
        sessionActive = false;
    }
    
    public String processRequest(String input) {
        if (!sessionActive) {
            // First message - start countdown
            try {
                currentValue = Integer.parseInt(input.trim());
                sessionActive = true;
                return "Countdown started from: " + currentValue;
            } catch (NumberFormatException e) {
                return "ERROR: Please send a valid integer";
            }
        } else {
            // Continue countdown
            if (currentValue > 0) {
                String response = String.valueOf(currentValue);
                currentValue--;
                return response;
            } else {
                sessionActive = false;
                return "Countdown complete!";
            }
        }
    }
    
    public boolean isSessionActive() {
        return sessionActive && currentValue >= 0;
    }
}
