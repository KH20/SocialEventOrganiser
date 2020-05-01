drop table if exists authorities;
CREATE TABLE IF NOT EXISTS `authorities` (
                                             `username` varchar(50) NOT NULL,
                                             `authority` varchar(50) NOT NULL,
                                             PRIMARY KEY (`username`,`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

drop table if exists groups;
CREATE TABLE IF NOT EXISTS `groups` (
                                        `groupId` int(11) NOT NULL AUTO_INCREMENT,
                                        `groupName` varchar(50) NOT NULL,
                                        PRIMARY KEY (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

drop table if exists users;
CREATE TABLE IF NOT EXISTS `users` (
                                       `userId` int(11) NOT NULL AUTO_INCREMENT,
                                       `username` varchar(50) DEFAULT NULL,
                                       `password` varchar(70) NOT NULL,
                                       `firstName` varchar(30) NOT NULL,
                                       `lastName` varchar(30) NOT NULL,
                                       `enabled` tinyint(4) NOT NULL,
                                       PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

drop table if exists events;
CREATE TABLE IF NOT EXISTS `events` (
                                        `eventId` int(11) NOT NULL AUTO_INCREMENT,
                                        `eventName` varchar(50) NOT NULL,
                                        `eventDesc` varchar(500) NOT NULL,
                                        `eventLocSt1` varchar(100) NOT NULL,
                                        `eventLocSt2` varchar(100) DEFAULT NULL,
                                        `eventLocCity` varchar(50) NOT NULL,
                                        `eventLocPost` varchar(10) NOT NULL,
                                        `eventStartDate` date NOT NULL,
                                        `eventEndDate` date NOT NULL,
                                        `eventStartTime` time NOT NULL,
                                        `eventEndTime` time NOT NULL,
                                        `eventOrganiser` int(11) NOT NULL,
                                        `dateCreated` date NOT NULL,
                                        `maxTeamSize` int(11) DEFAULT NULL,
                                        `eventColour` varchar(20) NOT NULL,
                                        PRIMARY KEY (`eventId`),
                                        CONSTRAINT `events_ibfk_1` FOREIGN KEY (`eventOrganiser`) REFERENCES `users` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

drop table if exists teams;
CREATE TABLE IF NOT EXISTS `teams` (
                                       `teamId` int(11) NOT NULL AUTO_INCREMENT,
                                       `eventId` int(11) DEFAULT NULL,
                                       `teamName` varchar(50) NOT NULL,
                                       PRIMARY KEY (`teamId`),
                                       CONSTRAINT `teams_ibfk_1` FOREIGN KEY (`eventId`) REFERENCES `events` (`eventId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

drop table if exists attendance;
CREATE TABLE IF NOT EXISTS `attendance` (
                                            `attendanceId` int(11) NOT NULL AUTO_INCREMENT,
                                            `userId` int(11) DEFAULT NULL,
                                            `eventId` int(11) DEFAULT NULL,
                                            `response` smallint(6) NOT NULL,
                                            `teamId` int(11) DEFAULT NULL,
                                            PRIMARY KEY (`attendanceId`),
                                            CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE CASCADE,
                                            CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`eventId`) REFERENCES `events` (`eventId`) ON DELETE CASCADE,
                                            CONSTRAINT `attendance_ibfk_3` FOREIGN KEY (`teamId`) REFERENCES `teams` (`teamId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;
