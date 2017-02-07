package castle;

import java.util.HashMap;

public class ThingRoom extends Room {
	HashMap<String, Thing> things = new HashMap<String, Thing>();
	HashMap<String, NPC> npcs = new HashMap<String, NPC>();
	
	public ThingRoom(String description, boolean needKey) {
		super(description, needKey);
	}
	
	public void setThing(String name, Thing thing) {
		things.put(name, thing);
		setHasThing(true);
	}
	
	public void setNPC(String name, NPC npc) {
		npcs.put(name, npc);
		setHasNPC(true);
	}

	@Override
	public void removeThing(String name) {
		things.remove(name);
		if(things.isEmpty())
			setHasThing(false);
	}

	@Override
	public void removeNPC(String name) {
		npcs.remove(name);
		if(npcs.isEmpty())
			setHasNPC(false);
	}

	@Override
	public Thing getThing(String name) {
		return things.get(name);
	}
	
	@Override
	public NPC getNPC(String name) {
		return npcs.get(name);
	}

	@Override
	public String printPrompt() {
		StringBuffer prompt = new StringBuffer();
		if(!things.isEmpty())
			for(String name: things.keySet()){
				prompt.append(name);
				prompt.append(" ");
			}
		if(!npcs.isEmpty())
			for(String name: npcs.keySet()){
				prompt.append(name);
				prompt.append(" ");
			}
		return prompt.toString();
	}
}
