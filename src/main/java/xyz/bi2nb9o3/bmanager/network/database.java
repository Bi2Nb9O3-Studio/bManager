package xyz.bi2nb9o3.bmanager.network;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import xyz.bi2nb9o3.bmanager.config.BMConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import static xyz.bi2nb9o3.bmanager.bmanager.CONFIG;

public class database {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String DB_URL = CONFIG.jdbcURL();
    public static String USER = CONFIG.dbUsername();
    public static String PASS = CONFIG.dbPassword();

    public static JSONArray analyzeData(String str) {
        return JSON.parseArray(str);
    }

    public static String[] getData(String sid, JSONArray dataSelector) {
        List<String> results = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            for (Object obj : dataSelector) {
                JSONObject jsonObject = (JSONObject) obj;
                String table = jsonObject.getString("table");
                String row = jsonObject.getString("row");
                JSONArray show = jsonObject.getJSONArray("show");
                Statement statement = conn.createStatement();
                System.out.println("SELECT " + row + " FROM " + table + " WHERE id = '" + sid + "'");
                ResultSet resultSet = statement.executeQuery("SELECT " + row + " FROM " + table + " WHERE "+CONFIG.indexRowName()+" = '" + sid + "'");
                while (resultSet.next()) {
                    String data = resultSet.getString(row);
                    results.add(show.getString(0) + " " + data + " " + show.getString(1));
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.sendMessage(Text.literal(("§9§l[bManager]§r "+"§cERROR:Database connect error.See more in log.")),true);
            results.add("§cDatabase connect error§r");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return results.toArray(new String[0]);
    }

}

