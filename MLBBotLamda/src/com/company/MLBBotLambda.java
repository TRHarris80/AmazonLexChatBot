package com.company;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.util.Map;

public class MLBBotLambda implements RequestHandler<Map<String, Object>, Object>{

    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        Map<String, Object> botMap = (Map<String, Object>) input.get("bot");
        String botName = (String) botMap.get("name");
        LexRequest lexRequest = new LexRequest();
        lexRequest.setBotName(botName);
        Map<String, Object> currentIntent = (Map<String, Object>) input.get("currentIntent");
        lexRequest.setGetPlayer((String)currentIntent.get("name"));
        Map<String, Object> slots = (Map<String, Object>) currentIntent.get("slots");
        lexRequest.setPositionType((String)slots.get("PositionType"));
        lexRequest.setTeamType((String)slots.get("TeamType"));

        return new LexRespond(new DialogAction("Close", "Fulfilled", new Message("PlainText", getData(lexRequest))));
    }

    public static String getData(LexRequest lexRequest){
        String content = "hey";
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            String query = "Select * from Players.PLAYERS where Team = '" + lexRequest.getTeamType() +"' and Position = '" + lexRequest.getPositionType() +"';";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            String playerNumber = resultSet.getString(1);
            String lastName = resultSet.getString(2);
            String firstName = resultSet.getString(3);
            String position = resultSet.getString(4);
            String team = resultSet.getString(5);
            content = "The player you are looking for is " + firstName + " " + lastName + " #" +playerNumber + ", he plays " + position + " for the " + team + ".";

            conn.close();
            return content;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static Connection getConnection() throws SQLException {

        String url = "jdbc:mysql://mlb-bot-main.c38onvvqfswh.us-west-2.rds.amazonaws.com:3306/Players";

        String username = "root";

        String password = "somepassword";

        Connection conn = DriverManager.getConnection(url, username, password);

        return conn;

    }



}
