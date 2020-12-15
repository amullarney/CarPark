package deployment;

// Spring requires a POJ class for each message.
public class HelpRequestMsg {
	private String messageName;
	private String location;
	private String peripheral;
	public HelpRequestMsg() {
  	  System.out.printf( "Creating HelpRequest message \n" );    			
	}
	public HelpRequestMsg( String messageName, String location, String peripheral ) {
		this.messageName = messageName;
		this.location = location;
		this.peripheral = peripheral;
	}
	public String getMessageName() {
		return messageName;
	}
	public String getLocation() {
		return location;
	}
	public String getPeripheral() {
		return peripheral;
	}
	public void setMessageName( String messageName ) {
		this.messageName = messageName;
	}
	public void setLocation( String location ) {
  	  System.out.printf( "Setting HelpRequest location\n" );    			
		this.location = location;
	}
	public void setPeripheral( String peripheral ) {
		this.peripheral = peripheral;
	}
}