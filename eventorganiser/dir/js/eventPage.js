// <script async th:inline="javascript">
//     // $(document).ready(function() {
//     //  console.log("TEST");
//     // function storeCurrentEventAndUserDataInLocalStorage() {
//     //   // Script for storing event & user data on the user's browser
//     //   localStorage.setItem("userId", [[${userId}]]);
//     //   localStorage.setItem("eventId", [[${eventId}]]);
//     //   if ([[${isTeamEvent}]]) {
//     //     localStorage.setItem("teamId", [[${teamId}]]);
//     //   }
//     //   if ([[${attendance}]] !== '0') {
//     //     localStorage.setItem("attendanceId", [[${attendanceId}]]);
//     //   }
//     // }
//     //
//     // storeCurrentEventAndUserDataInLocalStorage();
//     //
//     // function keepDivDescBoxAndDivMapBoxTheSameHeight() {    // Script for resizing Divs display boxes so that they're consistently the same height
//     //   function setHeight(box1, box2) {
//     //     var height = box1.height();
//     //     box2.css('height', height);
//     //   }
//     //
//     //   $(window).on('resize', function () {
//     //     if ($(window).width() > 700) {      // Media query
//     //       setHeight($('#eventDetailsBox'), $('#eventLocBox'));
//     //     } else {      // If page width is tablet/mobile, heights no longer need to match, so setting both to auto
//     //       $('#eventLocBox').css("height", "auto");
//     //       $('#eventDetailsBox').css("height", "auto");
//     //     }
//     //   });
//     //   setHeight($('#eventDetailsBox'), $('#eventLocBox'));
//     // }
//
//     // keepDivDescBoxAndDivMapBoxTheSameHeight();
//
//     // function styleChangesForTeamEvents() {  // Script for altering the display for team events
//     //   if ([[${isTeamEvent}]]) {   // Syntax for accessing Thymeleaf variables in JS
//     //     $(".eventTitleBoxInner").css({
//     //       "border": "2.5px solid rgba(0,0,128,0.9)",
//     //       "box-shadow": "0.5px 0.5px 25px rgba(0,0,128,0.4)"
//     //     });
//     //   }
//     // }
//     //
//     // styleChangesForTeamEvents();
//     //
//     // function attendanceButtonsFunctions() {
//     //   // Script for altering the display of the attendance buttons based on the users response to the event (if they've already made a response)
//     //   // And for sending new/updated attendance responses
//     //
//     //   var clsButtons = $("[class^='attendanceButtons']");
//     //   var token = $("meta[name='_csrf']").attr("content");    // Used to bypass SPring Boot's CSRF protocol     -- SOlution taken from 'https://stackoverflow.com/questions/34747437/use-of-spring-csrf-with-ajax-rest-call-and-html-page-with-thymeleaf' on Nov 26th 2019
//     //   var header = $("meta[name='_csrf_header']").attr("content");    // Used to bypass SPring Boot's CSRF protocol
//
//     // // CSS METHODS
//     // function updateAttdButtonsCSS() {
//     //   clsButtons.each(function (i, btn) {
//     //     var cls = $(this).attr('class');
//     //     if (/ SELECTED/.test(cls)) {
//     //       $(this).css({"background-color": "lightgrey", "box-shadow": "0px 0px 3px"});  // Updating style of selected attendance status
//     //       $(this).attr('class', 'attendanceButtons'); // Removing ' SELECTED' from class name
//     //     } else {
//     //       $(this).css({"background-color": "transparent", "box-shadow": "0px 0px 0px"}); // Updating style of non-selected buttons
//     //     }
//     //   });
//     // }
//     //
//     // updateAttdButtonsCSS();
//     //
//     // function updateSelectedUserAttdButton() {  // Adds 'SELECTED' to button class name if a new post request is successfully submitted to the DB
//     //   var btnPressed = "#" + localStorage.getItem("lstAttdBtnHit");
//     //   $(btnPressed).attr('class', 'attendanceButtons SELECTED');
//     // }
//
//     //HTTP REQUEST METHODS
//     // function postAttdStatus(response) {
//     //   var baseUri = "/post/attendance";
//     //   var userId = "userId=" + localStorage.getItem("userId");
//     //   var eventId = "&eventId=" + localStorage.getItem("eventId");
//     //   var res = "&response=" + response;
//     //   var fullUri = baseUri + "?" + userId + eventId + res;
//     //   if ([[${attendance}]] !== '0') {
//     //     fullUri += "&attendanceId=" + localStorage.getItem("attendanceId");
//     //   }
//     // $.ajax({
//     //   type: "POST",
//     //   url: fullUri,
//     //   beforeSend: function (xhr) {
//     //     xhr.setRequestHeader(header, token);
//     //   },
//     //   success: function (response) {
//     //     if (response === true) {
//     //       location.reload();
//     //     } else {
//     //       alert("There was an error, please try again.")
//     //     }
//     //   },
//     //   error: function (data) {
//     //     alert("FAIL");
//     //     alert(data.responseText);
//     //   }
//     // });
//     // }
//
//     // BUTTON LISTENERS
//     // clsButtons.on("click", function () {     // When an attendance button is clicked, which button is clicked is determined before attempting to post the user's request
//     //   localStorage.setItem("lstAttdBtnHit", $(this).attr('id'));
//     //   var response;
//     //   switch ($(this).attr('id')) {
//     //     case 'AB1':
//     //       response = '1';
//     //       break;
//     //     case 'AB2':
//     //       response = '2';
//     //       break;
//     //     case 'AB3':
//     //       response = '3';
//     //       break;
//     //   }
//     //   postAttdStatus(response);
//     // });
//     //   }
//
//     // attendanceButtonsFunctions();
//
//     function eventPagePopUpFunctions() {
//         var token = $("meta[name='_csrf']").attr("content");    // Used to bypass SPring Boot's CSRF protocol     -- SOlution taken from 'https://stackoverflow.com/questions/34747437/use-of-spring-csrf-with-ajax-rest-call-and-html-page-with-thymeleaf' on Nov 26th 2019
//         var header = $("meta[name='_csrf_header']").attr("content");    // Used to bypass SPring Boot's CSRF protocol
//         var popup = $('#popUpBox');
//
//         function openIframe() {
//             popup.css("display", "block");
//         }
//
//         function initIframe(source) {
//             localStorage.setItem("popup", source);
//             document.getElementById('popUpBox').contentWindow.changePopup();
//             openIframe();
//         }
//
//         // function getEventAttendees() {
//         //   var uri = "/api/get/event/attendees" + "?eventid=" + localStorage.getItem("eventId");
//         //   $.ajax({
//         //     type: "GET",
//         //     url: uri,
//         //     beforeSend: function (xhr) {
//         //       xhr.setRequestHeader(header, token);
//         //     },
//         //     success: function (response) {
//         //       var attendeesLi = [];
//         //       for (var i in response) {
//         //         attendeesLi.push(response[i]['firstName'] + " " + response[i]['lastName'])
//         //       }
//         //       localStorage.setItem("eventPageEventAttendees", attendeesLi);
//         //       document.getElementById('popUpBox').contentWindow.addAttendeesToDisplay();
//         //       initIframe("viewAttendeesPopUp");
//         //     },
//         //     error: function (response) {
//         //       alert("FAIL");
//         //       alert(response.responseText);
//         //     }
//         //   });
//         // }
//         //
//         //     function getEventTeammates() {
//         //       var uri = "/api/get/event/teammates" + "?eventid=" + localStorage.getItem("eventId")
//         //               + "&teamid="  + localStorage.getItem("teamId");
//         //       $.ajax({
//         //         type: "GET",
//         //         url: uri,
//         //         beforeSend: function (xhr) {
//         //           xhr.setRequestHeader(header, token);
//         //         },
//         //         success: function (teamMatesLi) {
//         //           var teamMatesArr = [];
//         //           if(localStorage.getItem("teamId") !== "0"){
//         //             for (var i in teamMatesLi) {
//         //               var teamMate = teamMatesLi[i]['firstName'] + " " + teamMatesLi[i]['lastName'];
//         //               teamMatesArr.push(teamMate);
//         //             }
//         //             if(teamMatesArr.length === 0){
//         //               teamMatesArr.push("No Teammates to show");
//         //             }
//         //
//         //             localStorage.setItem("eventPageTeamMates", teamMatesArr.join(", "));
//         //             document.getElementById('popUpBox').contentWindow.addTeammatesToDisplay();
//         //             initIframe("viewTeammatesPopUp");
//         //           }
//         //           else{
//         //             alert("Sorry! You must join a team first");
//         //           }
//         //
//         //         },
//         //         error: function (response) {
//         //           alert("Sorry! You must join a team first");
//         //         }
//         //       });
//         //     }
//         //
//         //     $("#seeAttendeesButton").on("click", function () {
//         //       getEventAttendees();
//         //     });
//         //
//         //     $("#viewTeammatesButton").on("click", function () {
//         //       getEventTeammates();
//         //     });
//         //
//         //     $("#changeTeamButton").on("click", function () {
//         //       console.log("EVENT ID:" + localStorage.getItem("eventId"));
//         //       $.ajax({
//         //         type: "GET",
//         //         url: "/api/get/event/teams?eventid=" + localStorage.getItem("eventId"),
//         //
//         //         beforeSend: function (xhr) {
//         //           xhr.setRequestHeader(header, token);
//         //         },
//         //         success: function (teamsLi) {
//         //           var teamsArr = [];
//         //           // console.log("TEAMS LIST:" + teamsLi);
//         //           for (var i=0;i<teamsLi.length;i++) {
//         //             // console.log("NEW TEAM:" + teamsLi[i]['teamName']);
//         //             teamsArr.push(teamsLi[i]['teamName']);
//         //           }
//         //           // console.log("ARRAY:" + teamsArr);
//         //
//         //           if(teamsArr.length === 0){
//         //             teamsArr.push("No Teams to show");
//         //           }
//         //
//         //           localStorage.setItem("eventPageTeams", teamsArr.join(", "));
//         //           document.getElementById('popUpBox').contentWindow.addTeamsForEventToDisplay();
//         //           initIframe("changeTeamPopUp");
//         //         },
//         //         error: function (response) {
//         //           alert(response.text);
//         //         }
//         //       });
//         //     });
//         //
//         //     $("#createTeamButton").on("click", function () {
//         //       initIframe("createTeamPopUp");
//         //     });
//         //   }
//         //
//         //   eventPagePopUpFunctions();
//         // });
//
//         function closeIFrame() {
//             $('#popUpBox').css("display", "none");
//         }}
//
//     </script>

var attendeesOpen = false;
var teammatesOpen = false;
var changeTeamOpen = false;
var createTeamOpen = false;

var getAttendeesButton = document.getElementById("seeAttendeesButton");
getAttendeesButton.addEventListener("click", showAttendees);
var attendeesClose = document.getElementById("attendeesClose");
attendeesClose.addEventListener("click", closeAttendees);

var getTeammatesButton = document.getElementById("seeTeammatesButton");
getTeammatesButton.addEventListener("click", showTeammates);
var teammatesClose = document.getElementById("teammatesClose");
teammatesClose.addEventListener("click", closeTeammates);

var getChangeTeamButton = document.getElementById("seeChangeTeamButton");
getChangeTeamButton.addEventListener("click", showChangeTeam);
var changeTeamClose = document.getElementById("changeTeamClose");
changeTeamClose.addEventListener("click", closeChangeTeam);

var getCreateTeamButton = document.getElementById("seeCreateTeamButton");
getCreateTeamButton.addEventListener("click", showCreateTeam);
var createTeamClose = document.getElementById("createTeamClose");
createTeamClose.addEventListener("click", closeCreateTeam);

function showAttendees(){
    closeTeammates();
    closeChangeTeam();
    closeCreateTeam();

    if(!attendeesOpen){
        var attendeesDiv = document.getElementById("attendeesDiv");
        attendeesDiv.style.visibility="visible";
        attendeesOpen = true;
    }
    else{
        closeAttendees();
    }
}

function closeAttendees(){
    var attendeesDiv = document.getElementById("attendeesDiv");
    attendeesDiv.style.visibility="hidden";
    attendeesOpen = false;
}

function showTeammates(){
    closeAttendees();
    closeChangeTeam()
    closeCreateTeam();

    if(!teammatesOpen){
        var teammatesDiv = document.getElementById("teammatesDiv");
        teammatesDiv.style.visibility="visible";
        teammatesOpen = true;
    }
    else{
        closeTeammates();
    }
}

function closeTeammates(){
    var teammatesDiv = document.getElementById("teammatesDiv");
    teammatesDiv.style.visibility="hidden";
    teammatesOpen = false;
}

function showChangeTeam(){
    closeAttendees();
    closeTeammates();
    closeCreateTeam();
    if(!changeTeamOpen){
        var changeTeamDiv = document.getElementById("changeTeamDiv");
        changeTeamDiv.style.visibility="visible";
        changeTeamOpen = true;
    }
    else{
        closeChangeTeam();
    }
}

function closeChangeTeam(){
    var changeTeamDiv = document.getElementById("changeTeamDiv");
    changeTeamDiv.style.visibility="hidden";
    changeTeamOpen = false;
}

function showCreateTeam(){
    closeAttendees();
    closeTeammates();
    closeChangeTeam();

    if(!createTeamOpen){
        var createTeamDiv = document.getElementById("createTeamDiv");
        createTeamDiv.style.visibility="visible";
        createTeamOpen = true;
    }
    else{
        closeCreateTeam();
    }
}

function closeCreateTeam(){
    var createTeamDiv = document.getElementById("createTeamDiv");
    createTeamDiv.style.visibility="hidden";
    createTeamOpen = false;
}



function keepDivDescBoxAndDivMapBoxTheSameHeight() {    // Script for resizing Divs display boxes so that they're consistently the same height
  function setHeight(box1, box2) {
    var height = box1.height();
    box2.css('height', height);
  }

  $(window).on('resize', function () {
    if ($(window).width() > 700) {      // Media query
      setHeight($('#eventDetailsBox'), $('#eventLocBox'));
    } else {      // If page width is tablet/mobile, heights no longer need to match, so setting both to auto
      $('#eventLocBox').css("height", "auto");
      $('#eventDetailsBox').css("height", "auto");
    }
  });
  setHeight($('#eventDetailsBox'), $('#eventLocBox'));
}

keepDivDescBoxAndDivMapBoxTheSameHeight();

