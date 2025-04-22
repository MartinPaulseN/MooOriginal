import java.sql.SQLException;
import java.util.List;

public interface Database {
    int getPlayerIdByName(String name) throws SQLException;
    void saveResult(int playerId, int guesses) throws SQLException;
    List<DatabaseManager.PlayerAverage> getTopTenPlayers() throws SQLException;

    class PlayerAverage{
        public final String name;
        public final double average;

        public PlayerAverage(String name, double average) {
            this.name = name;
            this.average = average;
        }
    }
}
