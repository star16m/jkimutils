package star16m.jkimutils.contents;

public class Header {

	private int targets;
	private String title;
	public Header(int targets, String title) {
		super();
		this.targets = targets;
		this.title = title;
	}
	public int getTargets() {
		return targets;
	}
	public String getTitle() {
		return title;
	}
}
