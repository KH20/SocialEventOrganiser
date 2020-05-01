package eventorganiser.dir.API;

import eventorganiser.dir.DBMethods.DBClasses.Event;
import eventorganiser.dir.DBMethods.DBRespositories.AttendanceRepository;
import eventorganiser.dir.DBMethods.DBRespositories.EventRepository;
import eventorganiser.dir.DBMethods.DBRespositories.TeamsRepository;
import eventorganiser.dir.DBMethods.DBRespositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class EventsAPI {

    private EventRepository eventRepository;
    private TeamsRepository teamsRepository;
    private UserRepository userRepository;
    private AttendanceRepository attendanceRepository;

    @Autowired
    public EventsAPI(EventRepository eventRepository, TeamsRepository teamsRepository,
                     UserRepository userRepository, AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
        this.eventRepository = eventRepository;
        this.teamsRepository = teamsRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/get/events")
    public Object getEvents(@RequestParam(value="sortBy", defaultValue = "") String sort, @RequestParam(value="userId", defaultValue = "") Integer userId){
        List<Event> allEvents = null;

        if(sort.isEmpty() || sort.isBlank()) {
            allEvents = eventRepository.getAllEvents();
        }
        else if(sort.equals("chronological") && userId == null){
            allEvents = eventRepository.getAllEventsInChronologicalOrder();
            System.out.println("");
        }else if (sort.equals("chronological")){
            allEvents = userRepository.getFutureUserEventsFromUserIdInChronologicalOrder(userId);
        }
        return allEvents;
    }

    @GetMapping("/api/get/event")
    public Object getEventById(@RequestParam(value="eventId", required = true) int eventId) {
        Object event = eventRepository.getEventDetailsByEventId(eventId);
        return event;
    }

    @GetMapping("/api/get/eventId")
    public Object getEventIDByNameAndDate(@RequestParam(value="eventName") String name, @RequestParam(value="date") String date) {
        return eventRepository.getEventIdFromEventNameAndDate(name,date);
    }

    @GetMapping("/api/get/events/created")
    public Object getEventsCreatedbyUserId(@RequestParam(value="userId") Integer userId){
        return eventRepository.getEventsCreatedbyUserId(userId);
    }

    @GetMapping("/api/get/eventAttendance")
    @ResponseBody
    public Object getEventAttendanceById(@RequestParam(value="eventId") int id) {
        return attendanceRepository.getEventAttendanceRecordsByEventId(id);
    }

    @GetMapping("/api/get/event/attendees")
    public Object getEventAttendeesByEventId(@RequestParam(value="eventId") int id){
        return eventRepository.getEventAttendeesByEventId(id);
    }

    @GetMapping("/api/get/event/teams")
    public Object getEventTeams(@RequestParam(value="eventId") Integer id) {
        return teamsRepository.getAllTeamsInEventByEventId(id);
    }

    @DeleteMapping("api/delete/event")
    public boolean deleteEvents(@RequestParam(value="eventId") Integer eventId){
        return eventRepository.deleteEvents(eventId) == 1;
    }


    // Catches incorrect format when user inputs date and time
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        binder.registerCustomEditor(Time.class, new CustomDateEditor(timeFormat, false));
    }

}
