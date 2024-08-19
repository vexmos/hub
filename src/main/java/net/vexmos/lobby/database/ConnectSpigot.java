package net.vexmos.lobby.database;

import net.vexmos.lobby.api.SpigotConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConnectSpigot {

    private Connection connection;
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    public ConnectSpigot() {
        SpigotConfig config = new SpigotConfig("database.yml");
        config.saveDefault();
        host = config.getConfig().getString("mysql.host");
        port = config.getConfig().getString("mysql.port");
        database = config.getConfig().getString("mysql.database");
        username = config.getConfig().getString("mysql.username");
        password = config.getConfig().getString("mysql.password");
        connect();
        createPlayersVisibleTable();
        createFirstLoginTable();
        createJoinMessageTable();
    }



    public void connect() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                System.out.println("MySQL conectado com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getCoins(String playerName) {
        String query = "SELECT coins FROM coins WHERE player_name = ?;";
        int coins = 0;

        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                coins = resultSet.getInt("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coins;
    }







    public void createPlayersVisibleTable() {
        String query = "CREATE TABLE IF NOT EXISTS PlayersVisible (" +
                "player_name VARCHAR(16) PRIMARY KEY, " +
                "visible BOOLEAN DEFAULT TRUE" +
                ");";
        try {
            prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPlayerVisible(String playerName, boolean isVisible) {
        String query = "INSERT INTO PlayersVisible (player_name, visible) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE visible = ?";
        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);
            statement.setBoolean(2, isVisible);
            statement.setBoolean(3, isVisible);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlayerVisible(String playerName) {
        boolean visible = true; // Valor padrão
        String query = "SELECT visible FROM PlayersVisible WHERE player_name = ?";
        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                visible = resultSet.getBoolean("visible");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visible;
    }



    public int getCristais(String playerName) {
        String query = "SELECT cristais FROM cristais WHERE player_name = ?;";
        int cristais = 0;

        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cristais = resultSet.getInt("cristais");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cristais;
    }



    // Define o status do Tell (on/off) para o jogador.
    public void setTellStatus(String playerName, boolean isEnabled) {
        String query = "INSERT INTO tell_status (player_name, tell_enabled) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE tell_enabled=?;";
        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);
            statement.setBoolean(2, isEnabled);
            statement.setBoolean(3, isEnabled);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Verifica se o Tell está ativado ou desativado para o jogador.
    public boolean isTellEnabled(String playerName) {
        String query = "SELECT tell_enabled FROM tell_status WHERE player_name=?";
        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("tell_enabled");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;  // Padrão é TRUE (Tell ativado).
    }


    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addCosmeticToPlayer(UUID playerUUID, String category, String cosmeticName) {
        String query = "INSERT INTO player_cosmetics (player_uuid, category, cosmetic_name) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE category = VALUES(category), cosmetic_name = VALUES(cosmetic_name)";

        try (PreparedStatement statement = prepareStatement(query)) {
            statement.setString(1, playerUUID.toString());
            statement.setString(2, category);
            statement.setString(3, cosmeticName);
            statement.executeUpdate();
            System.out.println("Cosmético adicionado para o jogador: " + playerUUID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCosmeticFromPlayer(UUID playerUUID, String category, String cosmeticName) {
        String query = "DELETE FROM player_cosmetics WHERE player_uuid = ? AND category = ? AND cosmetic_name = ?";

        try (PreparedStatement statement = prepareStatement(query)) {
            statement.setString(1, playerUUID.toString());
            statement.setString(2, category);
            statement.setString(3, cosmeticName);
            statement.executeUpdate();
            System.out.println("Cosmético removido para o jogador: " + playerUUID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean playerHasCosmetic(UUID playerUUID, String category, String cosmeticName) {
        String query = "SELECT 1 FROM player_cosmetics WHERE player_uuid = ? AND category = ? AND cosmetic_name = ?";

        try (PreparedStatement statement = prepareStatement(query)) {
            statement.setString(1, playerUUID.toString());
            statement.setString(2, category);
            statement.setString(3, cosmeticName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public PreparedStatement prepareStatement(String query) throws SQLException {
        if (isConnected()) {
            return connection.prepareStatement(query);
        }
        return null;
    }
    public void createJoinMessageTable() {
        String query = "CREATE TABLE IF NOT EXISTS JoinMessagePreference (" +
                "player_name VARCHAR(16) PRIMARY KEY, " +
                "join_message BOOLEAN DEFAULT FALSE" +
                ");";
        try {
            prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setJoinMessagePreference(String playerName, boolean joinMessage) {
        String query = "INSERT INTO JoinMessagePreference (player_name, join_message) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE join_message = ?";
        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);
            statement.setBoolean(2, joinMessage);
            statement.setBoolean(3, joinMessage);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obter a preferência de JoinMessage
    public boolean hasJoinMessage(String playerName) {
        boolean joinMessage = false; // Valor padrão
        String query = "SELECT join_message FROM JoinMessagePreference WHERE player_name = ?";
        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                joinMessage = resultSet.getBoolean("join_message");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joinMessage;
    }

    // Método para remover a preferência de JoinMessage (setar para false)
    public void removeJoinMessagePreference(String playerName) {
        setJoinMessagePreference(playerName, false);
    }


    public ResultSet query(String query) throws SQLException {
        if (isConnected()) {
            return prepareStatement(query).executeQuery();
        }
        return null;
    }

    public int update(String query) throws SQLException {
        if (isConnected()) {
            return prepareStatement(query).executeUpdate();
        }
        return 0;
    }

    public void createTags() {
        String query = "CREATE TABLE IF NOT EXISTS tags (" +
                "player_name VARCHAR(255) NOT NULL," +
                "tag VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (player_name)" +
                ");";

        try {
            PreparedStatement statement = prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCoinsTable() {
        String query = "CREATE TABLE IF NOT EXISTS coins (" +
                "player_name VARCHAR(16) PRIMARY KEY, " +
                "coins INT);";
        try {
            prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public String getPlayerGroup(String playerName) {
        String group = "membro";  // Valor padrão
        String query = "SELECT group_name FROM groups WHERE player_name = ?";

        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                group = resultSet.getString("group_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return group;
    }

    public void setPlayerGroup(String playerName, String group) {
        String query = "UPDATE groups SET group_name = ? WHERE player_name = ?";

        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, group);
            statement.setString(2, playerName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void setPlayerTag(String playerName, String tag) {
        String query = "SELECT * FROM tags WHERE player_name = ?";

        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Se o jogador já tem uma tag associada, atualize-a
                query = "UPDATE tags SET tag = ? WHERE player_name = ?";
                statement = prepareStatement(query);
                statement.setString(1, tag);
                statement.setString(2, playerName);
                statement.executeUpdate();
            } else {
                // Se o jogador não tem uma tag associada, crie um novo registro
                query = "INSERT INTO tags (player_name, tag) VALUES (?, ?)";
                statement = prepareStatement(query);
                statement.setString(1, playerName);
                statement.setString(2, tag);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createFirstLoginTable() {
        String query = "CREATE TABLE IF NOT EXISTS first_login (" +
                "player_name VARCHAR(16) PRIMARY KEY, " +
                "first_login_date DATETIME NOT NULL);";
        try {
            prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getFirstLoginDate(String playerName) {
        String query = "SELECT first_login_date FROM first_login WHERE player_name = ?";
        String firstLoginDate = null;

        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                firstLoginDate = resultSet.getString("first_login_date");
            } else {
                System.out.println("Jogador não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return firstLoginDate;
    }


    public void registerFirstLogin(String playerName) {
        String selectQuery = "SELECT * FROM first_login WHERE player_name = ?";
        String insertQuery = "INSERT INTO first_login (player_name, first_login_date) VALUES (?, NOW())";

        try {
            PreparedStatement selectStatement = prepareStatement(selectQuery);
            selectStatement.setString(1, playerName);
            ResultSet resultSet = selectStatement.executeQuery();

            if (!resultSet.next()) {
                // Se o jogador não está registrado, insere o primeiro login
                PreparedStatement insertStatement = prepareStatement(insertQuery);
                insertStatement.setString(1, playerName);
                insertStatement.executeUpdate();
                System.out.println("Primeiro login registrado para: " + playerName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public String getPlayerTag(String playerName) {
        String tag = null;
        String query = "SELECT tag FROM tags WHERE player_name = ?";

        try {
            PreparedStatement statement = prepareStatement(query);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tag = resultSet.getString("tag");
            } else {
                // Se o jogador não tem uma tag associada, retorne a tag padrão
                tag = "membro";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tag;
    }

}