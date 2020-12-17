package deployment;

import io.ciera.runtime.summit.application.IApplication;
import deployment.OperatorConsoleMsgController;

import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.application.tasks.GenericExecutionTask;
import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.Component;
import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IMessage;
import io.ciera.runtime.summit.interfaces.Message;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.impl.LOGImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Properties;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import deployment.entrystand.EntryStandCarPark;        // EntryStand-CarPark port
import deployment.carparkcontrol.CarparkControlEntry;  // CarPark-EntryStand port
import deployment.EntryStand;                          // Shell component
import deployment.VehicleWaitingMsg;                   
import deployment.TicketRequestedMsg;                  
import deployment.TicketCollectedMsg;
import deployment.VehicleEnteredMsg;
import deployment.TicketRequestEnabledMsg; 
import deployment.IssueTicketMsg;
import deployment.OpenBarrierMsg;
import deployment.CloseBarrierMsg;
import deployment.TicketRequestDisabledMsg;


// The Spring framework arranges for an instance of this class to be
// created, passing an instance of SimpMessagingTemplate as an argument,
// which enables messages to be sent to JavaScript clients.
@Controller
public class EntryStandMsgController {
	private static EntryStandMsgController singleton;
	
	private SimpMessagingTemplate template;  
	
	@Autowired
	public EntryStandMsgController( SimpMessagingTemplate template ) {
		singleton = this;
		this.template = template;
	}
	
	public static EntryStandMsgController Singleton() {
		return singleton;
	}
	
	// Begin outgoing (from this component) messages.
	// Each of the following methods is invoked when the (JavaScript) client sends 
	// a message to the corresponding message-broker topic, "/app/<messageName>".
	// For example, when the JavaScript client sends a message to "/app/VehicleWaiting",
	// the method annoated with @MessageMapping( "/VehicleWaiting" ) is invoked, and
	// an instance of the message is passed to it as a parameter.
    @MessageMapping( "/Register" )
    public void Register( RegisterMsg message ) throws Exception {
    	try {
      	  EntryStand.Singleton().CarPark().Register( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in Register()\n", e );    			
      	}
    }

    @MessageMapping( "/VehicleWaiting" )
    public void VehicleWaiting( VehicleWaitingMsg message ) throws Exception {
    	try {
      	  EntryStand.Singleton().CarPark().VehicleWaiting( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in VehicleWaiting()\n", e );    			
      	}
    }
    
    @MessageMapping( "/TicketRequested" )
    public void TicketRequested( TicketRequestedMsg message ) throws Exception {
    	try {
      	  EntryStand.Singleton().CarPark().TicketRequested( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in TicketRequested()\n", e );    			
      	}
    }

    @MessageMapping( "/TicketCollected" )
    public void TicketCollected( TicketCollectedMsg message ) throws Exception {
    	try {
      	  EntryStand.Singleton().CarPark().TicketCollected( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in TicketCollected()\n", e );    			
      	}
    }

    @MessageMapping( "/VehicleEntered" )
    public void VehicleEntered( VehicleEnteredMsg message ) throws Exception {
    	try {
      	  EntryStand.Singleton().CarPark().VehicleEntered( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in VehicleEntered()\n", e );    			
      	}
    }

    // *** Note: this mapping handles 'HelpRequest' from either an Entry or an Exit stand. ***
    // *** The same message, with peripheral, is sent to Operator Console in either case.  ***
    @MessageMapping( "/HelpRequest" )
    public void HelpRequest( HelpRequestMsg message ) throws Exception {
    	try {
    		OperatorConsoleMsgController.Singleton().SendHelpRequestMessage( message.getLocation(), message.getPeripheral() );
    	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in HelpRequest()\n", e );    			
      	}
    }
    // End of outgoing messages.
    
    // Incoming (to this component) messages.
    // The following methods forward incoming messages to the (JavaScript) client which
    // subscribes to a location-specific message-broker topic.  For example, the "North"
    // entry stand subscribes to "/topic/EntryStand/North".
    public void SendTicketRequestEnabledMessage ( String Location ) throws Exception {
    	TicketRequestEnabledMsg msg = new TicketRequestEnabledMsg( "Ticket request enabled");
        String topic = "/topic/EntryStand/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendIssueTicketMessage ( String Location, int Number ) throws Exception {
    	IssueTicketMsg msg = new IssueTicketMsg( "Issue ticket number: " + String.valueOf( Number ));
        String topic = "/topic/EntryStand/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendOpenBarrierMessage ( String Location ) throws Exception {
    	OpenBarrierMsg msg = new OpenBarrierMsg( "Open barrier" );
        String topic = "/topic/EntryStand/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendCloseBarrierMessage ( String Location ) throws Exception {
    	CloseBarrierMsg msg = new CloseBarrierMsg( "Close barrier" );
        String topic = "/topic/EntryStand/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    
   public void SendTicketRequestDisabledMessage ( String Location ) throws Exception {
    	TicketRequestDisabledMsg msg = new TicketRequestDisabledMsg( "Ticket request disabled");
        String topic = "/topic/EntryStand/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    // End of incoming messages.
}
