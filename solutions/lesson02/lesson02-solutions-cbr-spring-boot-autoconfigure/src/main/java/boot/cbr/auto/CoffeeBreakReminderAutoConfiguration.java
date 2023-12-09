package boot.cbr.auto;

import boot.cbr.CoffeeBreakReminder;
import boot.cbr.DelaySettings;
import boot.cbr.MessageHandler;
import boot.cbr.Slf4JMessageHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ConditionalOnClass(Logger.class) // Require SLF4J on classpath
@EnableConfigurationProperties(CoffeeBreakReminderProperties.class)
@EnableScheduling
public class CoffeeBreakReminderAutoConfiguration {

    @Autowired
    private CoffeeBreakReminderProperties coffeeBreakReminderProperties;

    @Bean
    @ConditionalOnMissingBean
    public DelaySettings reminderDelaySettings() {
        DelaySettings delaySettings = new DelaySettings();
        delaySettings.setInitialDelay((long) (coffeeBreakReminderProperties.getInitialReminderAfterMinutes() * 60000L));
        delaySettings.setFixedRate(coffeeBreakReminderProperties.getRepeatReminderAfterSeconds() * 1000L);
        return delaySettings;
    }

    @Bean
    @ConditionalOnMissingBean
    public CoffeeBreakReminder coffeeBreakReminder() {
        CoffeeBreakReminder cbr = new CoffeeBreakReminder();
        cbr.setReminderMessage(coffeeBreakReminderProperties.getReminderMessage());
        return cbr;
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageHandler reminderMessageHandler() {
        Slf4JMessageHandler messageHandler = new Slf4JMessageHandler();
        return messageHandler;
    }
}
