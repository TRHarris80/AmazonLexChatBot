package com.company;

public class LexRequest {


    private String botName;
    private String intentName;
    private String getPlayer;
    private String positionType;
    private String teamType;

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotName() {
        return botName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setGetPlayer(String getPlayer) {
        this.getPlayer = getPlayer;
    }

    public String getGetPlayer() {
        return getPlayer;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setTeamType(String teamType) {
        this.teamType = teamType;
    }

    public String getTeamType() {
        return teamType;
    }
}
