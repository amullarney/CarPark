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

import deployment.paymentmachine.PaymentMachineCarPark;        // PaymentMachine-CarPark port
import deployment.carparkcontrol.CarparkControlPayer;          // CarPark-PaymentMachine port
import deployment.PaymentMachine;                              // Shell component
import deployment.CancelledTransactionMsg;
import deployment.TicketCollectedMsg;
import deployment.ExitDeadlineMsg;
import deployment.InsertedCurrencyMsg;
import deployment.InsertedTicketMsg;
import deployment.InsufficientChangeMsg;
import deployment.RemainingBalanceMsg;
import deployment.ReturnedTicketMsg;
import deployment.WaivedChangeMsg;
import deployment.DispenseChangeMsg;
import deployment.TransactionCancelledMsg;

// The Spring framework arranges for an instance of this class to be
// created, passing an instance of SimpMessagingTemplate as an argument,
// which enables messages to be sent to JavaScript clients.
@Controller
public class PaymentMachineMsgController {
	private static PaymentMachineMsgController singleton;
	
	private SimpMessagingTemplate template;  
	
	@Autowired
	public PaymentMachineMsgController( SimpMessagingTemplate template ) {
		singleton = this;
		this.template = template;
	}
	
	public static PaymentMachineMsgController Singleton() {
		return singleton;
	}
	
	// Begin outgoing (from this component) messages.
	// Each of the following methods is invoked when the (JavaScript) client sends 
	// a message to the corresponding message-broker topic, "/app/<messageName>".
	// For example, when the JavaScript client sends a message to "/app/InsertedTicket",
	// the method annotated with @MessageMapping( "/InsertedTicket" ) is invoked, and
	// an instance of the message is passed to it as a parameter.
	
	// Entry/exit stands have Register message, so this one uses a prefix to distinguish it.
    @MessageMapping( "/PMRegister" )
    public void Register( RegisterPayMsg message ) throws Exception {
    	boolean MakesChange = false;
    	if ( message.getDispenses() == "true" )
    		MakesChange = true;
    	try {
    		PaymentMachine.Singleton().CarPark().Register( message.getLocation(), MakesChange );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in PMRegister()\n", e );    			
      	}
    }
	
    // The exit stand has an InsertedTicket message, so this one uses a prefix to distinguish it.
    @MessageMapping( "/PMInsertedTicket" )
    public void InsertedTicket( InsertedTicketMsg message ) throws Exception {
    	try {
      	  PaymentMachine.Singleton().CarPark().InsertedTicket( message.getLocation(), Integer.parseInt( message.getTicketNumber() ) );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in InsertedTicket()\n", e );    			
      	}
    }
    
    @MessageMapping( "/InsertedCurrency" )
    public void InsertedCurrency( InsertedCurrencyMsg message ) throws Exception {
    	if ( message == null )
    		System.out.print( "InsertedCurrency(): message is null!\n" );
    	try {
      	  PaymentMachine.Singleton().CarPark().InsertedCurrency( message.getLocation(), Double.parseDouble( message.getAmount() ) );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in InsertedCurrency()\n", e );    			
      	}
    }

    // Since the Entry Stand has a message mapping for "TicketCollected", this one needs a 
    // different name, hence the prefix.
    @MessageMapping( "/PMTicketCollected" )
    public void TicketCollected( TicketCollectedMsg message ) throws Exception {
    	try {
      	  PaymentMachine.Singleton().CarPark().TicketCollected( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in TicketCollected()\n", e );    			
      	}
    }

    @MessageMapping( "/CancelledTransaction" )
    public void CancelledTransaction( CancelledTransactionMsg message ) throws Exception {
    	try {
      	  PaymentMachine.Singleton().CarPark().CancelledTransaction( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in CancelledTransaction()\n", e );    			
      	}
    }
    
    @MessageMapping( "/WaivedChange" )
    public void WaivedChange( WaivedChangeMsg message ) throws Exception {
    	try {
      	  PaymentMachine.Singleton().CarPark().WaivedChange( message.getLocation() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in WaivedChange()\n", e );    			
      	}
    }
    // End of outgoing messages.
    
    // Incoming (to this component) messages.
    // The following methods forward incoming messages to the (JavaScript) client which
    // subscribes to a location-specific message-broker topic.  For example, the "North"
    // entry stand subscribes to "/topic/PaymentMachine/North".
    public void SendExitDeadlineMessage ( String Location, String Deadline ) throws Exception {
    	ExitDeadlineMsg msg = new ExitDeadlineMsg( "Exit deadline: " + Deadline );
        String topic = "/topic/PaymentMachine/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendInsufficientChangeMessage ( String Location ) throws Exception {
    	InsufficientChangeMsg msg = new InsufficientChangeMsg( "InsufficientChange" );
        String topic = "/topic/PaymentMachine/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendRemainingBalanceMessage ( String Location, double Amount ) throws Exception {
    	RemainingBalanceMsg msg = new RemainingBalanceMsg( "Remaining balance: " + String.valueOf( Amount ) );
        String topic = "/topic/PaymentMachine/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendReturnedTicketMessage ( String Location ) throws Exception {
    	ReturnedTicketMsg msg = new ReturnedTicketMsg( "ReturnedTicket" );
        String topic = "/topic/PaymentMachine/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    
    public void SendDispenseChangeMessage ( String Location, double Amount ) throws Exception {
    	DispenseChangeMsg msg = new DispenseChangeMsg( "Dispense change: " + String.valueOf( Amount ) );
        String topic = "/topic/PaymentMachine/" + Location;
        this.template.convertAndSend( topic, msg );
    }

    public void SendTransactionCancelledMessage ( String Location, String Why ) throws Exception {
    	TransactionCancelledMsg msg = new TransactionCancelledMsg( "Transaction cancelled by " + Why );
        String topic = "/topic/PaymentMachine/" + Location;
        this.template.convertAndSend( topic, msg );
    }
    // End of incoming messages.
}
