package boot.cbr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class CoffeeBreakReminder {
    private static final Logger log = LoggerFactory.getLogger(CoffeeBreakReminder.class);
    public static final String DEFAULT_MESSAGE = "It's time to get some coffee!!!";

    private String reminderMessage = DEFAULT_MESSAGE;

    @Autowired
    private List<MessageHandler> messageHandlers;

    @Scheduled(initialDelayString = "#{reminderDelaySettings.initialDelay}",
            fixedRateString = "#{reminderDelaySettings.fixedRate}")
    public void remind() {
        log.info(reminderMessage);
        messageHandlers.forEach(handler -> handler.showMessage(reminderMessage));
    }

    public String getReminderMessage() {
        return reminderMessage;
    }

    public void setReminderMessage(String reminderMessage) {
        this.reminderMessage = reminderMessage;
    }
}
