package deployment;

//Spring requires a POJ class for each message.
public class InsertedCurrencyMsg {
	private String location;
	private String amount;
	public InsertedCurrencyMsg() {
		System.out.printf( "InsertedCurrencyMsg()\n" );
	}
	public InsertedCurrencyMsg( String location, String amount ) {
		this.location = location;
		this.amount = amount;
	}
	public String getLocation() {
		System.out.printf( "InsertedCurrencyMsg.getLocation(), %s\n", location );
		return location;
	}
	public String getAmount() {
		System.out.printf( "InsertedCurrencyMsg.getAmount(), %s\n", amount );
		return amount;
	}
	public void setLocation( String location ) {
		System.out.printf( "InsertedCurrencyMsg.setLocation( %s )\n", location );
		this.location = location;
	}
	public void setAmount( String amount ) {
		System.out.printf( "InsertedCurrencyMsg.setAmount( %s )\n", amount );
		this.amount = amount;
	}
}