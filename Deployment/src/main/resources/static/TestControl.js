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
    stompClient.send("/app/AdvanceTime", {}, 
      JSON.stringify({'hours': $("#hours").val(), 'minutes': $("#minutes").val()}));
}

function sendSetTime() {
    stompClient.send("/app/SetTime", {}, 
      JSON.stringify({'year': $("#year").val(),
    	              'month': $("#month").val(),
    	              'day': $("#day").val(),
    	              'hour': $("#hour").val(),
    	              'minute': $("#minute").val()}));
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