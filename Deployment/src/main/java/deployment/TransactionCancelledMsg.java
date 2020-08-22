package deployment;

// Spring requires a POJ class for each message.
public class TransactionCancelledMsg {
	private String content;
	public TransactionCancelledMsg() {
	}
	public TransactionCancelledMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}