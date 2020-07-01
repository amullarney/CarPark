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

import deployment.entrystand.EntryStandCarPark;        // EntryStand-CarPark port
import deployment.carparkcontrol.CarparkControlEntry;  // EntryStand interface
import deployment.VehicleWaitingMsg;                   // VehicleWaiting message class
import deployment.TicketRequestEnabledMsg;             // TicketRequestEnabled message class
import deployment.EntryStand;                          // Shell component

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
    
    public void SendTicketRequestEnabledMessage ( String Location ) throws Exception {
    	TicketRequestEnabledMsg tremsg = new TicketRequestEnabledMsg( "Ticket request enabled");
        String topic = "/topic/EntryStand/" + Location;
        this.template.convertAndSend( topic, tremsg );
    }
    
    public void SendIssueTicketMessage ( String Location, int Number ) throws Exception {
    	IssueTicketMsg itmsg = new IssueTicketMsg( "Issue ticket number: " + String.valueOf( Number ));
        String topic = "/topic/EntryStand/" + Location;
        this.template.convertAndSend( topic, itmsg );
    }
}
