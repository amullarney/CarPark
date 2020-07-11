package deployment;

// Spring requires a POJ class for each message.
public class RemainingBalanceMsg {
	private String content;
	public RemainingBalanceMsg() {
	}
	public RemainingBalanceMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}