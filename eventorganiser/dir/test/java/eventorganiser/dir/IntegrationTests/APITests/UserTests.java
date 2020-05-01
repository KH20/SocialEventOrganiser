package eventorganiser.dir.IntegrationTests.APITests;

import eventorganiser.dir.DBMethods.DBClasses.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAPIGetUserByUsername() throws Exception {
        //Test that checks singular JSON response
        mockMvc.perform(get("/api/get/user?username=Test2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").value("2"));
    }

    @Test
    public void testAPIGetAllUsers() throws Exception {
        //Test that checks JSON array response
        mockMvc.perform(get("/api/get/users")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username").value("Test1"));
    }

    @Test
    public void testAPIGetUserFullNameByUsername() throws Exception {
        mockMvc.perform(get("/api/get/userfullname?username=Test3")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Test 3")));
    }

    @Test
    public void testAPIGetUserDetailsByUserId() throws Exception {
        mockMvc.perform(get("/api/get/user/details?userId=4")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Test4")));
    }

    @Test
    public void testAPIGetRandomUser() throws Exception {
        mockMvc.perform(get("/api/get/user/random")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testAPIGetUserTeamsByUserId() throws Exception {
        mockMvc.perform(get("/api/get/user/teams?userId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Team 1")));
    }

    @Test
    public void testAPIGetUserTeamForEventByUserIdAndEventId() throws Exception {
        mockMvc.perform(get("/api/get/userteams/events?userId=1&eventId=3")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.teamName").value("Team 1"));
    }

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(post("/register?username=NewUser&password=1234&cPassword=1234&firstName=New&lastName=User").with(csrf())).andDo(print()).andExpect(status().isFound());
        mockMvc.perform(get("/api/get/user?username=NewUser")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testRegErr() throws Exception {
        ArrayList<String> errArray = new ArrayList<>();
        errArray.add("Username already exists");
        mockMvc.perform(post("/register?username=NewUser&password=1234&cPassword=1234&firstName=New&lastName=User").with(csrf()))
                .andDo(print()).andExpect(model().attribute("errors", errArray));
    }

}

