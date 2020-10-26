var stompClient = null;

var vm = new Vue({
	el: '#main-content',
	data: {
	    VehicleExitedDisabled : true,
	    InsertedTicketDisabled: true,
	    BarrierOpen: false,
    }
})

function initialize() {
	vm.VehicleExitedDisabled = true;
	vm.InsertedTicketDisabled = true;
	vm.BarrierOpen = false;
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
        initialize();
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
        stompClient.subscribe('/topic/ExitStand/' + $("#location").val(), function (reply) {
            showReply(JSON.parse(reply.body).content);
        });
     stompClient.send("/app/Register", {}, JSON.stringify({'location': $("#location").val()}));
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
    if ( messageName == "EXVehicleWaiting" )
    	vm.InsertedTicketDisabled = false;
}

function sendInsertedTicket() {
    stompClient.send("/app/InsertedTicket", {}, 
      JSON.stringify({'location': $("#location").val(), 'ticketNumber': $("#TicketNumber").val()}));
    vm.InsertedTicketDisabled = true;
}

// Display a message received from the server and 
// update the enable/disable states of buttons based on the 
// the incoming message.
function showReply(message) {
    $("#replies").append("<tr><td>" + message + "</td></tr>");
    if ( message == "Open barrier" ) {
    	vm.VehicleExitedDisabled = false;
    	vm.BarrierOpen = true;
    } else if ( message == "Close barrier" ) {
    	vm.VehicleExitedDisabled = true;
    	vm.BarrierOpen = false;
    }
}

// Map buttons to functions.
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#VehicleWaiting" ).click(function() { sendToServer( "EXVehicleWaiting" ); });
    $( "#InsertedTicket" ).click(function() { sendInsertedTicket(); });
    $( "#VehicleExited" ).click(function() { sendToServer( "VehicleExited" ); });
});