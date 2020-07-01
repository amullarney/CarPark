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
                                                       // TicketRequestEnabled message class
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
    	System.out.printf( "EntryStandMsgController: \n" );
    	System.out.printf( "Location: %s\n", message.getLocation() );
    	try {
      	  EntryStand.Singleton().CarPark().VehicleWaiting( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in VehicleWaiting()\n", e );    			
      	}
    }
    /*
    public void SendReplyMessage ( String msg ) throws Exception {
    	ReplyMessage rmsg = new ReplyMessage( HtmlUtils.htmlEscape( msg ) );
        System.out.printf( "Sending reply: %s\n", rmsg.getContent() );
        String topic = "/topic/replies";
        if ( msg.contains( "north") )
        	topic = "/topic/replies/north";
        else if  ( msg.contains( "south" ) )
            topic = "/topic/replies/south";
        this.template.convertAndSend( topic, rmsg );
    }
    */
}
