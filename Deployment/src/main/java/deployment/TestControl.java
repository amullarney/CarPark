package deployment;


import deployment.testcontrol.timeutilities.TimeUtilities;
import deployment.testcontrol.timeutilities.TimeUtilitiesSet;
import deployment.testcontrol.timeutilities.impl.TimeUtilitiesImpl;
import deployment.testcontrol.timeutilities.impl.TimeUtilitiesSetImpl;

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


public class TestControl extends Component<TestControl> {

    private Map<String, Class<?>> classDirectory;
    private static TestControl singleton;

    public TestControl(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);
        TimeUtilities_extent = new TimeUtilitiesSetImpl();
        singleton = this;
        TIM = null;
        classDirectory = new TreeMap<>();
        classDirectory.put("TimeUtilities", TimeUtilitiesImpl.class);
    }
    
    public static TestControl Singleton() {
    	return singleton;
    }

    // domain functions
    public void AdvanceTime( final int p_hours,  final int p_minutes ) throws XtumlException {
    	try {
          new TimeUtilitiesImpl.CLASS(context()).AdvanceTime( p_hours, p_minutes );
    	} catch ( Exception e ) {}
    	System.out.printf( "AdvanceTime( hours: %d, minutes: %d )\n", p_hours, p_minutes );
    }

    public void SetTime( final int p_year,  final int p_month,  final int p_day,  final int p_hour,  final int p_minute ) {
        context().TIM().set_time( p_year, p_month, p_day, p_hour, p_minute, 0, 0 );
        System.out.printf( "SetTime( year: %d, month: %d, day: %d, hour: %d, minute: %d )\n", p_year, p_month, p_day, p_hour, p_minute );
    }



    // relates and unrelates


    // instance selections
    private TimeUtilitiesSet TimeUtilities_extent;
    public TimeUtilitiesSet TimeUtilities_instances() {
        return TimeUtilities_extent;
    }


    // relationship selections


    // ports


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
            prop.load(getClass().getResourceAsStream("TestControlProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("TestControlProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version_date", "Unknown");
    }

    @Override
    public boolean addInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot add empty instance to population." );
        if ( instance instanceof TimeUtilities ) return TimeUtilities_extent.add( (TimeUtilities)instance );
        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );
        if ( instance instanceof TimeUtilities ) return TimeUtilities_extent.remove( (TimeUtilities)instance );
        return false;
    }

    @Override
    public TestControl context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}
