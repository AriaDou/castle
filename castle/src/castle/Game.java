package castle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private Room changeRoom;//具有任意门功能的房间
    Scanner in = new Scanner(System.in);
    
    HashMap<String, Handler> handlers = new HashMap<String, Handler>();
    HashMap<String, Thing> things = new HashMap<String, Thing>();
    HashMap<String, NPC> npcs = new HashMap<String, NPC>();
    HashMap<String, Thing> bag = new HashMap<String, Thing>();
    ArrayList<Room> rooms = new ArrayList<Room>();
    
    class Handler{
    	public void doCmd(String word) {}
    	public void useThing(String npc, String thing) {}
    	public boolean isBye() {
    		return false;
    	}
    }
    
        
    //初始化
    public Game() 
    {
    	handlers.put("go", new Handler(){
    		@Override
    		public void doCmd(String word) {
    			goRoom(word);
    		}
    	});
    	handlers.put("help", new Handler(){
    		@Override
    		public void doCmd(String word) {
    			System.out.println("迷路了吗？你可以做的命令有：go bye help pick talk use");
    	        System.out.println("如：\tgo east");
    	        System.out.println("\tuse sth.");
    	        System.out.println("\tpick sth.");
    	        System.out.println("\ttalk sb.");
    		}
    	});
    	handlers.put("bye", new Handler(){
    		@Override
    		public boolean isBye() {
    			return true;
    		}
    	});
    	handlers.put("pick", new Handler(){
    		@Override
    		public void doCmd(String word) {
    			pick(word);
    		}
    	});
    	handlers.put("talk", new Handler(){
    		@Override
    		public void doCmd(String word) {
    			talk(word);
    		}
    	});
    	handlers.put("use", new Handler(){
    		@Override
    		public void doCmd(String word) {
    			System.out.println("操作对象是？");
    			String npc = in.nextLine();
    			use(word, npc);
    		}
    	});
    	handlers.put("dimond", new Handler(){
    		@Override
    		public void useThing(String npc, String thing) {
    			System.out.println(things.get(thing).use());
				System.out.println(npcs.get(npc).use(thing));
    			if(npc.equals("princess")){
					System.out.println("通关啦，不过这结局你满意吗？");
					System.out.println("你可以再想想，或者输入bye退出游戏");
				}
			}
    	});
    	handlers.put("sword", new Handler(){
    		@Override
    		public void useThing(String npc, String thing){
    			System.out.println(things.get(thing).use());
				System.out.println(npcs.get(npc).use(thing));
				currentRoom.removeNPC(npc);
				npcs.remove(npc);
    		}
    	});
    	
    	createThings();
    	createNPCs();
        createRooms();
    }
    
    private void createThings()
    { 
        things.put("dimond", new Thing("dimond"));
        things.put("sword", new Thing("sword"));
        things.put("key", new Thing("key"));
    }
    
    private void createNPCs()
    {
    	NPC dragon = new NPC("dragon");
    	NPC princess = new NPC("princess");
    	
    	npcs.put("dragon", dragon);
    	npcs.put("princess", princess);
       
    	dragon.setTalkPrompt("偷偷告诉你一个小秘密：公主喜欢我唷⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄");
    	dragon.setTalkPrompt("下周给公主买什么包好呢？");
    	dragon.setTalkPrompt("这样过一辈子也是挺幸福的呢");
    	dragon.setUsePrompt("sword", "对不起啊公主，没能陪你到最后呢...");
    	dragon.setUsePrompt("dimond", "走开！我是直男！");
    	
    	princess.setTalkPrompt("我对龙宝的爱天地可证！");
    	princess.setUsePrompt("dimond", "最喜欢的就是你了啦O(∩_∩)O~~什么时候结婚？么么哒");
    	princess.setUsePrompt("sword", "???");
    }

    private void createRooms()
    {
      
        //	制造房间
        ThingRoom cavern = new ThingRoom("洞穴", false);
        Room outside = new Room("城堡外", false);
        ThingRoom lobby = new ThingRoom("大堂", false);
        ThingRoom pub = new ThingRoom("小酒吧", false);
        Room study = new Room("书房", false);
        ThingRoom bedroom = new ThingRoom("卧室", true);
        
        //放入任意门队列
        changeRoom = cavern;
        rooms.add(pub);
        rooms.add(outside);
        rooms.add(study);
        rooms.add(lobby);
        
        //	初始化房间的出口
        cavern.setExits(rooms.get((int)(Math.random()*100)%4), null, outside, null);
        outside.setExits(cavern, lobby, study, pub);
        lobby.setExits(null, null, null, outside);
        pub.setExits(null, outside, null, null);
        study.setExits(outside, bedroom, null, null);
        bedroom.setExits(null, null, null, study);
        
        //初始化房间的物品和NPC
        cavern.setThing("sword", things.get("sword"));
        lobby.setNPC("dragon", npcs.get("dragon"));
        lobby.setThing("key", things.get("key"));
        pub.setThing("dimond", things.get("dimond"));
        bedroom.setNPC("princess", npcs.get("princess"));

        currentRoom = outside;  //	从城堡门外开始
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("欢迎来到克拉大陆！");
        System.out.println("想要打败恶龙，赢取白富美，走上人生巅峰吗？试试看通关吧~");
        System.out.println("如果需要帮助，请输入 'help' 。");
        System.out.println();
        printRoomMess();  
        printPeopleMess();
    }
    
    public void printRoomMess(){
    	System.out.println("现在你在" + currentRoom);
        System.out.print("出口有：");
        System.out.println(currentRoom.printExit());
        if(currentRoom.hasThingOrNPC()){
        	System.out.print("这儿有：");
        	System.out.println(currentRoom.printPrompt());
        }
    }
    
    public void printPeopleMess(){
    	if(bag.isEmpty()){
    		System.out.print("现在你身上没有东西。");
    	}	
    	else{
    		System.out.print("现在你身上有：");
    		for(String name : bag.keySet())
    			System.out.print(name + " ");
    		}
    	System.out.println();
    }

    // 以下为用户命令

    public void goRoom(String direction) 
    {
    	if(currentRoom.equals(changeRoom))
            currentRoom.setChangeExits("north", rooms.get((int)(Math.random()*100)%4));
    	
		Room nextRoom = currentRoom.goExit(direction);

		if (nextRoom == null) {
			System.out.println("那里没有门！");
	    }
	    else {
	    	if (nextRoom.getNeedKey() && !bag.containsKey("key"))
	    		System.out.println("没有钥匙进不去哦~");
	    	else{
	            currentRoom = nextRoom;
	            printRoomMess();
	            printPeopleMess();
	        }
		}
    }
    
    public void pick(String name) {
    	if(name.equals("")){
			System.out.println("指令错误");
			return;
		}
    	Thing thing = currentRoom.getThing(name);
		if(thing != null){
			if(currentRoom.getHasNPC()){
	    		System.out.println("在别人面前偷东西不好吧");
	    		return;
	    		}
			currentRoom.removeThing(name);
			bag.put(name, thing);	
			System.out.println(thing.pick());
			return;
		}
		System.out.println("这儿没这东西");
	}
    
    public void talk(String name){
    	NPC npc = currentRoom.getNPC(name);
    	if(npc != null){
    		System.out.println(npc.talk());
    		return;
    	}
    	System.out.println("为什么要自言自语呢？");
    }
    
    public void use(String thing, String npc) {
    	NPC useNPC = currentRoom.getNPC(npc);
    	if(!bag.containsKey(thing)){
    		System.out.println("现在身上没有该物品");
    		return;
    	}
    	if(useNPC == null){
    		System.out.println("屋内没有该对象");
    		return;
    	}
    	if(useNPC.canUseThing(thing)){
			handlers.get(thing).useThing(npc, thing);
			return;
    	}
		System.out.println("该物品暂时没啥用处");
	}

    public void play() {
		
    	while (npcs.containsKey("princess") || npcs.containsKey("dragon")) {
    		String line = in.nextLine();
    		String[] words = line.split(" ");
    		Handler comand = handlers.get(words[0]);
    		String value = "";
    		if(words.length > 1)
    			value = words[1];
    		if(comand != null){
    			if(comand.isBye())
        			break;
    			comand.doCmd(value);
    		}
        }
    	System.out.println("感谢您的光临。再见！");
        in.close();
	}
    
	public static void main(String[] args) {
		Game game = new Game();
		game.printWelcome();
		game.play();
	}

}
