var stompClient = null;

var vue_data = { TicketRequestDisabled: true };

var vm = new Vue({
	el: '#main-content',
	data: vue_data
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

// When connecting, subscribe to a location-specific topic to receive
// messages sent from the server.
function connect() {
    var socket = new SockJS('/Carpark-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/EntryStand/' + $("#location").val(), function (reply) {
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
function sendToServer( messageName ) {
    stompClient.send("/app/" + messageName, {}, JSON.stringify({'location': $("#location").val()}));
}

// Display a message received from the server.
function showReply(message) {
    $("#replies").append("<tr><td>" + message + "</td></tr>");
    if (message == "Ticket request enabled") {
    	vm.TicketRequestDisabled = false;
    }
    if (message == "Open barrier") {
    	vm.TicketRequestDisabled = true;
    }
}

// Map buttons to functions.
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#VehicleWaiting" ).click(function() { sendToServer( "VehicleWaiting" ); });
    $( "#TicketRequest" ).click(function() { sendToServer( "TicketRequested" ); });
    $( "#TicketCollected" ).click(function() { sendToServer( "TicketCollected" ); });
    $( "#VehicleEntered" ).click(function() { sendToServer( "VehicleEntered" ); });
});