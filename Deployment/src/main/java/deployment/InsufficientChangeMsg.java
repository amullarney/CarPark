package deployment;

// Spring requires a POJ class for each message.
public class InsufficientChangeMsg {
	private String content;
	public InsufficientChangeMsg() {
	}
	public InsufficientChangeMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}