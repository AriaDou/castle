package castle;

import java.util.ArrayList;
import java.util.HashMap;

public class NPC {
	private String description;
	ArrayList<String> talkPrompt = new ArrayList<String>();
	HashMap<String, String> usePrompt = new HashMap<String, String>();
	

	public NPC(String description) {
		this.description = description;
	}
	
	public void setTalkPrompt(String prompt) {
		talkPrompt.add(description + ":\"" + prompt + "\"");
	}
	
	public void setUsePrompt(String thing, String prompt) {
		usePrompt.put(thing, description + ":\"" + prompt + "\"");
	}
	
	public String getDescription(){
		return description;
	}

	public boolean canUseThing(String thing) {
		return usePrompt.containsKey(thing);
	}

	public String use(String thing) {
		return usePrompt.get(thing);
	}
	
	public String talk(){
		int i = talkPrompt.size();
		return talkPrompt.get((int)(Math.random()*100)%i);
	}
}
