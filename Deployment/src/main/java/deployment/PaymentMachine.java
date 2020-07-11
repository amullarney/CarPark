package deployment;


import deployment.paymentmachine.PaymentMachineCarPark;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.Component;
import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.TIM;
import io.ciera.runtime.summit.util.impl.TIMImpl;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;


public class PaymentMachine extends Component<PaymentMachine> {

    private Map<String, Class<?>> classDirectory;
    private static PaymentMachine Singleton;

    public PaymentMachine(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);

        Singleton = this;
        classDirectory = new TreeMap<>();
    }
    
    public static PaymentMachine Singleton() {
    	return Singleton;
    }

    // domain functions
    public void DispenseChange( final String p_Location,  final double p_Amount ) throws XtumlException {
    	try {
            PaymentMachineMsgController.Singleton().SendDispenseChangeMessage( p_Location, p_Amount );
      	} catch ( Exception e ) {}
    }
    
    public void ExitDeadline( final String p_Location, final int p_Deadline ) throws XtumlException {
    	long deadline = (long) p_Deadline * 1000000L;
    	String exitDeadline = context().TIM().timestamp_format( deadline, "yyyy/MM/dd HH:mm" );
    	try {
            PaymentMachineMsgController.Singleton().SendExitDeadlineMessage( p_Location, exitDeadline );
      	} catch ( Exception e ) {}
    }
    
    public void InsufficientChange( final String p_Location ) throws XtumlException {
    	try {
            PaymentMachineMsgController.Singleton().SendInsufficientChangeMessage( p_Location );
      	} catch ( Exception e ) {}
    }

    public void RemainingBalance( final String p_Location,  final double p_Amount ) throws XtumlException {
    	try {
            PaymentMachineMsgController.Singleton().SendRemainingBalanceMessage( p_Location, p_Amount );
      	} catch ( Exception e ) {}
    }

    public void ReturnedTicket( final String p_Location ) throws XtumlException {
    	try {
            PaymentMachineMsgController.Singleton().SendReturnedTicketMessage( p_Location );
      	} catch ( Exception e ) {}
    }



    // relates and unrelates


    // instance selections


    // relationship selections


    // ports
    private PaymentMachineCarPark PaymentMachineCarPark;
    public PaymentMachineCarPark CarPark() {
        if ( null == PaymentMachineCarPark ) PaymentMachineCarPark = new PaymentMachineCarPark( this, null );
        return PaymentMachineCarPark;
    }


    // utilities
    // utilities
    private TIM TIM;
    public TIM TIM() {
        if ( null == TIM ) TIM = new TIMImpl<>( this );
        return TIM;
    }

    // component initialization function
    @Override
    public void initialize() throws XtumlException {

    }

    @Override
    public String getVersion() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("PaymentMachineProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("PaymentMachineProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version_date", "Unknown");
    }

    @Override
    public boolean addInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot add empty instance to population." );

        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );

        return false;
    }

    @Override
    public PaymentMachine context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}
