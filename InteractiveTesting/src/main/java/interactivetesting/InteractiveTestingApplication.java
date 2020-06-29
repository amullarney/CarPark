package interactivetesting;


import interactivetesting.CarparkControl;
import interactivetesting.InteractiveTestbench;

import io.ciera.runtime.summit.application.ApplicationExecutor;
import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.ILogger;
import io.ciera.runtime.summit.application.tasks.GenericExecutionTask;
import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class InteractiveTestingApplication implements IApplication {

    private IComponent<?>[] components;
    private ApplicationExecutor[] executors;
    private static InteractiveTestingApplication singleton;

    public InteractiveTestingApplication() {
    	singleton = this;
        components = new IComponent<?>[2];
        executors = new ApplicationExecutor[1];
        setup( null, null );
        initialize();
    }

    @Override
    public void run() {
        for (ApplicationExecutor executor : executors) {
            executor.run();
        }
    }

    @Override
    public void setup( String[] args, ILogger logger ) {
        for ( int i = 0; i < executors.length; i++ ) {
            if ( null != logger ) {
                executors[i] = new ApplicationExecutor( "InteractiveTestingApplicationExecutor" + i, args, logger );
            }
            else {
                executors[i] = new ApplicationExecutor( "InteractiveTestingApplicationExecutor" + i, args );
            }
        }
        components[0] = new CarparkControl(this, executors[0], 0);
        components[1] = new InteractiveTestbench(this, executors[0], 1);
        ((InteractiveTestbench)components[1]).EntryStand().satisfy(((CarparkControl)components[0]).Entry());
        ((CarparkControl)components[0]).Entry().satisfy(((InteractiveTestbench)components[1]).EntryStand());
        ((InteractiveTestbench)components[1]).ExitStand().satisfy(((CarparkControl)components[0]).Exit());
        ((CarparkControl)components[0]).Exit().satisfy(((InteractiveTestbench)components[1]).ExitStand());
        ((InteractiveTestbench)components[1]).Operator().satisfy(((CarparkControl)components[0]).Operator());
        ((CarparkControl)components[0]).Operator().satisfy(((InteractiveTestbench)components[1]).Operator());
        ((InteractiveTestbench)components[1]).Payer().satisfy(((CarparkControl)components[0]).Payer());
        ((CarparkControl)components[0]).Payer().satisfy(((InteractiveTestbench)components[1]).Payer());
    }

    public CarparkControl CarparkControl() {
        return (CarparkControl)components[0];
    }
    public InteractiveTestbench InteractiveTestbench() {
        return (InteractiveTestbench)components[1];
    }

    @Override
    public void initialize() {
        for ( IComponent<?> component : components ) {
            component.getRunContext().execute( new GenericExecutionTask() {
                @Override
                public void run() throws XtumlException {
                    component.initialize();
                }
            });
        }
    }

    @Override
    public void start() {
        if (1 == executors.length) {
            executors[0].run();
        }
        else {
            for ( ApplicationExecutor executor : executors ) {
                executor.start();
            }
        }
    }

    @Override
    public void printVersions() {
        io.ciera.runtime.Version.printVersion();
        for ( IComponent<?> c : components ) {
            System.out.printf("%s: %s (%s)", c.getClass().getName(), c.getVersion(), c.getVersionDate());
            System.out.println();
        }
    }

    @Override
    public void stop() {
        for ( ApplicationExecutor executor : executors ) {
            executor.execute(new HaltExecutionTask());
        }
    }

    public static void main( String[] args ) {
        SpringApplication.run( InteractiveTestingApplication.class, args );
        singleton.start();
    }

}
