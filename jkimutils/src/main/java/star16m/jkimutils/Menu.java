package star16m.jkimutils;

public class Menu {
	
	private String name;
	private String link;
	public Menu(String name, String link) {
		super();
		this.name = name;
		this.link = link;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getHTMLString() {
		return "<li><a href='" + getLink() + "'>" + getName() + "</a></li>";
	}
}
