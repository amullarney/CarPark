package deployment;

// Spring requires a POJ class for each message.
public class RegisterMsg {
	private String messageName;
	private String location;
	private String peripheral;
	public RegisterMsg() {
	}
	public RegisterMsg( String messageName, String location, String peripheral ) {
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
		this.location = location;
	}
	public void setPeripheral( String peripheral ) {
		this.peripheral = peripheral;
	}
}