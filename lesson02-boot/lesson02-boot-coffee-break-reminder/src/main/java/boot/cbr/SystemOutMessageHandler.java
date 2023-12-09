package boot.cbr;

public class SystemOutMessageHandler implements MessageHandler {
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
