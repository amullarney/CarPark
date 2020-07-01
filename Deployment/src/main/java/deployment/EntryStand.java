package deployment;


import deployment.entrystand.EntryStandCarPark;

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


public class EntryStand extends Component<EntryStand> {

    private Map<String, Class<?>> classDirectory;
    private static EntryStand Singleton;
    

    public EntryStand(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);

        classDirectory = new TreeMap<>();
        Singleton = this;
    }
    
    public static EntryStand Singleton() {
    	return Singleton;
    }

    // domain functions
    public void CloseBarrier( final String p_Location ) throws XtumlException {
    }

    public void IssueTicket( final String p_Location,  final int p_Number ) throws XtumlException {
    }

    public void OpenBarrier( final String p_Location ) throws XtumlException {
    }

    public void TicketRequestDisabled( final String p_Location ) throws XtumlException {
    }

    public void TicketRequestEnabled( final String p_Location ) throws XtumlException {
    	try {
            EntryStandMsgController.Singleton().SendTicketRequestEnabledMessage( p_Location );
      	} catch ( Exception e ) {}
    }



    // relates and unrelates


    // instance selections


    // relationship selections


    // ports
    private EntryStandCarPark EntryStandCarPark;
    public EntryStandCarPark CarPark() {
        if ( null == EntryStandCarPark ) EntryStandCarPark = new EntryStandCarPark( this, null );
        return EntryStandCarPark;
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
            prop.load(getClass().getResourceAsStream("EntryStandProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("EntryStandProperties.properties"));
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
    public EntryStand context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}
