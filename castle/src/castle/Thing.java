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
		return "��ʹ����" + description;
	}
	
	public String pick() {
		return "�������" + description;
	}
}
