package deployment;


import deployment.CarparkControl;
import deployment.EntryStand;
import deployment.ExitStand;
import deployment.OperatorConsole;
import deployment.PaymentMachine;
import deployment.TestControl;

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
public class DeploymentApplication implements IApplication {

    private IComponent<?>[] components;
    private ApplicationExecutor[] executors;
    private static DeploymentApplication singleton;

    public DeploymentApplication() {
    	singleton = this;
        components = new IComponent<?>[6];
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
                executors[i] = new ApplicationExecutor( "DeploymentApplicationExecutor" + i, args, logger );
            }
            else {
                executors[i] = new ApplicationExecutor( "DeploymentApplicationExecutor" + i, args );
            }
        }
        components[0] = new CarparkControl(this, executors[0], 0);
        components[3] = new OperatorConsole(this, executors[0], 3);
        components[2] = new ExitStand(this, executors[0], 2);
        components[4] = new PaymentMachine(this, executors[0], 4);
        components[1] = new EntryStand(this, executors[0], 1);
        components[5] = new TestControl(this, executors[0], 5);
        ((OperatorConsole)components[3]).CarPark().satisfy(((CarparkControl)components[0]).Operator());
        ((CarparkControl)components[0]).Operator().satisfy(((OperatorConsole)components[3]).CarPark());
        ((ExitStand)components[2]).CarPark().satisfy(((CarparkControl)components[0]).Exit());
        ((CarparkControl)components[0]).Exit().satisfy(((ExitStand)components[2]).CarPark());
        ((PaymentMachine)components[4]).CarPark().satisfy(((CarparkControl)components[0]).Payer());
        ((CarparkControl)components[0]).Payer().satisfy(((PaymentMachine)components[4]).CarPark());
        ((EntryStand)components[1]).CarPark().satisfy(((CarparkControl)components[0]).Entry());
        ((CarparkControl)components[0]).Entry().satisfy(((EntryStand)components[1]).CarPark());
    }

    public CarparkControl CarparkControl() {
        return (CarparkControl)components[0];
    }
    public OperatorConsole OperatorConsole() {
        return (OperatorConsole)components[3];
    }
    public ExitStand ExitStand() {
        return (ExitStand)components[2];
    }
    public PaymentMachine PaymentMachine() {
        return (PaymentMachine)components[4];
    }
    public EntryStand EntryStand() {
        return (EntryStand)components[1];
    }
    public TestControl TestControl() {
        return (TestControl)components[5];
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
        SpringApplication.run( DeploymentApplication.class, args );
        singleton.start();
    }

}
