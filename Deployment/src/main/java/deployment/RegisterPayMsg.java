package deployment;

// Spring requires a POJ class for each message.
public class RegisterPayMsg {
	private String messageName;
	private String location;
	private String peripheral;
	private String dispenses;
	public RegisterPayMsg() {
	}
	public RegisterPayMsg( String messageName, String location, String peripheral, String dispenses ) {
		this.messageName = messageName;
		this.location = location;
		this.peripheral = peripheral;
		this.dispenses = dispenses;
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
	public String getDispenses() {
		return dispenses;
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
	public void setDispenses( String dispenses ) {
		this.dispenses = dispenses;
	}
}