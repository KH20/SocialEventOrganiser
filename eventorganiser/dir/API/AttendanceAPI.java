package eventorganiser.dir.API;

import eventorganiser.dir.DBMethods.DBRespositories.AttendanceRepository;
import eventorganiser.dir.DBMethods.DBRespositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AttendanceAPI {

    private AttendanceRepository attendanceRepository;
    private UserRepository userRepository;

    public AttendanceAPI() { }

    @Autowired
    public AttendanceAPI(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
    }

    //get User Attendance
    @GetMapping("/api/get/user/attendance")
    public Object APIgetUserAttendance(@RequestParam(name="userId", required=true) int userId) {
        return userRepository.getUserAttendanceRecordsFromUserId(userId);
    }

    //Get Events from user attendance
    @GetMapping("/api/get/user/attending")
    public Object apiGetUserEventsAttending(@RequestParam(name="userId") int userId){
        return attendanceRepository.getEventsUserIsAttendingByUserId(userId);
    }

    //get Event Attendance
    @GetMapping("/api/get/event/attendance")
    public Object APIgetEventAttendanceById(@RequestParam(name="eventId", required=true) int eventId) {
        return attendanceRepository.getEventAttendanceRecordsByEventId(eventId);
    }

    //get All attendance
    @GetMapping("/api/get/attendance/all")
    public Object APIgetAllAttendanceRecords() {
        return attendanceRepository.getAllAttendanceRecords();
    }

    //get  random Attendance
    @GetMapping("/api/get/attendance/random")
    public Object APIgetRandomAttendanceRecord() {
        return attendanceRepository.getRandomAttendanceRecord();
    }

    //get Attendance Record Id
    @GetMapping("/api/get/attendance")
    public Object APIgetAttendanceRecordIdFromUserIdAndEventId(@RequestParam(name="userId", required=true) int userId, @RequestParam(name="eventId", required=true) int eventId) {
        return attendanceRepository.getAttendanceRecordIdFromUserIdAndEventId(userId, eventId);
    }

    //get Attendance Record
    @GetMapping("/api/get/attendance/record")
    public Object APIgetAttendanceRecordFromUserIdAndEventId(@RequestParam(name="userId", required=true) String userId, @RequestParam(name="eventId", required=true) String eventId) {
        int userIdInt =  Integer.parseInt(userId);
        int eventIdInt =  Integer.parseInt(eventId);
        return attendanceRepository.getAttendanceRecordFromUserIdAndEventId(userIdInt, eventIdInt);
    }

    @RequestMapping(value="/api/post/attendance/update", method=RequestMethod.GET)
    public boolean updateAttendance(HttpServletRequest req,
                                    @RequestParam(name="userId") String userId,
                                    @RequestParam(name="eventId") String eventId,
                                    @RequestParam(name="response") String response,
                                    @RequestParam(name="attendanceId") String attendanceId){
        System.out.println(response +" e attendanceId:" + attendanceId);
        return 1 == attendanceRepository.editAttendanceRecordUserResponseToEvent(Integer.parseInt(attendanceId),
                                                                     Integer.parseInt(response) ); // returns true if DB post was successful
    }

    @RequestMapping(value="/api/post/attendance/new", method=RequestMethod.GET)
    public boolean postAttendance(HttpServletRequest req,
                                  @RequestParam(name="userId") String userId,
                                  @RequestParam(name="eventId") String eventId,
                                  @RequestParam(name="response") String response) {
        return 1 == attendanceRepository.postNewAttendanceRecord(Integer.parseInt(userId), Integer.parseInt(eventId),
                                                     Integer.parseInt(response)); // returns true if DB post was successful
    }

}
