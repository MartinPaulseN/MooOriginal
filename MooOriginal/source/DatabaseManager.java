import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements Database {
    private final Connection connection;

    public DatabaseManager(String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public int getPlayerIdByName(String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM players WHERE name = ?")) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getInt("id");
            else return -1;
        }
    }

    public void saveResult(int playerId, int guesses) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO results (result, playerid) VALUES (?, ?)")) {
            statement.setInt(1, guesses);
            statement.setInt(2, playerId);
            statement.executeUpdate();
        }
    }

    public ArrayList<Database.PlayerAverage> getTopTenPlayers() throws SQLException {
        ArrayList<Database.PlayerAverage> topList = new ArrayList<>();
        Statement statement1 = connection.createStatement();
        Statement statement2 = connection.createStatement();
        ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM players");

        while (resultSet1.next()) {
            int id = resultSet1.getInt("id");
            String name = resultSet1.getString("name");
            ResultSet resultSet2 = statement2.executeQuery("SELECT result FROM results WHERE playerid = " + id);

            int total = 0, count = 0;
            while (resultSet2.next()) {
                total += resultSet2.getInt("result");
                count++;
            }

            if (count > 0) {
                double average = (double) total / count;
                topList.add(new Database.PlayerAverage(name, average));
            }
        }

        topList.sort((a, b) -> Double.compare(a.average, b.average));
        return new ArrayList<>(topList.subList(0, Math.min(10, topList.size())));
    }
}
