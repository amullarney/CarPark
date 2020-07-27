package deployment;

// Spring requires a POJ class for each message.
public class OccupancyUpdateMsg {
	private String messageName;
	private String occupancy;
	private String capacity;
	private String availability;
	public OccupancyUpdateMsg() {
	}
	public OccupancyUpdateMsg( String messageName, String occupancy, String capacity, String availability ) {
		this.messageName = messageName;
		this.occupancy = occupancy;
		this.capacity = capacity;
		this.availability = availability;
	}
	public String getMessageName() {
		return messageName;
	}
	public String getOccupancy() {
		return occupancy;
	}
	public String getCapacity() {
		return capacity;
	}
	public String getAvailability() {
		return availability;
	}
	public void setMessageName( String messageName ) {
		this.messageName = messageName;
	}
	public void setOccupancy( String occupancy ) {
		this.occupancy = occupancy;
	}
	public void setCapacity( String capacity ) {
		this.capacity = capacity;
	}
	public void setAvailability( String availability ) {
		this.availability = availability;
	}
}