var stompClient = null;

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

function sendVehicleWaiting() {
    stompClient.send("/app/VehicleWaiting", {}, JSON.stringify({'location': $("#location").val()}));
}

function sendTicketRequested() {
    stompClient.send("/app/TicketRequested", {}, JSON.stringify({'location': $("#location").val()}));
}

function showReply(message) {
    $("#replies").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#VehicleWaiting" ).click(function() { sendVehicleWaiting(); });
    $( "#TicketRequested" ).click(function() { sendTicketRequested(); });
});