package deployment;

// Spring requires a POJ class for each message.
public class CurrentDateTimeMsg {
	private String content;
	public CurrentDateTimeMsg() {
	}
	public CurrentDateTimeMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}