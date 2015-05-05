package com.marketo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CommandManager {

	boolean subMenuFlag=false;
	int currentPlaylistID;
	SongFile songFile;
	
	public CommandManager(){
		
		// Path to the song list file
		String songListFileName="SongList.txt"; ///Users/Jay/Documents/workspace/MM/src/com/marketo/
		// Enter the delimiter here for the given file for <artist> and <title> separation  
		// In the given file the two are separated by the two or more space "\\s{2,}"
		String delimiter="\\s{2,}";
		
		songFile=new SongFile(songListFileName,delimiter);
	}
	
	// Method to print the main menu
	
	void printMainMenu(){
		System.out.println("\n\nMarketo Music");
		System.out.println("Main Menu");
		System.out.println("1. Create Playlist: create <playlist name>");
		System.out.println("2. Edit Playlist: edit <playlist id>");
		System.out.println("3. Print Song: song <song id>");
		System.out.println("4. Print Playlist: playlist <playlist id>");
		System.out.println("5. Print All Songs or Playlists: print song/playlist");
		System.out.println("6. Search Song: search artist/title <string of words to be searched>");
		System.out.println("7. Sort songs: sort artist/title");
		System.out.println("8. Quit: quit");
		System.out.print("Enter a Command : ");
	}
	
	// Method to print the play list menu / sub menu
	
	void printPlaylistMenu(){
		System.out.println("\n\nPlaylist Menu:");
		System.out.println("1. Delete Song: delete <song id>");
		System.out.println("2. Insert Song: insert <song id>");
		System.out.println("3. Search and Insert Song: insert_search title/artist <string of words to be searched>");
		System.out.println("4. Print Playlist: print");
		System.out.println("5. Sort songs: sort artist/title");
		System.out.println("6. Search Song: search artist/title <string of words to be searched>");
		System.out.println("7. Main Menu: main");
		System.out.print("Enter a Command : ");
	}
	
	// Method to check is command is for main menu or sub menu
	
	void parseCommand(String command){
		if(command!=null && command.length()>0){
			if(!subMenuFlag){
				mainMenuCommand(command);
			}
			else{
				subMenuCommand(command);
			}
		}
		else{
			System.out.println("Please enter a valid command.");
		}
	}
	
	// Method to read command from command line
	
	String getCommand(){
		String command="";
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			command = br.readLine();
		}
		catch(Exception e){
			System.out.println("Somethinf went wrong with the system input!!   Please try again.");
		}
		return command;
	}
	
	// Method to parse the main menu commands and take actions
	
	void mainMenuCommand(String command){
		String[] strArray=command.split("\\s");
		String mainCommand=strArray[0];
		
		// if - else if - else loop to check for the particular command and take actions according those commands
		
		
		// Block to print the song details for given song id
		if(mainCommand.equals("song")){
			try{
				if(strArray.length==2){
					int songID=Integer.parseInt(strArray[1]);
					songFile.printSong(songID);
				}
				else{
					System.out.println("Invalid Command!!");
				}
				
			}
			catch(Exception e){
				System.out.println("Enter valid song id!!");
			}
		}
		
		// Block to print the playlist details for given playlist id
		else if(mainCommand.equals("playlist")){
			try{
				if(strArray.length==2){
					int playlistID=Integer.parseInt(strArray[1]);
					songFile.printPlaylist(playlistID);
				}
				else{
					System.out.println("Invalid Command!!");
				}
				
			}
			catch(Exception e){
				System.out.println("Enter valid playlist id!!");
			}
		}
		
		// Block to whole song list or all playlist depending on the user input
		else if(mainCommand.equals("print")){
			try{
				if(strArray.length==2){
					String subCommand=strArray[1];
					if(subCommand.equals("song")){
						songFile.printAllSongs();
					}
					else if(subCommand.equals("playlist")){
						songFile.printAllplaylist();
					}
					else{
						System.out.println("Invalid Command!!");
					}
				}
				else{
					System.out.println("Invalid Command!!");
				}
			}
			catch(Exception e){
				System.out.println("Invalid print command!!");
			}
		}
		
		// Block to perform search for songs in the song list with user given attribute and search terms
		else if(mainCommand.equals("search")){
			try{
				if(strArray.length > 2){
					String subCommand=strArray[1];
					// Get search string from command
					String songAttributeValue=command.substring(command.indexOf("\"")+1,command.lastIndexOf("\"")).toLowerCase(); 
					if(subCommand.equals("artist")||subCommand.equals("title")){
						ArrayList<Song> list=songFile.searchSong(subCommand, songAttributeValue);
						songFile.printArrayList(list);
					}
					else{
						System.out.println("Invalid Command!! ");
					}
				}
				else{
					System.out.println("Invalid Command!!No search terms");
				}
				
			}
			catch(Exception e){
				System.out.println("Invalid search command!!");
			}
		}
		
		// Block to sort the song list on user given song attribute
		else if(mainCommand.equals("sort")){
			try{
				if(strArray.length==2){
					String subCommand=strArray[1];
					if(subCommand.equals("artist")||subCommand.equals("title")){
						songFile.sortSongs(subCommand);
					}
					else{
						System.out.println("Invalid Command!!");
					}
				}
				else{
					System.out.println("Invalid Command!!");
				}	
				
			}
			catch(Exception e){
				System.out.println("Invalid sort command!!");
			}
		}
		
		// Block to create the new playlist and go to playlist menu
		else if(mainCommand.equals("create")){
			try{
				if(strArray.length==2){
					String playlistName=strArray[1];
					currentPlaylistID=songFile.createPlaylist(playlistName);
					// Flag which takes to playlist menu / sub menu
					this.subMenuFlag=true;
				}
				else{
					System.out.println("Invalid Command!!");
				}
				
			}
			catch(Exception e){
				System.out.println("Invalid create command!!");
			}
		}
		
		// Block to edit the given playlist id
		else if(mainCommand.equals("edit")){
			try{
				if(strArray.length==2){
					currentPlaylistID=Integer.parseInt(strArray[1]);
					if(songFile.isPlaylistIdAvailable(currentPlaylistID)){// currentPlaylistID<songFile.playlistID){
						this.subMenuFlag=true;
					}
					else{
						System.out.println("Invalid Playlist ID!!");
					}
				}
				else{
					System.out.println("Invalid Command!!");
				}
				
			}
			catch(Exception e){
				System.out.println("Invalid edit command!!");
			}
		}
		
		// For all other invalid commands
		else{
			System.out.println("Please enter valid command.");
		}
	
	}
	
	// Method to parse sub menu commands and take actions
	
	void subMenuCommand(String command){
		String[] strArray=command.split("\\s");
		String mainCommand=strArray[0];
		
		// if - else if - else loop to check for the particular command and take actions according those commands
		
		// Block to delete the given song id from the current playlist
		if(mainCommand.equals("delete")){
			try{
				if(strArray.length==2){
					int songID=Integer.parseInt(strArray[1]);
					Playlist pl=songFile.playlistArray.get(currentPlaylistID);
					pl.deleteSong(new Integer(songID));
				}
				else{
					System.out.println("Invalid Command!!");
				}
				
			}
			catch(Exception e){
				System.out.println("Invalid delete command!!");
			}
		}
		
		// Block to insert the given song id to current playlist
		else if(mainCommand.equals("insert")){
			try{
				if(strArray.length==2){
					int songID=Integer.parseInt(strArray[1]);
					if(songFile.isSongIdAvailable(songID)){//songID<songFile.songID){
						Playlist pl=songFile.playlistArray.get(currentPlaylistID);
						pl.insertSong(new Integer(songID));
					}
					else{
						System.out.println("Song with the given ID does not exists!!");
					}
				}
				else{
					System.out.println("Invalid Command!!");
				}
				
				
			}
			catch(Exception e){
				System.out.println("Invalid insert command!!");
			}
		}
		
		// Block to print the current playlist 
		else if(mainCommand.equals("print")){
			if(strArray.length==1){
				songFile.printPlaylist(currentPlaylistID);
			}
			else{
				System.out.println("Invalid Command!!");
			}
			
		}
		
		// Block to sort and print the songs of the current playlist by the given song attribute 
		else if(mainCommand.equals("sort")){
			try{
				if(strArray.length==2){
					String subCommand=strArray[1];
					if(subCommand.equals("artist") || subCommand.equals("title")){
						songFile.sortPlaylistSongs(currentPlaylistID, subCommand);
					}
					else{
						System.out.println("Invalid Command!!");
					}
				}
				else{
					System.out.println("Invalid Command!!");
				}
				
			}
			catch(Exception e){
				System.out.println("Invalid sort command!!");
			}
			
		}
		
		// Block to search the playlist for the songs with user given attributes and search terms
		else if(mainCommand.equals("search")){
			try{
				if(strArray.length>2){
					String subCommand=strArray[1];
					// Get search string from command
					String songAttributeValue=command.substring(command.indexOf("\"")+1,command.lastIndexOf("\"")).toLowerCase();
					if(subCommand.equals("artist") || subCommand.equals("title")){
						ArrayList<Song> list=songFile.searchPlaylist(currentPlaylistID, subCommand, songAttributeValue);
						songFile.printArrayList(list);
					}
					else{
						System.out.println("Invalid Command!!");
					}
				}
				else{
					System.out.println("Invalid Command!! No search terms");
				}
				
			}
			catch(Exception e){
				System.out.println("Invalid search command!!");
			}
			
		}
		
		// Block to search the for the songs and insert to the current playlist
		else if(mainCommand.equals("insert_search")){
			try{
				if(strArray.length>2){
					String subCommand=strArray[1];
					// Get search string from command
					String songAttributeValue=command.substring(command.indexOf("\"")+1,command.lastIndexOf("\"")).toLowerCase();
					if(subCommand.equals("artist") || subCommand.equals("title")){
						ArrayList<Song> list=songFile.searchSong(subCommand, songAttributeValue);
						Playlist playlist=songFile.playlistArray.get(currentPlaylistID);
						if(list!= null && list.size()>0){
							for(Song s:list){
								System.out.println("Inserting song:"+s.getSongArtist());
								playlist.insertSong(s.getSongId());
							}
						}
						else{
							System.out.println("No matching songs found!!");
						}
					}
					
					else{
						System.out.println("Invalid Command!!");
					}
				}
				else{
					System.out.println("Invalid Command!! No search terms");
				}
				
			}
			catch(Exception e){
				System.out.println("Invalid insert_search command!!");
			}
		}
		
		// Block for other invalid commands
		else{
			System.out.println("Please enter a valid command.");
		}
		
	
	}
	
}
