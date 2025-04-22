public class SimpleWindowUI implements UserInterface {
    private final SimpleWindow window;

    public SimpleWindowUI(String title) {
        window = new SimpleWindow(title);

    }

    public String prompt(String message) {
        window.addString(message);
        return window.getString();
    }

    public void showMessage(String message) {
        window.addString(message);

    }

    public boolean askYesNo(String message) {
        return window.yesNo(message);

    }

    public void clear() {
        window.clear();

    }

    public void exit() {
        window.exit();

    }
}
