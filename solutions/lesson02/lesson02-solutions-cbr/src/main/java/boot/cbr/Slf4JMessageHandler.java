package boot.cbr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4JMessageHandler implements MessageHandler {
    private static final Logger log = LoggerFactory.getLogger(Slf4JMessageHandler.class);

    @Override
    public void showMessage(String message) {
        log.info(message);
    }
}
