public class Main2 {
    public static void main(String[] args) throws Exception {
        try {
            UserInterface ui = new SimpleWindowUI("Moo");
            Database db = new DatabaseManager(
                    "jdbc:mysql://localhost/Moo", "root", "MartinPaulsen03");

            GameController controller = new GameController(ui, db);
            controller.run();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
