package eventorganiser.dir.IntegrationTests.APITests;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAPIGetEvents() throws Exception {
        mockMvc.perform(get("/api/get/events")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("Test Event 1"));
    }

    @Test
    public void testAPIGetEventsChronological() throws Exception {
        mockMvc.perform(get("/api/get/events?sortBy=chronological")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("Test Event 2"));
    }

    @Test
    public void testAPIGetEventById() throws Exception {
        mockMvc.perform(get("/api/get/event?eventId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Event 1"));
    }

    @Test
    public void testAPIGetEventIdByNameAndDate() throws Exception {
        mockMvc.perform(get("/api/get/eventId?eventName=Test Event 1&date=2020-10-06")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").value("1"));
    }

    @Test
    public void testAPIGetUserCreatedEventsByUserId() throws Exception {
        mockMvc.perform(get("/api/get/events/created?userId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("Test Event 1"));
    }

    @Test
    public void testAPIGetEventAttendanceByEventId() throws Exception {
        mockMvc.perform(get("/api/get/eventAttendance?eventId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].attendanceId").value("1"));
    }

    @Test
    public void testAPIGetEventAttendeesByEventId() throws Exception {
        mockMvc.perform(get("/api/get/event/attendees?eventId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username").value("Test4"));
    }

    @Test
    public void testAPIGetEventTeamsByEventId() throws Exception {
        mockMvc.perform(get("/api/get/event/teams?eventId=3")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].teamName").value("Team 1"));
    }

    @Test
    @WithMockUser(username = "Test1", password = "1234", roles = "USER")
    public void testCalendarPageLoads() throws Exception {
        mockMvc.perform(get("/calendar")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Test1", password = "1234", roles = "USER")
    public void testFeedPageLoads() throws Exception {
        mockMvc.perform(get("/feedPage")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Test1", password = "1234", roles = "USER")
    public void testDeletePageLoads() throws Exception {
        mockMvc.perform(get("/deleteEvent")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Test1", password = "1234", roles = "USER")
    public void testAddEventPage() throws Exception {
        mockMvc.perform(get("/addEvent")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Test1", password = "1234", roles = "USER")
    public void testAPIPostEvent() throws Exception {

        mockMvc.perform(post("/post/event?title=testEvent&eventStartDate=2020-11-26&eventEndDate=2020-11-26" +
                "&eventStartTime=10:00&eventEndTime=11:00&eventLocSt1=address1&eventLocSt2=address2&eventLocCity=cardiff" +
                "&eventLocPost=123456&description=newEvent&maxTeamSize=0&backgroundColor=black").with(csrf())).andDo(print()).andExpect(status().isFound())
        .andExpect(redirectedUrlPattern("/eventPage?*"));
    }




}

