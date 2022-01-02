package app.durkin.erasure.db;

import app.durkin.erasure.Erasure;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;

public class SQLite extends Database {

    private String dbName;

    public SQLite(Erasure plugin) {
        super(plugin);
        this.dbName = "erasure";
    }

    private String SQLiteCreateDeathsTable = "CREATE TABLE IF NOT EXISTS deaths (" +
            "`id` INTEGER PRIMARY KEY," +
            "`player` varchar(32) NOT NULL," +
            "`type` varchar(64) NOT NULL" +
            ");";

    private String SQLiteWorldNameTable = "CREATE TABLE IF NOT EXISTS worlds (" +
            "`id` INTEGER PRIMARY KEY," +
            "`name` varchar(64) NOT NULL" +
            ");";

    private String SQLiteCreatePlayerTable = "CREATE TABLE IF NOT EXISTS players (" +
            "`id` INTEGER PRIMARY KEY," +
            "`name` varchar(32) NOT NULL," +
            "`uuid` varchar(32) NOT NULL" +
            ");";

    public Connection getSQLConnection() {
        File data = new File(plugin.getDataFolder(), this.dbName + ".db");
        if (!data.exists()) {
            try {
                data.createNewFile();
            } catch(IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error.", e);
            }
        }

        try {
            if (conn != null && !conn.isClosed()) {
                return conn;
            }

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + data);
            return conn;
        } catch(SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "SQLite Exception!", e);
        } catch(ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "Can't get JDBC library.", e);
        }
        return null;
    }

    public void load() {
        conn = getSQLConnection();
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(SQLiteCreateDeathsTable);
            s.executeUpdate(SQLiteWorldNameTable);
            s.executeUpdate(SQLiteCreatePlayerTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }

    public Integer getNumberOfDeathsForPlayer(String player) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM deaths WHERE player = '"+player+"';");
            rs = ps.executeQuery();
            int deaths = 0;
            while (rs.next()) {
                deaths++;
            }
            return deaths;
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not execute query", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch(SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not close SQL", e);
            }
        }
        return 0;
    }

    public boolean addDeathToTable(String player, String type) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO deaths (player, type) VALUES ('"+player+"', '"+type+"');");
            return ps.execute();
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not execute statement", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not close SQL", e);
            }
        }
        return false;
    }

    public String getLatestWorldName() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM worlds");
            rs = ps.executeQuery();
            String worldName = null;
            while (rs.next()) {
                worldName = rs.getString("name");
            }
            return worldName;
        } catch(SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not execute statement", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not close SQL", e);
            }
        }
        return null;
    }

    public boolean addWorldNameToTable(String world) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO worlds (name) VALUES ('"+world+"');");
            return ps.execute();
        } catch(SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not execute statement", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not close SQL", e);
            }
        }
        return false;
    }

    public String getPlayerUUID(String playerName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM players WHERE player = '"+playerName+"';");
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("uuid");
            }
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not execute query", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not close SQL", e);
            }
        }
        return null;
    }

    public boolean addPlayerToDB(String playerName, String playerUUID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO players (player, uuid) VALUES ('"+playerName+"', '"+playerUUID+"');");
            return ps.execute();
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not execute statement", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not close SQL", e);
            }
        }
        return false;
    }
}
