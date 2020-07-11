var stompClient = null;

var vm = new Vue({
	el: '#main-content',
})

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#replies").html("");
}

// When connecting, subscribe to a topic to receive
// messages sent from the server.
function connect() {
    var socket = new SockJS('/Carpark-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/TestControl', function (reply) {
            showReply(JSON.parse(reply.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

// Client-to-server messages.
function sendAdvanceTime() {
	var minutes = $("#minutes").val();
	var hours = $("#hours").val();
	
	// Assume a zero value for any fields left blank by the user
	if ( minutes.length == 0 )
		minutes = "0";
	if ( hours.length == 0 )
		hours = "0";
    stompClient.send("/app/AdvanceTime", {}, 
      JSON.stringify({'hours': hours, 'minutes': minutes}));
}

function sendSetTime() {
	// Use the current date and time for any field left blank by the user
	var currentDate = new Date();
	var year = $("#year").val();
	var month = $("#month").val();
	var day = $("#day").val();
	var hour = $("#hour").val();
	var minute = $("#minute").val();
	if ( year.length == 0 )
		year = currentDate.getFullYear();
	if ( month.length == 0 )
		month = currentDate.getMonth() + 1;  // JavaScript is 0-based, TIM EE is 1-based
	if ( day.length == 0 )
		day = currentDate.getDate();
	if ( hour.length == 0 )
		hour = currentDate.getHours();
	if ( minute.length == 0 )
		minute = currentDate.getMinutes();
    stompClient.send("/app/SetTime", {}, 
      JSON.stringify({'year': year,
    	              'month': month,
    	              'day': day,
    	              'hour': hour,
    	              'minute': minute}));
}

// Display a message received from the server.
function showReply(message) {
    $("#replies").append("<tr><td>" + message + "</td></tr>");
}

// Map buttons to functions.
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#AdvanceTime" ).click(function() { sendAdvanceTime(); });
    $( "#SetTime" ).click(function() { sendSetTime(); });
});