package boot.cbr.auto;

import boot.cbr.CoffeeBreakReminder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("springtraining.cbr")
public class CoffeeBreakReminderProperties {

    /**
     * The reminder message logged, when it's time to get some coffee.
     */
    private String reminderMessage = CoffeeBreakReminder.DEFAULT_MESSAGE;

    /**
     * The initial delay in minutes until the reminder message appears the first time.
     */
    private double initialReminderAfterMinutes = 90;

    /**
     * Delay to repeat the reminder message in seconds.
     */
    private int repeatReminderAfterSeconds = 300;

    public String getReminderMessage() {
        return reminderMessage;
    }

    public void setReminderMessage(String reminderMessage) {
        this.reminderMessage = reminderMessage;
    }

    public double getInitialReminderAfterMinutes() {
        return initialReminderAfterMinutes;
    }

    public void setInitialReminderAfterMinutes(double initialReminderAfterMinutes) {
        this.initialReminderAfterMinutes = initialReminderAfterMinutes;
    }

    public int getRepeatReminderAfterSeconds() {
        return repeatReminderAfterSeconds;
    }

    public void setRepeatReminderAfterSeconds(int repeatReminderAfterSeconds) {
        this.repeatReminderAfterSeconds = repeatReminderAfterSeconds;
    }
}
