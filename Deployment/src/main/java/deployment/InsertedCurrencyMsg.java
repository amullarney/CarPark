package deployment;

//Spring requires a POJ class for each message.
public class InsertedCurrencyMsg {
	private String location;
	private String amount;
	public InsertedCurrencyMsg() {
	}
	public InsertedCurrencyMsg( String location, String amount ) {
		this.location = location;
		this.amount = amount;
	}
	public String getLocation() {
		return location;
	}
	public String getAmount() {
		return amount;
	}
	public void setLocation( String location ) {
		this.location = location;
	}
	public void setAmount( String amount ) {
		this.amount = amount;
	}
}