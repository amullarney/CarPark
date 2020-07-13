package deployment;

import io.ciera.runtime.summit.application.IApplication;
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

import deployment.exitstand.ExitStandCarPark;        // ExitStand-CarPark port
import deployment.carparkcontrol.CarparkControlExit;  // CarPark-ExitStand port
import deployment.ExitStand;                          // Shell component
import deployment.VehicleWaitingMsg;                   
import deployment.VehicleExitedMsg;
import deployment.OpenBarrierMsg;
import deployment.CloseBarrierMsg;
import deployment.InsertedTicketMsg;


// The Spring framework arranges for an instance of this class to be
// created, passing an instance of SimpMessagingTemplate as an argument,
// which enables messages to be sent to JavaScript clients.
@Controller
public class ExitStandMsgController {
	private static ExitStandMsgController singleton;
	
	private SimpMessagingTemplate template;  
	
	@Autowired
	public ExitStandMsgController( SimpMessagingTemplate template ) {
		singleton = this;
		this.template = template;
	}
	
	public static ExitStandMsgController Singleton() {
		return singleton;
	}
	
	// Begin outgoing (from this component) messages.
	// Each of the following methods is invoked when the (JavaScript) client sends 
	// a message to the corresponding message-broker topic, "/app/<messageName>".
	// For example, when the JavaScript client sends a message to "/app/EXVehicleWaiting",
	// the method annoated with @MessageMapping( "/EXVehicleWaiting" ) is invoked, and
	// an instance of the message is passed to it as a parameter.
	// The Entry Stand also handles the VehicleWaiting message, so the Exit Stand uses
	// a prefix on the mapping to enable the framework to disambiguate the two recipients.
    @MessageMapping( "/EXVehicleWaiting" )
    public void VehicleWaiting( VehicleWaitingMsg message ) throws Exception {
    	try {
      	  ExitStand.Singleton().CarPark().VehicleWaiting( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in VehicleWaiting()\n", e );    			
      	}
    }
    
    @MessageMapping( "/InsertedTicket" )
    public void InsertedTicket( InsertedTicketMsg message ) throws Exception {
    	try {
      	  ExitStand.Singleton().CarPark().InsertedTicket( message.getLocation(), Integer.parseInt( message.getTicketNumber() ) );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in InsertedTicket()\n", e );    			
      	}
    }

    @MessageMapping( "/VehicleExited" )
    public void VehicleEntered( VehicleExitedMsg message ) throws Exception {
    	try {
      	  ExitStand.Singleton().CarPark().VehicleExited( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in VehicleExited()\n", e );    			
      	}
    }
    // End of outgoing messages.
    
    // Incoming (to this component) messages.
    // The following methods forward incoming messages to the (JavaScript) client which
    // subscribes to a location-specific message-broker topic.  For example, the "North"
    // entry stand subscribes to "/topic/ExitStand/North".
    public void SendOpenBarrierMessage ( String Location ) throws Exception {
    	OpenBarrierMsg msg = new OpenBarrierMsg( "Open barrier" );
        String topic = "/topic/ExitStand/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendCloseBarrierMessage ( String Location ) throws Exception {
    	CloseBarrierMsg msg = new CloseBarrierMsg( "Close barrier" );
        String topic = "/topic/ExitStand/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    // End of incoming messages.
}
