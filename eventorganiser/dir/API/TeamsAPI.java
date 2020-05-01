package eventorganiser.dir.API;

import eventorganiser.dir.DBMethods.DBRespositories.TeamsRepository;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import static java.lang.Integer.parseInt;

@RestController
public class TeamsAPI {

    private TeamsRepository teamsRepository;

    public TeamsAPI(TeamsRepository teamsRepository) {
        this.teamsRepository = teamsRepository;
    }

    // get all Teams
    @GetMapping("/api/get/teams")
    public Object APIgetAllTeams() {
        return teamsRepository.getAllTeams();
    }

    // get random Team
    @GetMapping("/api/get/team/random")
    public Object APIgetRandomTeam() {
        return teamsRepository.getRandomTeam();
    }

    // get Team Id
    @GetMapping("/api/get/teamId")
    public Object APIgetTeamIdFromEventId(@RequestParam(name="eventId", required=true) int eventId,
                                          @RequestParam(name="userId", required=true) int userId) {
        return teamsRepository.getTeamIdFromEventIdAndUserId(eventId, userId);
    }

    @GetMapping("/api/get/event/teamId")
    public int getTeamIdFromTeamNameAndEventId(@RequestParam(name="teamName") String teamName,
                                                       @RequestParam(name="eventId") int eventId){
        return teamsRepository.getTeamIdFromTeamNameAndEventId(teamName,eventId);
    }

    @GetMapping("/api/get/event/teammates")
    public Object APIgetTeammatesFromUserIdFromEventId(@RequestParam(name="eventId", required=true) int eventId,
                                                       @RequestParam(name="teamId", required=true) int teamId) {
        return teamsRepository.getTeamMembers(eventId, teamId);
    }

    // get Team Name
    @GetMapping("/api/get/team/name")
    public Object APIgetTeamNameFromTeamId(@RequestParam(name="teamId", required=true) int teamId) {
        return teamsRepository.getTeamNameFromTeamId(teamId);
    }

}
