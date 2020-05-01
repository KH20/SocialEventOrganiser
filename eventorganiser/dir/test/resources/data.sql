insert into users values(1, 'Test1', '1234', 'Test', '1', 1);
insert into users values(2, 'Test2', '1234', 'Test', '2', 1);
insert into users values(3, 'Test3', '1234', 'Test', '3', 1);
insert into users values(4, 'Test4', '1234', 'Test', '4', 1);
insert into events values(1, 'Test Event 1', 'Description of Test Event 1', 'Castle Park', 'Broadmead', 'Bristol', 'BS1 3XB', '2020-10-06', '2020-10-06', '12:00:00', '14:00:00', 1, '2020-11-03', 0, 'red');
insert into events values(2, 'Test Event 2', 'Description of Test Event 2', 'National Software Academy', 'Queensway', 'Newport', 'NP20 4AX', '2020-09-05', '2020-09-05', '09:30:00', '16:30:00', 2, '2020-11-03', 0, 'green');
insert into events values(3, 'Test Team Event 1', 'Description of Test Team Event 1', 'Cardiff University', 'Cardiff', 'Cardiff', 'CF10 3AT', '2020-03-04', '2020-04-04', '12:00:00', '12:00:00', 3, '2020-11-03', 3, 'black');
insert into teams values(1, 3, 'Team 1');
insert into teams values(2, 3, 'Team 2');
insert into attendance (attendanceId, userId, eventId, response) values(1, 1, 1, 1);
insert into attendance (attendanceId, userId, eventId, response) values(2, 1, 3, 1);
insert into attendance (attendanceId, userId, eventId, response) values(3, 2, 2, 3);
insert into attendance (attendanceId, userId, eventId, response, teamId) values(4, 2, 3, 1, 2);
insert into attendance (attendanceId, userId, eventId, response, teamId) values(5, 1, 3, 1, 1);
insert into attendance (attendanceId, userId, eventId, response) values(6, 3, 2, 2);
insert into attendance (attendanceId, userId, eventId, response, teamId) values(7, 4, 3, 1, 1);




