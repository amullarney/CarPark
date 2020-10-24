var stompClient = null;

var entry = null;
var exit = null;

var vm = new Vue({
	el: '#main-content',
	data: {
	    DateTime: "",
	    Capacity: "",
	    Occupancy: "",
	    Availability: "",
	    NorthEntry: false,
	    NorthTicket: "",
	    NorthBarrier: "",
	    NorthDelayedEntry: false,
	    Lane1Exit: false,
	    Lane1Ticket: "",
	    Lane1ExitDeadline: "",
	    Lane1Barrier: "",
	    Lane1TicketNumber: "",
	    Lane1AdditionalCharge: "",
	    Lane1Overstay: "",
	    Lane1Charge: "",
	    Lane1Duration: "",
	    Lane1TardyExit: false,
	    Lane1UnpaidStayExit: false
    }
})

const EntryStand = {
  template: '#entry-console-display',
  data: function () {
    return {
      location: 'somewhere',
      ticket: 'not requested',
      barrier: 'closed'
    }
  },
  methods: {
    setLoc: function() {
      this.location = 'South';
    },
    setTicket(ticket) {
      this.ticket = ticket;
    },
    setBarrier(barrier) {
      this.barrier = barrier;
    }
  }
}

const ExitStand = {
  template: '#exit-console-display',
  data: function () {
    return {
      location: 'wayout',
      ticket: 'none',
      barrier: 'stuck'
    }
  },
  methods: {
    setLoc: function() {
      this.location = 'East';
    },
    setTicket(ticket) {
      this.ticket = ticket;
    },
    setBarrier(barrier) {
      this.barrier = barrier;
    }
  }
}

function initialize() {
	vm.DateTime = "";
	vm.Capacity = "";
	vm.Occupancy = "";
	vm.Availability = "";
	vm.NorthEntry = false;
	vm.Lane1Exit = false;
	vm.NorthDelayedEntry = false;
    vm.Lane1TardyExit = false;
    vm.Lane1UnpaidStayExit = false;
}

function makeEntry() {
    const entryClass = Vue.extend(EntryStand);
    var theEntry = new entryClass;
    entry = theEntry.$mount();
    document.getElementById('entry-consoles').appendChild(theEntry.$el);
    entry.setLoc();
}

function makeExit() {
    const exitClass = Vue.extend(ExitStand);
    var theExit= new exitClass;
    exit = theExit.$mount();
    document.getElementById('exit-consoles').appendChild(theExit.$el);
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
        stompClient.subscribe('/topic/OperatorConsole', function (reply) {
            handleReply(reply);
        });
    makeEntry();
    makeExit();
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
function sendToServer( messageName, paramName, paramValue ) {
	var messageBody = {};
	messageBody[paramName] = paramValue;
    stompClient.send("/app/" + messageName, {}, JSON.stringify( messageBody ));
}

// Display a message received from the server and
// update data as necessary.
function handleReply(reply) {
    var messageName = JSON.parse( reply.body ).messageName;
    if ( messageName !== "DateTimeUpdate" ) {
        $("#replies").append("<tr><td>" + JSON.stringify( JSON.parse( reply.body ) ) + "</td></tr>");
    }
    if ( messageName == "ActivateEntryStand" ) {
    	vm.NorthBarrier = JSON.parse( reply.body ).barrier;
    	vm.NorthTicket = JSON.parse( reply.body ).ticket;
    	vm.NorthEntry = true;
    	entry.setTicket(vm.NorthTicket);
    	entry.setBarrier(vm.NorthBarrier);
    } else if ( messageName == "ActivateExitStand" ) {
    	vm.Lane1Ticket = JSON.parse( reply.body ).ticket;
    	vm.Lane1Barrier = JSON.parse( reply.body ).barrier;
    	vm.Lane1ExitDeadline = JSON.parse( reply.body ).exitDeadline;
        vm.Lane1Exit = true;
    } else if ( messageName == "DeactivateEntryStand" ) {
    	vm.NorthEntry = false;
    	vm.NorthDelayedEntry = false;
    } else if ( messageName == "DeactivateExitStand" ) {
    	vm.Lane1Exit = false;
    	vm.Lane1TardyExit = false;
    	vm.Lane1UnpaidStayExit = false;
    } else if ( messageName == "DelayedEntry" ) {
    	vm.NorthDelayedEntry = true;
    } else if ( messageName == "OccupancyUpdate" ) {
    	vm.Occupancy = JSON.parse( reply.body ).occupancy;
    	vm.Capacity = JSON.parse( reply.body ).capacity;
    	vm.Availability = JSON.parse( reply.body ).availability;
    } else if ( messageName == "TardyExit" ) {
    	vm.Lane1TicketNumber = JSON.parse( reply.body ).ticketNumber;
    	vm.Lane1AdditionalCharge = Number( JSON.parse( reply.body ).additionalCharge ).toFixed(2);
    	vm.Lane1Overstay = Number( JSON.parse( reply.body ).overstay ).toFixed(2);
    	vm.Lane1TardyExit = true;
    } else if ( messageName == "UnpaidStayExit" ) {
    	vm.Lane1TicketNumber = JSON.parse( reply.body ).ticketNumber;
    	vm.Lane1Charge = Number( JSON.parse( reply.body ).charge ).toFixed(2);
    	vm.Lane1Duration = Number( JSON.parse( reply.body ).duration ).toFixed(2);
    	vm.Lane1UnpaidStayExit = true;
    } else if ( messageName == "DateTimeUpdate" ) {
    	vm.DateTime = JSON.parse( reply.body ).dateTime;
    }
}

// Map buttons to functions.
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#NorthIssueTicket" ).click(function() { sendToServer( "OperatorIssueTicket", "location", "North" ); });
    $( "#NorthOpenBarrier" ).click(function() { sendToServer( "OpenEntryBarrier", "location", "North" ); });
    $( "#Lane1OpenBarrier" ).click(function() { sendToServer( "OpenExitBarrier", "location", "Lane 1" ); });
    $( "#Lane1TardyCancel" ).click(function() { sendToServer( "FeeWaived", "ticketNumber", vm.Lane1TicketNumber ); });
    $( "#Lane1TardyPaid" ).click(function() { sendToServer( "FeeCollected", "ticketNumber", vm.Lane1TicketNumber ); });
    $( "#Lane1UnpaidCancel" ).click(function() { sendToServer( "FeeWaived", "ticketNumber", vm.Lane1TicketNumber ); });
    $( "#Lane1UnpaidPaid" ).click(function() { sendToServer( "FeeCollected", "ticketNumber", vm.Lane1TicketNumber ); });
});