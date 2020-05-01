package eventorganiser.dir.UnitTests;

import eventorganiser.dir.DBMethods.DBClasses.AttendanceRecord;
import eventorganiser.dir.DBMethods.DBClasses.Event;
import eventorganiser.dir.DBMethods.DBClasses.Team;
import eventorganiser.dir.DBMethods.DBClasses.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
public class ClassTests {
    User user = new User();
    Team team = new Team();
    Event event = new Event();
    AttendanceRecord attendance = new AttendanceRecord();

    @Test
    public void userTestsGetSet(){
        user.setUserId(1);
        user.setFirstName("First1");
        user.setLastName("Last1");
        user.setUsername("Test1");
        user.setPassword("1234");
        user.setcPassword("1234");
        assertEquals(1, user.getUserId());
        assertEquals("Test1", user.getUsername());
        assertEquals("1234", user.getPassword());
        assertEquals("1234", user.getcPassword());
        assertEquals("First1", user.getFirstName());
        assertEquals("Last1", user.getLastName());
    }

    @Test
    public void teamTestsGetSet(){
        team.setTeamName("TestTeam");
        team.setTeamId(1);
        team.setEventId(2);
        assertEquals(1, team.getTeamId());
        assertEquals(2, team.getEventId());
        assertEquals("TestTeam", team.getTeamName());
    }

    @Test
    public void eventTestsGetSet(){
        event.setId(1);
        event.setTitle("Test Event");
        event.setUrl("http://www.testevent.com");
        event.setEventStartDate(new Date(1000));
        event.setEventStartTime(new Time(1000));
        event.setMaxTeamSize(0);
        event.setEventLocSt1("Test Street 1");
        event.setEventLocSt2("Test Drive");
        event.setEventLocCity("Test City");
        event.setEventLocPost("T357 ME");
        event.setEventEndTime(new Time(2000));
        event.setEventEndDate(new Date(2000));
        event.setBackgroundColor("red");
        event.setDateCreated(new Date(500));
        event.setDescription("Test Description");
        event.setEventOrganiser(1);

        assertEquals(1, event.getId());
        assertEquals("Test Event", event.getTitle());
        assertEquals("http://www.testevent.com", event.getUrl());
        assertEquals(new Date(1000), event.getEventStartDate());
        assertEquals(new Time(1000), event.getEventStartTime());
        assertEquals(new Date(2000), event.getEventEndDate());
        assertEquals(new Time(2000), event.getEventEndTime());
        assertFalse("Not a team event", event.getMaxTeamSize() > 0);
        assertEquals("Test Street 1", event.getEventLocSt1());
        assertEquals("Test Drive", event.getEventLocSt2());
        assertEquals("Test City", event.getEventLocCity());
        assertEquals("T357 ME", event.getEventLocPost());
        assertEquals("red", event.getBackgroundColor());
        assertEquals(new Date(500), event.getDateCreated());
        assertEquals("Test Description", event.getDescription());
        assertEquals(1, event.getEventOrganiser());

    }

    @Test
    public void newEventTestsGetSet(){
        event.setId(2);
        event.setTitle("Test Event 2");
        event.setUrl("http://www.testevent2.com");
        event.setEventStartDate(new Date(1000));
        event.setEventStartTime(new Time(1000));
        event.setMaxTeamSize(0);
        event.setEventLocSt1("Test Street 2");
        event.setEventLocSt2("Test Drive 2");
        event.setEventLocCity("Test City 2");
        event.setEventLocPost("T357 ME2");
        event.setEventEndTime(new Time(2000));
        event.setEventEndDate(new Date(2000));
        event.setBackgroundColor("red");
        event.setDateCreated(new Date(500));
        event.setDescription("Test Description 2");
        event.setEventOrganiser(2);

        assertEquals(2, event.getId());
        assertEquals("Test Event 2", event.getTitle());
        assertEquals("http://www.testevent2.com", event.getUrl());
        assertEquals(new Date(1000), event.getEventStartDate());
        assertEquals(new Time(1000), event.getEventStartTime());
        assertEquals(new Date(2000), event.getEventEndDate());
        assertEquals(new Time(2000), event.getEventEndTime());
        assertFalse("Not a team event", event.getMaxTeamSize() > 0);
        assertEquals("Test Street 2", event.getEventLocSt1());
        assertEquals("Test Drive 2", event.getEventLocSt2());
        assertEquals("Test City 2", event.getEventLocCity());
        assertEquals("T357 ME2", event.getEventLocPost());
        assertEquals("red", event.getBackgroundColor());
        assertEquals(new Date(500), event.getDateCreated());
        assertEquals("Test Description 2", event.getDescription());
        assertEquals(2, event.getEventOrganiser());

    }

    @Test
    public void attendanceTestsGetSet(){
        attendance.setAttendanceId(1);
        attendance.setEventId(2);
        attendance.setResponse(3);
        attendance.setTeamId(4);
        attendance.setUserId(5);

        assertEquals(1, attendance.getAttendanceId());
        assertEquals(2, attendance.getEventId());
        assertEquals(3, attendance.getResponse());
        assertEquals(4, attendance.getTeamId());
        assertEquals(5, attendance.getUserId());
    }

    @Test
    public void eventTestConstructor(){
        Event testEvent = new Event(1, "Test Event", "Test Description", "Test Street 1", "Test Drive", "Test City",
                "T357 ME", new Date(2000), new Date(2000), new Time(2000), new Time(2000), 1, new Date(500), 0, "black");


        assertNotEquals(null, testEvent);

    }

    @Test
    public void newEventTestConstructor(){
        Event testEvent = new Event(2, "Test Event 2", "Test Description 2", "Test Street 2", "Test Drive 2", "Test City 2",
                "T357 ME2", new Date(2000), new Date(2000), new Time(2000), new Time(2000), 2, new Date(500), 0, "black");

        assertNotEquals(null, testEvent);

    }

}
