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

import sharedtypes.BarrierState;
import sharedtypes.EntryTicketState;
import sharedtypes.ExitDeadline;
import sharedtypes.ExitTicketState;

import deployment.operatorconsole.OperatorConsoleCarPark;    // OperatorConsole-CarPark port
import deployment.carparkcontrol.CarparkControlOperator;     // CarPark-OperatorConsole port
import deployment.OperatorConsole;                           // Shell component
import deployment.ActivateEntryStandMsg; 
import deployment.ActivateExitStandMsg;
import deployment.DateTimeUpdateMsg;
import deployment.DeactivateEntryStandMsg;
import deployment.DeactivateExitStandMsg;
import deployment.DelayedEntryMsg;
import deployment.FeeCollectedMsg;
import deployment.FeeWaivedMsg;
import deployment.OperatorIssueTicketMsg;
import deployment.OccupancyUpdateMsg;
import deployment.OpenEntryBarrierMsg;
import deployment.OpenExitBarrierMsg;
import deployment.TardyExitMsg;
import deployment.UnpaidStayExitMsg;


// The Spring framework arranges for an instance of this class to be
// created, passing an instance of SimpMessagingTemplate as an argument,
// which enables messages to be sent to JavaScript clients.
@Controller
public class OperatorConsoleMsgController {
	private static OperatorConsoleMsgController singleton;
	
	private SimpMessagingTemplate template;  
	
	@Autowired
	public OperatorConsoleMsgController( SimpMessagingTemplate template ) {
		singleton = this;
		this.template = template;
	}
	
	public static OperatorConsoleMsgController Singleton() {
		return singleton;
	}
	
	// Begin outgoing (from this component) messages.
	// Each of the following methods is invoked when the (JavaScript) client sends 
	// a message to the corresponding message-broker topic, "/app/<messageName>".
	// For example, when the JavaScript client sends a message to "/app/VehicleWaiting",
	// the method annoated with @MessageMapping( "/VehicleWaiting" ) is invoked, and
	// an instance of the message is passed to it as a parameter.
    @MessageMapping( "/FeeWaived" )
    public void FeeWaived( FeeWaivedMsg message ) throws Exception {
    	try {
      	  OperatorConsole.Singleton().CarPark().FeeWaived( Integer.parseInt( message.getTicketNumber() ) );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in FeeWaived()\n", e );    			
      	}
    }
    
    @MessageMapping( "/FeeCollected" )
    public void FeeCollected( FeeCollectedMsg message ) throws Exception {
    	try {
      	  OperatorConsole.Singleton().CarPark().FeeCollected( Integer.parseInt( message.getTicketNumber() ) );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in FeeCollected()\n", e );    			
      	}
    }
    
    @MessageMapping( "/OperatorIssueTicket" )
    public void OperatorIssueTicket( OperatorIssueTicketMsg message ) throws Exception {
    	try {
      	  OperatorConsole.Singleton().CarPark().IssueTicket( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in OperatorIssueTicket()\n", e );    			
      	}
    }
    
    @MessageMapping( "/OpenEntryBarrier" )
    public void OpenEntryBarrier( OpenEntryBarrierMsg message ) throws Exception {
    	try {
      	  OperatorConsole.Singleton().CarPark().OpenEntryBarrier( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in OpenEntryBarrier()\n", e );    			
      	}
    }
    
    @MessageMapping( "/OpenExitBarrier" )
    public void OpenExitBarrier( OpenExitBarrierMsg message ) throws Exception {
    	try {
      	  OperatorConsole.Singleton().CarPark().OpenExitBarrier( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in OpenExitBarrier()\n", e );    			
      	}
    }
    // End of outgoing messages.
    
    // Incoming (to this component) messages.
    // The following methods forward incoming messages to the (JavaScript) client which
    // subscribes to the message-broker topic, /topic/OperatorConsole.  There can be any
    // number of instances of operator consoles, but they all receive the same data.
    public void SendTardyExitMessage ( String Location, int TicketNumber, double AdditionalCharge, int Overstay ) throws Exception {
    	TardyExitMsg msg = new TardyExitMsg( "TardyExit", Location, 
          String.valueOf( TicketNumber ), String.valueOf( AdditionalCharge ), String.valueOf( Overstay ) );
        String topic = "/topic/OperatorConsole";
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendUnpaidStayExitMessage ( String Location, int TicketNumber, double Charge, double Duration ) throws Exception {
    	UnpaidStayExitMsg msg = new UnpaidStayExitMsg( "UnpaidStayExit", Location, 
          String.valueOf( TicketNumber ), String.valueOf( Charge ), String.valueOf( Duration ) );
        String topic = "/topic/OperatorConsole";
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendActivateEntryStandMessage ( String Location, BarrierState Barrier, EntryTicketState Ticket ) throws Exception {
    	ActivateEntryStandMsg msg = new ActivateEntryStandMsg( "ActivateEntryStand", Location, 
          Barrier.name(), Ticket.name() );
        String topic = "/topic/OperatorConsole";
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendActivateExitStandMessage ( String Location, BarrierState Barrier, ExitTicketState Ticket, ExitDeadline ExitDeadline ) throws Exception {
    	ActivateExitStandMsg msg = new ActivateExitStandMsg( "ActivateExitStand", Location, 
          Barrier.name(), Ticket.name(), ExitDeadline.name() );
        String topic = "/topic/OperatorConsole";
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendDateTimeUpdateMessage ( String DateTime ) throws Exception {
    	DateTimeUpdateMsg msg = new DateTimeUpdateMsg( "DateTimeUpdate", DateTime ); 
        String topic = "/topic/OperatorConsole";
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendDeactivateEntryStandMessage ( String Location ) throws Exception {
    	DeactivateEntryStandMsg msg = new DeactivateEntryStandMsg( "DeactivateEntryStand", Location ); 
        String topic = "/topic/OperatorConsole";
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendDeactivateExitStandMessage ( String Location ) throws Exception {
    	DeactivateExitStandMsg msg = new DeactivateExitStandMsg( "DeactivateExitStand", Location ); 
        String topic = "/topic/OperatorConsole";
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendDelayedEntryMessage ( String Location ) throws Exception {
    	DelayedEntryMsg msg = new DelayedEntryMsg( "DelayedEntry", Location ); 
        String topic = "/topic/OperatorConsole";
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendOccupancyUpdateMessage ( int Occupancy, int Capacity, int Availability ) throws Exception {
    	OccupancyUpdateMsg msg = new OccupancyUpdateMsg( "OccupancyUpdate", String.valueOf( Occupancy ),
    	  String.valueOf( Capacity ), String.valueOf( Availability ) ); 
        String topic = "/topic/OperatorConsole";
        this.template.convertAndSend( topic, msg );
    }
    

    // End of incoming messages.
}
