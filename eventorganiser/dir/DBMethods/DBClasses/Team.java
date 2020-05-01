package eventorganiser.dir.DBMethods.DBClasses;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private int teamId;
    private int eventId;
    private String teamName;

    public Team() {}

    public Team(int teamId, int eventId, String teamName) {
        this.teamId = teamId;
        this.eventId = eventId;
        this.teamName = teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getEventId() {
        return eventId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

}