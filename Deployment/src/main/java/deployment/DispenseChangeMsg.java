package deployment;

// Spring requires a POJ class for each message.
public class DispenseChangeMsg {
	private String content;
	public DispenseChangeMsg() {
	}
	public DispenseChangeMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}