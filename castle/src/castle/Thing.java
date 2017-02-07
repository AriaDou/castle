package castle;

public class Thing {
	private String description;
	
	public Thing(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String use() {
		return "你使用了" + description;
	}
	
	public String pick() {
		return "你捡起了" + description;
	}
}
