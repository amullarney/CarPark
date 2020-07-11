package deployment;

// Spring requires a POJ class for each message.
public class ExitDeadlineMsg {
	private String content;
	public ExitDeadlineMsg() {
	}
	public ExitDeadlineMsg( String content ) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
}