package eventorganiser.dir.IntegrationTests.APITests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AttendanceTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAPIGetAttendance() throws Exception {
        mockMvc.perform(get("/api/get/user/attendance?userId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].response").value("3"));
    }

    @Test
    public void testAPIGetEventsAttending() throws Exception {
        mockMvc.perform(get("/api/get/user/attending?userId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("Test Event 1"));
    }

    @Test
    public void testAPIGetEventAttendance() throws Exception {
        mockMvc.perform(get("/api/get/event/attendance?eventId=2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].response").value("3"));
    }

    @Test
    public void testAPIGetAllAttendance() throws Exception {
        mockMvc.perform(get("/api/get/attendance/all")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[3].attendanceId").value("4"));
    }

    @Test
    public void testAPIGetRandomAttendance() throws Exception {
        mockMvc.perform(get("/api/get/attendance/random")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testAPIGetAttendanceIdFromUserIdAndEventId() throws Exception {
        mockMvc.perform(get("/api/get/attendance?userId=1&eventId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").value("1"));
    }

    @Test
    public void testAPIGetAttendanceRecordFromUserIdAndEventId() throws Exception {
        mockMvc.perform(get("/api/get/attendance/record?userId=1&eventId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.attendanceId").value("1"));
    }

    @Test
    public void testAPIUpdateAttendance() throws Exception {
        mockMvc.perform(get("/api/post/attendance/update?userId=1&eventId=1&response=3&attendanceId=1").with(csrf())).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testAPICreateAttendance() throws Exception {
        mockMvc.perform(get("/api/post/attendance/new?userId=4&eventId=1&response=1").with(csrf())).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

}

