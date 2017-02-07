package castle;

import java.util.HashMap;

public class Room {
    private String description;
    private boolean needKey;
    private boolean hasThing = false;
    private boolean hasNPC = false;
    HashMap<String, Room> exitRoom = new HashMap<String, Room>();

    public Room(String description, boolean needKey) 
    {
        this.description = description;
        this.needKey = needKey;
    }
    
    public String printExit(){
    	StringBuffer exit = new StringBuffer();
    	for (String key: exitRoom.keySet()) {
    		exit.append(key);
    		exit.append(' ');
		}
        return exit.toString();
    }
    
	public String printPrompt() {
		return null;
	}

	public boolean getNeedKey() {
		return needKey;
	}
	
    public Room goExit(String direction) 
    {
    	return exitRoom.get(direction);
    }

    public void setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null)
            exitRoom.put("north", north);
        if(east != null)
        	exitRoom.put("east", east);
        if(south != null)
        	exitRoom.put("south", south);
        if(west != null)
        	exitRoom.put("west", west);
    }
    
    public void setChangeExits(String direction, Room changeRoom){
    	exitRoom.replace(direction, changeRoom);
    }
    
    public void setHasThing(boolean hasThing) {
		this.hasThing = hasThing;
    }
    
    public void setHasNPC(boolean hasNPC) {
		this.hasNPC = hasNPC;
    }
    
    public boolean getHasThing() {
		return hasThing;
	}
    
    public boolean getHasNPC(){
    	return hasNPC;
    }
    
    public boolean hasThingOrNPC() {
		if(!hasNPC && !hasThing)
			return false;
		return true;
	}
    
    public Thing getThing(String name) {
		return null;
	}

	public NPC getNPC(String name) {
		return null;
	}
	
	public void removeThing(String name) {}

	public void removeNPC(String name) {}
    
    @Override
    public String toString()
    {
        return description;
    }
}
