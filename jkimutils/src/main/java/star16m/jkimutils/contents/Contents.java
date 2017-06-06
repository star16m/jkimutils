package star16m.jkimutils.contents;

import java.util.List;

public class Contents {

	private String key;
	private String title;
	private String description;
	private List<Header> header;
	private List<String[]> data;
	public Contents(String key, String title, String description) {
		super();
		this.key = key;
		this.title = title;
		this.description = description;
	}
	public String getKey() {
		return key;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public List<Header> getHeader() {
		return header;
	}
	public void setHeader(List<Header> header) {
		this.header = header;
	}
	public List<String[]> getData() {
		return data;
	}
	public void setData(List<String[]> data) {
		this.data = data;
	}
}
