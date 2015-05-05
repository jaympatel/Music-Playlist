package com.marketo;



public class Main {

	public static void main(String[] args) {
	
		try{
			CommandManager cm=new CommandManager();
			while(true){
				cm.printMainMenu();
				String command = cm.getCommand();
				if(command.toLowerCase().equals("quit"))
					break;
				cm.parseCommand(command);
				while (cm.subMenuFlag){
					cm.printPlaylistMenu();
					String subCommand=cm.getCommand();
					if(subCommand.toLowerCase().equals("main")){
						cm.subMenuFlag=false;
						break;
					}
					cm.parseCommand(subCommand);
				}
				
			}
		}
		catch(Exception e){
			System.out.println("Oops!! Something went wrong. Please try again!!");
			//e.printStackTrace();
		}
	}

}
