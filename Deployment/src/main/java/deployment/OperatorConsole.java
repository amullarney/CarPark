package deployment;


import deployment.operatorconsole.OperatorConsoleCarPark;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.Component;
import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.XtumlException;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import sharedtypes.BarrierState;
import sharedtypes.EntryTicketState;
import sharedtypes.ExitDeadline;
import sharedtypes.ExitTicketState;


public class OperatorConsole extends Component<OperatorConsole> {

    private Map<String, Class<?>> classDirectory;
    private static OperatorConsole Singleton;

    public OperatorConsole(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);
        classDirectory = new TreeMap<>();
        Singleton = this;
    }
    
    public static OperatorConsole Singleton() {
    	return Singleton;
    }

    // domain functions
    public void ActivateEntryStand( final String p_Location,  final BarrierState p_Barrier,  final EntryTicketState p_Ticket ) throws XtumlException {
    	try {
            OperatorConsoleMsgController.Singleton().SendActivateEntryStandMessage( p_Location, p_Barrier, p_Ticket );
      	} catch ( Exception e ) {}
    }

    public void ActivateExitStand( final String p_Location, final BarrierState p_Barrier, final ExitTicketState p_Ticket, final ExitDeadline p_ExitDeadline ) throws XtumlException {
    	try {
            OperatorConsoleMsgController.Singleton().SendActivateExitStandMessage( p_Location, p_Barrier, p_Ticket, p_ExitDeadline );
      	} catch ( Exception e ) {}    
    }

    public void CarparkClosed() throws XtumlException {
    	// @TODO Presently the Operator Console client does not use the CarparkClosed message, but the 
    	// testbench does.
    }

    public void CarparkOpened() throws XtumlException {
    	// @TODO Presently the Operator Console client does not use the CarparkOpened message, but the 
    	// testbench does.
    }

    public void DateTimeUpdate( final String p_DateTime ) throws XtumlException {
    	try {
            OperatorConsoleMsgController.Singleton().SendDateTimeUpdateMessage( p_DateTime );
      	} catch ( Exception e ) {}   
    }

    public void DeactivateEntryStand( final String p_Location ) throws XtumlException {
    	try {
            OperatorConsoleMsgController.Singleton().SendDeactivateEntryStandMessage( p_Location );
      	} catch ( Exception e ) {}
    }

    public void DeactivateExitStand( final String p_Location ) throws XtumlException {
    	try {
            OperatorConsoleMsgController.Singleton().SendDeactivateExitStandMessage( p_Location );
      	} catch ( Exception e ) {}
    }

    public void DelayedEntry( final String p_Location ) throws XtumlException {
    	try {
            OperatorConsoleMsgController.Singleton().SendDelayedEntryMessage( p_Location );
      	} catch ( Exception e ) {}
    }

    public void OccupancyUpdate( final int p_Occupancy,  final int p_Capacity,  final int p_Availability ) throws XtumlException {
    	try {
            OperatorConsoleMsgController.Singleton().SendOccupancyUpdateMessage( p_Occupancy, p_Capacity, p_Availability );
      	} catch ( Exception e ) {}
    }

    public void Register( final String p_Location,  final String p_Peripheral ) throws XtumlException {
    	try {
            OperatorConsoleMsgController.Singleton().SendRegisterMessage( p_Location, p_Peripheral );
      	} catch ( Exception e ) {}
    }

    public void TardyExit( final String p_Location,  final int p_TicketNumber,  final double p_AdditionalCharge,  final int p_Overstay ) throws XtumlException {
    	try {
            OperatorConsoleMsgController.Singleton().SendTardyExitMessage( p_Location, p_TicketNumber, p_AdditionalCharge, p_Overstay );
      	} catch ( Exception e ) {}
    }

    public void UnpaidStayExit( final String p_Location,  final int p_TicketNumber,  final double p_Charge,  final double p_Duration ) throws XtumlException {
    	try {
            OperatorConsoleMsgController.Singleton().SendUnpaidStayExitMessage( p_Location, p_TicketNumber, p_Charge, p_Duration );
      	} catch ( Exception e ) {}
    }


    // relates and unrelates


    // instance selections


    // relationship selections


    // ports
    private OperatorConsoleCarPark OperatorConsoleCarPark;
    public OperatorConsoleCarPark CarPark() {
        if ( null == OperatorConsoleCarPark ) OperatorConsoleCarPark = new OperatorConsoleCarPark( this, null );
        return OperatorConsoleCarPark;
    }


    // utilities


    // component initialization function
    @Override
    public void initialize() throws XtumlException {

    }

    @Override
    public String getVersion() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("OperatorConsoleProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("OperatorConsoleProperties.properties"));
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
    public OperatorConsole context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}
