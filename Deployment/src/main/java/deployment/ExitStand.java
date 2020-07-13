package deployment;


import deployment.exitstand.ExitStandCarPark;

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


public class ExitStand extends Component<ExitStand> {

    private Map<String, Class<?>> classDirectory;
    private static ExitStand Singleton;

    public ExitStand(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);
        classDirectory = new TreeMap<>();
        Singleton = this;
    }
    
    public static ExitStand Singleton() {
    	return Singleton;
    }

    // domain functions
    public void CloseBarrier( final String p_Location ) throws XtumlException {
    	try {
            ExitStandMsgController.Singleton().SendCloseBarrierMessage( p_Location );
      	} catch ( Exception e ) {}
    }

    public void OpenBarrier( final String p_Location ) throws XtumlException {
    	try {
            ExitStandMsgController.Singleton().SendOpenBarrierMessage( p_Location );
      	} catch ( Exception e ) {}
    }


    // relates and unrelates


    // instance selections


    // relationship selections


    // ports
    private ExitStandCarPark ExitStandCarPark;
    public ExitStandCarPark CarPark() {
        if ( null == ExitStandCarPark ) ExitStandCarPark = new ExitStandCarPark( this, null );
        return ExitStandCarPark;
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
            prop.load(getClass().getResourceAsStream("ExitStandProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("ExitStandProperties.properties"));
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
    public ExitStand context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}
