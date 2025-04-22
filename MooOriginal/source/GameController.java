import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final UserInterface ui;
    private final Database db;

    public GameController(UserInterface ui, Database db) {
        this.ui = ui;
        this.db = db;
    }

    public void run() throws SQLException, InterruptedException {
        ui.showMessage("Enter your user name:\n");
        String name = ui.prompt("");
        int playerId = db.getPlayerIdByName(name);

        if (playerId < 0) {
            ui.showMessage("User not in database, please register with admin");
            Thread.sleep(5000);
            ui.exit();
            return;
        }

        boolean play = true;
        while (play) {
            String goal = generateGoal();
            ui.clear();
            ui.showMessage("New game:\n");
            String guess = ui.prompt("");
            int guesses = 1;
            String feedback = generateFeedback(goal, guess);
            ui.showMessage(guess + "\n" + feedback + "\n");

            while (!feedback.equals("BBBB,")) {
                guess = ui.prompt("");
                feedback = generateFeedback(goal, guess);
                guesses++;
                ui.showMessage(guess + ":\n" + feedback + "\n");
            }

            db.saveResult(playerId, guesses);
            showTopTen();
            play = ui.askYesNo("Correct, it took " + guesses + " guesses\nContinue?");
        }

        ui.exit();
    }

    private String generateGoal() {
        StringBuilder goal = new StringBuilder();
        while (goal.length() < 4) {
            String digit = String.valueOf((int)(Math.random() * 10));
            if (!goal.toString().contains(digit)) {
                goal.append(digit);
            }
        }
        return goal.toString();
    }

    private String generateFeedback(String goal, String guess) {
        int bulls = 0, cows = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (goal.charAt(i) == guess.charAt(j))
                    if (i == j) bulls++; else cows++;

        return "B".repeat(bulls) + "," + "C".repeat(cows);
    }

    private void showTopTen() throws SQLException {
        List<DatabaseManager.PlayerAverage> topList = new ArrayList<>();
        ui.showMessage("Top Ten List\n     Player     Average\n");
        int pos = 1;
        for (DatabaseManager.PlayerAverage p : topList) {
            ui.showMessage(String.format("%3d %-10s%5.2f%n", pos++, p.name, p.average));
        }
    }
}
