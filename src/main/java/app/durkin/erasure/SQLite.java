package app.durkin.erasure;

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
}
