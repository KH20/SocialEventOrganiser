package eventorganiser.dir.DBMethods.DBRespositories;

import eventorganiser.dir.DBMethods.DBClasses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbctemplate) {
        this.jdbcTemplate = jdbctemplate;
    }

    /////////////////////////////////////// GET ALL METHODS ///////////////////////////////////////

    public List<User> getAllUsers() throws DataAccessException {
        return jdbcTemplate.query("select * from users", new Object[]{},
                (rs, i) ->
                        new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"), rs.getString("password"),
                                rs.getString("firstName"), rs.getString("lastName") )
        );
    }

    /////////////////////////////////////// GET ID METHODS ///////////////////////////////////////

    public Integer getUserIdFromUsername(String username) throws DataAccessException {
        String str = String.format("SELECT userId FROM users WHERE username = '%s' LIMIT 1;", username);
        List<Integer> usrId = jdbcTemplate.query(str, new Object[]{},
                (rs, i) -> rs.getInt("userId") );
        return usrId.get(0);
    }

    public String getUserFullNAmeFromUsername(String username) throws DataAccessException {

        String firstname = String.format("SELECT firstName FROM users WHERE username = '%s' LIMIT 1;", username);
        String lastname = String.format("SELECT lastName FROM users WHERE username = '%s' LIMIT 1;", username);
        List<String> fname = jdbcTemplate.query(firstname, new Object[]{},
                (rs, i) -> rs.getString("firstName") );
        List<String> lname = jdbcTemplate.query(lastname, new Object[]{},
                (rs, i) -> rs.getString("lastName") );
        String fullname = fname.get(0) + " " + lname.get(0);
        return fullname;
    }

    /////////////////////////////////////// GET USER DETAILS METHODS ///////////////////////////////////////

    public List<User> getUserDetailsFromId(int userId) throws DataAccessException {
        String str = String.format("SELECT * FROM users WHERE userId = '%d' LIMIT 1", userId);
        return jdbcTemplate.query(str, new Object[]{},
                (rs, i) -> new User(rs.getInt("userId"),
                        rs.getString("username"), rs.getString("firstName"),
                        rs.getString("lastName"))
        );
    }
    public List<AttendanceRecord> getUserAttendanceRecordsFromUserId(int userId) throws DataAccessException {
        String str = String.format("SELECT * FROM attendance WHERE userId = '%s'", userId);
        return jdbcTemplate.query(str, new Object[]{},
                (rs, i) -> new AttendanceRecord(rs.getInt("attendanceId"), rs.getInt("userId"),
                        rs.getInt("eventId"), rs.getInt("response"),
                        rs.getInt("teamId") )
        );
    }

    public List<Event> getFutureUserEventsFromUserIdInChronologicalOrder(int userId) {
        String str = new StringBuilder()
                .append("SELECT * "                                                          )
                .append("FROM events "                                                       )
                .append("WHERE eventId IN (SELECT eventId "                                  )
                .append(                  "FROM attendance "                                 )
                .append(                  "WHERE userId = " + userId + " AND response != 2)" )
                .append("AND eventStartDate >= CURRENT_DATE() "                  )
                .append("ORDER BY eventStartDate "                               )
                .toString();
        List<Event> eventLi = jdbcTemplate.query(str, new Object[]{},
                (rs, i) ->
                        new Event(rs.getInt("eventId"), rs.getString("eventName"),
                                rs.getString("eventDesc"), rs.getString("eventLocSt1"),
                                rs.getString("eventLocSt2"), rs.getString("eventLocCity"),
                                rs.getString("eventLocPost"), rs.getDate("eventStartDate"),
                                rs.getDate("eventEndDate"),rs.getTime("eventStartTime"),
                                rs.getTime("eventEndTime"), rs.getInt("eventOrganiser"),
                                rs.getDate("dateCreated"), rs.getInt("maxTeamSize"),
                                rs.getString("eventColour")) );
        if (eventLi.isEmpty()) {return null; }
        else { return eventLi; }
    }

//    public List<Event> getPastUserEventsFromUserIdInReverseChronologicalOrder(int userId) {
//        String str = new StringBuilder()
//                .append("SELECT * "                                         )
//                .append("FROM events "                                      )
//                .append("WHERE eventId IN (SELECT eventId "                 )
//                .append(                  "FROM attendance "                )
//                .append(                  "WHERE userId = " + userId + ") " )
//                .append("AND eventDate < CURRENT_DATE() "                   )
//                .append("ORDER BY eventDate DESC "                          )
//                .toString();
//        List<Event> eventLi = jdbcTemplate.query(str, new Object[]{},
//                (rs, i) ->
//                        new Event(rs.getInt("eventId"), rs.getString("eventName"),
//                                rs.getString("eventDesc"), rs.getString("eventLocSt1"),
//                                rs.getString("eventLocSt2"), rs.getString("eventLocCity"),
//                                rs.getString("eventLocPost"), rs.getDate("eventStartDate"),
//                                rs.getDate("eventEndDate"),rs.getTime("eventStartTime"),
//                                rs.getTime("eventEndTime"), rs.getInt("eventOrganiser"),
//                                rs.getDate("dateCreated"), rs.getInt("maxTeamSize"),
//                                rs.getString("eventColour")) );
//        if (eventLi.isEmpty()) {return null; }
//        else { return eventLi; }
//    }

    public List<Team> getUserTeamsFromUserId(int userId) throws DataAccessException {
        String str = "select teams.teamId, teams.eventId, teams.teamName from teams " +
        "inner join attendance on teams.teamId = attendance.teamId " +
        "inner join users on users.userId = attendance.userId " +
        "where users.userId =" + userId;

        List<Team> listOfTeams =  jdbcTemplate.query(str, new Object[]{},
                (rs, i) -> new Team(rs.getInt("teamId"), rs.getInt("eventId"),
                        rs.getString("teamName")));
        if(listOfTeams.isEmpty())
            return null;
        else
            return listOfTeams;

    }

    public Team getUserTeamForEventByUserIdAndEventId(int userId, int eventId) throws DataAccessException {
        String str = "select teams.teamId, teams.eventId, teams.teamName from teams " +
                "inner join attendance on teams.teamId = attendance.teamId " +
                "inner join users on users.userId = attendance.userId " +
                "inner join events on events.eventId = attendance.eventId " +
                "where users.userId = " + userId + " and events.eventId = " + eventId + " limit 1";

        List<Team> teamList = jdbcTemplate.query(str, new Object[]{},
                (rs, i) -> new Team(rs.getInt("teamId"),
                        rs.getInt("eventId"),
                        rs.getString("teamName")));
        if(teamList.isEmpty()){
            return null;
        }
        else{
            return teamList.get(0);
        }

    }

//    public List<Group> getUserGroupsFromUserId(int userId) throws DataAccessException {
//        String str = String.format("SELECT groupId FROM group_members WHERE userId = '%s'", userId);
//        List<String> groupIdLi = jdbcTemplate.query(str, new Object[]{},
//                (rs, i) -> ( (Integer) rs.getInt("groupId")).toString() );
//        if ( groupIdLi.isEmpty() ) {return null; };
//        String insertStr = String.join(", ", groupIdLi);
//        String str2 = String.format("SELECT * FROM groups WHERE groupId IN (%s)", insertStr);
//        List<Group> groupLi = jdbcTemplate.query(str2, new Object[]{},
//                (rs, i) -> new Group(rs.getInt("groupId"), rs.getString("groupName") )
//        );
//        if ( groupLi.isEmpty() ){ return null; }
//        else { return groupLi; }
//    }

    /////////////////////////////////////// POST METHODS ///////////////////////////////////////

//    public int postNewUser (String username, String password, int enabled, String firstName, String lastName) throws DataAccessException {
//        String str = "INSERT INTO users(username, password, enabled, firstName, lastName) VALUES(?,?,?,?,?)";
//        return jdbcTemplate.update(str, username, password, enabled, firstName, lastName);
//    }

    /////////////////////////////////////// DELETE METHODS ///////////////////////////////////////

//    public int deleteUserById (int userId) throws DataAccessException {
//        String str = "DELETE FROM users WHERE username = ?";
//        return jdbcTemplate.update(str, userId);
//    }

    /////////////////////////////////////// GET RANDOM ROW METHODS ///////////////////////////////////////

    public User getRandomUser()throws DataAccessException {
        String str = "SELECT * FROM users ORDER BY RAND() LIMIT 1";
        List<User> usrLi = jdbcTemplate.query(str, new Object[]{},
                (rs, i) -> new User(rs.getInt("userId"), rs.getString("username"),
                        rs.getString("firstName"), rs.getString("lastName") ) );
        return usrLi.get(0);
    }

    /*
    Method to return whether a username already exists. Used as part of the registration process
    */
    public boolean doesUserNameExist(String u) {
        return (jdbcTemplate.queryForList("select username from users where username = ?", new Object[]{u})).size() > 0;
    }

    /*
    Add authority information for a user. Authorities refer to user groups which
    can have different levels of security access. Here all users are part of a
    single group, but this could be extended to allow some users to also be
    administrators, or other types of additional privilege.
    */
    private int _addToAuthoritiesTable(User u){
        String sql2 = "insert into authorities(username, authority) values(?,?)";
        return jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql2);
                ps.setString(1, u.getUsername());
                ps.setString(2, "ROLE_USER");
                return ps;
            }
        });
    }

    /*
        Add a new user to the database.
        User data is added across two tables, which have been split into separate methods here.
    */
    public boolean addUser(User u) {
        return _addToUserTable(u) > 0 && _addToAuthoritiesTable(u) > 0;
    }

//    public User findUser(String user){
//        List<User> userList = jdbcTemplate.query("select username from users where username='" + "'" +" limit 1",
//                new Object[]{},
//                (rs, i) -> new User(
//                        rs.getString(user)
//                )
//        );
//        return userList.get(0);
//    }

    @Bean //https://www.baeldung.com/spring-bean
    public BCryptPasswordEncoder passwordEncoder2() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


    /*
    Add a new user to the users table in the database using a User object passed
    from RegController which is built from what the user submits as part of the
    registration form.
    */
    private int _addToUserTable(User u){
        String sql = "insert into users(username, password, firstName, lastName, enabled) values(?,?,?,?,?)";
        return jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql);
                //unlike the databases learning resources surrounding tv shows, the user table here does not contain
                // an auto-incrementing field named id, otherwise a second attribute would be added to the line
                // above --> new String[]{"id"}
                ps.setString(1, u.getUsername());
                ps.setString(2, passwordEncoder2().encode(u.getPassword()));
                ps.setString(3, u.getFirstName());
                ps.setString(4, u.getLastName());
                ps.setInt(5, 1);
                return ps;
            }
        });
    }

}