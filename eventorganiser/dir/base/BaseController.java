package eventorganiser.dir.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventorganiser.dir.DBMethods.DBClasses.*;
import eventorganiser.dir.DBMethods.DBRespositories.AttendanceRepository;
import eventorganiser.dir.DBMethods.DBRespositories.EventRepository;
import eventorganiser.dir.DBMethods.DBRespositories.TeamsRepository;
import eventorganiser.dir.DBMethods.DBRespositories.UserRepository;
import eventorganiser.dir.UtilityFunctions.Maps.MapFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Controller
public class BaseController {

    private EventRepository eventRepo;
    private AttendanceRepository attendanceRepo;
    private TeamsRepository teamsRepo;
    private UserRepository userRepo;

    @Autowired
    private HttpServletRequest context; // this will provide the current instance of HttpServletRequest

    @Autowired
    public BaseController(EventRepository eventRepo, AttendanceRepository attendanceRepo, TeamsRepository teamsRepo, UserRepository userRepo){
        this.eventRepo = eventRepo;
        this.attendanceRepo = attendanceRepo;
        this.teamsRepo = teamsRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public RedirectView redirectWithUsingRedirectView() {

        return new RedirectView("/feedPage");
    }

    @GetMapping("/calendar")
    public ModelAndView Calendar(HttpSession session) {
        String username = (String)session.getAttribute("SESSION_USERNAME");
        if(username == null){
            username="Test1";
        }
        int userId = userRepo.getUserIdFromUsername(username);
        session.setAttribute("SESSION_USERID", userId);
        ModelAndView mv = new ModelAndView();
        ObjectMapper objectMapper = new ObjectMapper();
        mv.addObject("userId",userId);
        mv.setViewName("calendar");
        return mv;
    }

    @GetMapping("/addEvent")
    public ModelAndView addEvent(Event event) {
        ModelAndView mv = new ModelAndView("/addEvent");
        mv.addObject(event);
        return mv;
    }

    @GetMapping("/eventPage")
    @ResponseBody
    public ModelAndView eventPage(@RequestParam(name="eventId") int eventId, HttpSession session) throws IOException {
        ModelAndView mv = new ModelAndView("eventPage");
        int userId = Integer.parseInt(session.getAttribute("SESSION_USERID").toString());
        Event event = eventRepo.getEventDetailsByEventId(eventId);
        AttendanceRecord attendanceRecord = attendanceRepo.getAttendanceRecordFromUserIdAndEventId(userId, event.getId());
        Team team = teamsRepo.getTeamFromEventIdAndUserId(eventId,userId);
        List<User> allAttendees = eventRepo.getEventAttendeesByEventId(eventId);
        List<Team> allTeams = teamsRepo.getAllTeamsInEventByEventId(eventId);
        int teamId=-1;

        mv.addObject(event);
        mv.addObject("attendees", allAttendees);
        mv.addObject("mapDetails", MapFunctions.getEventLocAndLngFromGoogleMapsApi(MapFunctions.makeEventLocGoogleMapsApiQryStr(event)));
        mv.addObject("teamID", teamId);
        mv.addObject("teams", allTeams);

        if(attendanceRecord != null)
            mv.addObject(attendanceRecord);
        if(team != null) {
            List<User> teammates = teamsRepo.getTeamMembers(eventId, team.getTeamId());
            mv.addObject(team);
            mv.addObject("teammates", teammates);
        }
        return mv;
    }

    @GetMapping("/eventPagePopUp")
    @ResponseBody
    public ModelAndView eventPagePopUp(HttpSession session) throws IOException {
        //This view allows the popups to appear when Attendees, Team Members, Change Team or Create Team are pressed
        ModelAndView mv = new ModelAndView("eventPagePopUp");
        return mv;
    }

    @RequestMapping("/post/event/team/update")
    public String UpdateMyTeam(@RequestParam(value="eventId") Integer eventId,
                               @RequestParam(value="teamId") Integer teamId,
                               HttpSession session, HttpServletRequest request){
        int userId = Integer.parseInt(session.getAttribute("SESSION_USERID").toString());
        String referer = request.getHeader("Referer");
        eventRepo.updateUserTeam(userId, eventId, teamId);

        return "redirect:" + referer;
    }

    @RequestMapping("/post/event/team/new")
    public String CreateNewTeam(@RequestParam(value="eventId") Integer eventId,
                               @RequestParam(value="teamName") String teamName,
                               HttpServletRequest request){

        String referer = request.getHeader("Referer");
        teamsRepo.postNewTeam(eventId, teamName);

        return "redirect:" + referer;
    }

    @RequestMapping("/post/attendance")
    public String PostAttendance(@RequestParam(value = "attendanceId", required = false) int attendanceId, @RequestParam int attendanceResponse, @RequestParam int eventId, HttpSession session, HttpServletRequest request){
        int userId = Integer.parseInt(session.getAttribute("SESSION_USERID").toString());
        String referer = request.getHeader("Referer");
        if (attendanceRepo.editAttendanceRecordUserResponseToEvent(attendanceId, attendanceResponse) > 0) {
            return "redirect:" + referer;
        }

        attendanceRepo.postNewAttendanceRecord(userId, eventId, attendanceResponse);
        return "redirect:" + referer;
    }

    @GetMapping("/mapPage")
    @ResponseBody
    public ModelAndView returnMap(HttpSession session){
        //Allows the rendering of the location map in the event page
        return new ModelAndView("mapPage");
    }

    @GetMapping("/feedPage")
    public ModelAndView feedPage(HttpSession s) throws IOException, ParseException {
        String username = (String)s.getAttribute("SESSION_USERNAME");
        if(username == null){
            username="Test1";
        }
        int userId = userRepo.getUserIdFromUsername(username);
        s.setAttribute("SESSION_USERID", userId);
        ModelAndView mv = new ModelAndView("feedPage");
        boolean flag = false;
        List<AttendanceRecord> attendance = userRepo.getUserAttendanceRecordsFromUserId(userId);
        List<Event> events = eventRepo.getAllEventsInChronologicalOrder();
        List<Event> myEvents = new ArrayList<>();
        List<AttendanceRecord> allAttendance = new ArrayList<>();
        List<AttendanceRecord> myAttendance = new ArrayList<>();

        for(int i = 0;i<events.size(); i++){

            for(int j=0; j<attendance.size();j++){
                if(events.get(i).getId() == attendance.get(j).getEventId()){
                    allAttendance.add(attendance.get(j));
                    if(attendance.get(j).getResponse() == 1 || attendance.get(j).getResponse() == 3){
                        myEvents.add(events.get(i));
                        myAttendance.add(attendance.get(j));
                    }
                    flag = true;
                }
            }
            if(flag == false){
                allAttendance.add(new AttendanceRecord(0,0,0,0,0));
            }
            flag = false;
        }

        mv.addObject("allAttendance", allAttendance);
        mv.addObject("allEvents", events);
        mv.addObject("myEvents", myEvents);
        mv.addObject("myAttendance", myAttendance);
        return mv;
    }

    @GetMapping("/deleteEvent")
    public ModelAndView deletePage(HttpSession session, HttpServletRequest request) throws IOException, ParseException {
        HttpSession s = context.getSession();
        ModelAndView mv = new ModelAndView("deletePage");
        Integer userId =  (Integer)s.getAttribute("SESSION_USERID");
        if(userId == null){
            userId=1;
        }
        //Get all the events created by the User logged in
        List<Event> myEvents = eventRepo.getEventsCreatedbyUserId(userId);
        mv.addObject("myEvents", myEvents);
        return mv;
    }

    @PostMapping("/post/event")
    @ResponseBody
    public ModelAndView postEvent(@ModelAttribute newEvent event, HttpSession session) throws ParseException, IOException {
        String username = (String)session.getAttribute("SESSION_USERNAME");
        if(username == null){
            username="Test1";
        }
        Integer userId = userRepo.getUserIdFromUsername(username);
        session.setAttribute("SESSION_USERID", userId);
        ModelAndView mv = new ModelAndView();
        String startDate = event.getEventStartDate();
        String endDate = event.getEventEndDate();
        String eventTitle = event.getTitle();
        String eventDescription = event.getDescription();
        String startTime = event.getEventStartTime();
        String endTime = event.getEventEndTime();
        String address1 = event.getEventLocSt1();
        String address2 = event.getEventLocSt2();
        String city = event.getEventLocCity();
        String postCode = event.getEventLocPost();
        int poster = userId;
        int maxTeamSize = event.getMaxTeamSize();
        String eventColour = event.getBackgroundColor();
//        Date sd = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
//        Date ed = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmm");
//        LocalTime st = LocalTime.parse(startTime, dtf);
//        LocalTime et = LocalTime.parse(endTime, dtf);
//        st = st.plusHours(1);
//        et = et.plusHours(1);
//        if(ed.before(sd) || (et.isBefore(st) && sd.equals(ed))){
//            mv.setViewName("redirect:/addEvent");
//            return mv;
//        }
        String startT = startTime + ":00";
        String endT = endTime + ":00";

        Date dCreated = Date.from(Instant.now());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String stringDCreated = formatter.format(dCreated);
        System.out.println("START TIME: " + startT);
        System.out.println("END TIME: " + endT);

        eventRepo.postNewEvent(eventTitle, eventDescription, address1, address2, city, postCode, startDate, endDate,
                startT, endT, poster,maxTeamSize, stringDCreated, eventColour);

        int id = eventRepo.getEventIdFromEventNameAndDate(eventTitle, startDate);

        mv.setViewName("redirect:/eventPage?eventId=" + id);
        return mv;

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        return "redirect:/feedPage";
    }
}
