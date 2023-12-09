package boot.cbr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
public class CoffeeBreakReminderTest {
    private static final Logger log = LoggerFactory.getLogger(CoffeeBreakReminderTest.class);

    @EnableScheduling
    @Configuration
    static class Config {
        @Bean(name="reminderDelaySettings")
        DelaySettings delaySettings() {
            DelaySettings delaySettings = new DelaySettings();
            delaySettings.setInitialDelay(100);
            delaySettings.setFixedRate(20);
            return delaySettings;
        }

        @Bean
        CoffeeBreakReminder coffeeBreakReminder() {
            return new CoffeeBreakReminder();
        }

        @Bean
        CountingMessageHandler messageHandler() {
            return new CountingMessageHandler();
        }
    }

    static class CountingMessageHandler implements MessageHandler {

        String message;
        int count;

        @Override
        public void showMessage(String message) {
            this.message = message;
            count++;
        }
    }

    @Autowired
    CountingMessageHandler countingMessageHandler;

    @Test
    void test(TestInfo info) throws InterruptedException {
        log.info("Started " + info.getDisplayName());
        Thread.sleep(160);
        assertThat(countingMessageHandler.message).isEqualTo(CoffeeBreakReminder.DEFAULT_MESSAGE);
        assertThat(countingMessageHandler.count).isGreaterThan(5);
    }
}
