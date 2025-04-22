public interface UserInterface {
    void showMessage(String message);
    String prompt(String message);
    boolean askYesNo(String message);
    void clear();
    void exit();
}
