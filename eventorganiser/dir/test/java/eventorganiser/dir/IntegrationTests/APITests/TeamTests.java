package eventorganiser.dir.IntegrationTests.APITests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TeamTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAPIGetAllTeams() throws Exception {
        mockMvc.perform(get("/api/get/teams")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].teamName").value("Team 2"));
    }

    @Test
    public void testAPIGetRandomTeam() throws Exception {
        mockMvc.perform(get("/api/get/team/random")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testAPIGetTeamIdByEventIdAndUserId() throws Exception {
        mockMvc.perform(get("/api/get/teamId?eventId=3&userId=2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").value("2"));
    }

    @Test
    public void testAPIGetTeamIdByEventIdAndTeamName() throws Exception {
        mockMvc.perform(get("/api/get/event/teamId?teamName='Team 2'&eventId=3")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").value("2"));
    }

    @Test
    public void testAPIGetTeammatesByEventIdAndTeamId() throws Exception {
        mockMvc.perform(get("/api/get/event/teammates?eventId=3&teamId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].userId").value("4"));
    }

    @Test
    public void testAPIGetTeamNameById() throws Exception {
        mockMvc.perform(get("/api/get/team/name?teamId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Team 1")));
    }

    @Test
    @WithMockUser(username = "Test1", password = "1234", roles = "USER")
    public void testAPIPostTeam() throws Exception {
        mockMvc.perform(post("/post/event/team/new?eventId=3&teamName=Test Team 1").with(csrf())).andDo(print()).andExpect(status().isFound());
        mockMvc.perform(get("/api/get/event/teamId?teamName='Test Team 1'&eventId=3")).andDo(print()).andExpect(status().isOk());
    }

}

