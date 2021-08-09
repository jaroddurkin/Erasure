package app.durkin.erasure.db;

import app.durkin.erasure.Erasure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

abstract class Database {

    Erasure plugin;
    Connection conn;
    public String table = "deaths";
    public Database(Erasure plugin) {
        this.plugin = plugin;
    }

    abstract Connection getSQLConnection();

    abstract void load();

    public void initialize() {
        this.conn = getSQLConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = ?");
            ResultSet rs = ps.executeQuery();
            close(ps, rs);
        } catch(SQLException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Unable to retrieve connection", e);
        }
    }

    public void close(PreparedStatement ps, ResultSet rs) {
        try {
            if (ps != null) ps.close();
            if (rs != null) rs.close();
        } catch(SQLException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Unable to close connection!", e);
        }
    }
}
