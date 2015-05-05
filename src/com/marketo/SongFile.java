package com.marketo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;


/*
 * SongFile - Main data store to store songs and playlists
 */

public class SongFile {
	private int songID=0;
	private int playlistID=0;
	ArrayList<Song> songList;
	ArrayList<Playlist> playlistArray;

	// Constructor to initialize the song file object from provided file path
	
	public SongFile(String songListFileName,String delimiter) {
		playlistArray= new ArrayList<Playlist>();
		try{
			FileReader fileReader = new FileReader(songListFileName);
			BufferedReader br = new BufferedReader(fileReader);
			songList= new ArrayList<Song>();
			String line;
			while ((line = br.readLine()) != null) {
				// Splitting the line with the provided delimiter
				String[] strSplit=line.split(delimiter);                      
				if(strSplit.length==2){                                       
					Song song=new Song();
					// creating unique song id then storing it in the song object with the artist name and song title 
					song.setSongId(this.songID++);
					song.setSongArtist(strSplit[0]);
					song.setSongTitle(strSplit[1]);
					// storing it to data structure for future use
					this.songList.add(song);
				}	    
			}
			br.close();
		}
		catch(FileNotFoundException fe){
			System.out.println("File not found!! Please provide valid path to file.");
			System.exit(1);
		}
		catch(Exception e){
			System.out.println("Oops!! Something went wrong while reading file. Please try again!!");
			System.exit(1);
			//e.printStackTrace();
		}
	}
	
	// Method to create new playlist
	
	public int createPlaylist(String playlistname){
		// create new playlist id then create new playlist with unique id and user provided name
		int newPlaylistID=playlistID++;
		Playlist playlist=new Playlist();
		LinkedList<Integer> songList=new LinkedList<Integer>();
		playlist.setPlaylistID(newPlaylistID);
		playlist.setPlaylistName(playlistname);
		playlist.setSongIDs(songList);
		// store playlist in the data structure for future use
		playlistArray.add(playlist);
		System.out.println("New Playlist created with name: "+ playlist.getPlaylistName()+" id: "+playlist.getPlaylistID());
		return newPlaylistID;
	}
	
	// Method to sort the playlist songs and print them
	
	public void sortPlaylistSongs(int currentPlaylist,String songAttribute){
		// create new comparator object with the user provided attribute to sort the songs
		SongCompare sc=new SongCompare(songAttribute);
		ArrayList<Song> sortedList=new ArrayList<Song>();
		Playlist playlist=playlistArray.get(currentPlaylist);
		ListIterator<Integer> listIterator = playlist.getSongIDs().listIterator();
		// create the arraylist from the linked list of songs
        while (listIterator.hasNext()) {
            int songID=listIterator.next();
            sortedList.add(this.songList.get(songID));
        }
        // sort the arraylist and print
        Collections.sort(sortedList,sc);
		this.printArrayList(sortedList);
	}
	
	// Method to search the playlist for the particular search term in the songs 
	
	public ArrayList<Song> searchPlaylist(int currentPlaylist,String songAttribute,String songAttributeValue){
		ArrayList<Song> playlistSongList=new ArrayList<Song>();
		Playlist playlist=playlistArray.get(currentPlaylist);
		ArrayList<Song> matchedSongs=new ArrayList<Song>();
		ListIterator<Integer> listIterator = playlist.getSongIDs().listIterator();
		// create the arraylist from the linked list of songs
        while (listIterator.hasNext()) {
            int songID=listIterator.next();
            playlistSongList.add(this.songList.get(songID));
        }
        // search the arraylist for the particular search term
        for(Song song : playlistSongList){
			if(songAttribute.equals("artist")){
				if(song.getSongArtist().toLowerCase().contains(songAttributeValue)){
					matchedSongs.add(song);
				}
			}
			if(songAttribute.equals("title")){
				if(song.getSongTitle().toLowerCase().contains(songAttributeValue)){
					matchedSongs.add(song);
				}
			}
		}
		return matchedSongs;
	}
	
	// Method to sort the entire song list on the user provided attribute
	
	public void sortSongs(String songAttribute){
		// create new comparator object with the user provided attribute to sort the songs
		SongCompare sc=new SongCompare(songAttribute);
		ArrayList<Song> sortedList=new ArrayList<Song>();
		sortedList.addAll(this.songList);
		// sort the list and print
		Collections.sort(sortedList,sc);
		this.printArrayList(sortedList);
		
	}
	
	// Method to search entire the song list for the particular search terms
	
	public ArrayList<Song> searchSong(String songAttribute,String songAttributeValue){
		ArrayList<Song> matchedSongs=new ArrayList<Song>();
		for(Song song : this.songList){
			if(songAttribute.equals("artist")){
				if(song.getSongArtist().toLowerCase().contains(songAttributeValue)){
					matchedSongs.add(song);
				}
			}
			if(songAttribute.equals("title")){
				if(song.getSongTitle().toLowerCase().contains(songAttributeValue)){
					matchedSongs.add(song);
				}
			}
		}
		return matchedSongs;
	}
	
	// Method to print all the songs in the data store
	
	public void printAllSongs(){
		this.printArrayList(this.songList);
	}
	
	// Method to print all playlists in the data store
	
	public void printAllplaylist(){
		for(int cnt=0;cnt<playlistArray.size();cnt++){
			this.printPlaylist(cnt);
		}
	}
	
	// Method to print song details for the given song id
	
	public void printSong(int songID){
		Song song=this.songList.get(songID);
		if(song!=null){
			System.out.println("Song ID: "+song.getSongId());
			System.out.println("Song Artist: "+song.getSongArtist());
			System.out.println("Song Title: "+song.getSongTitle());
		}
		else{
			System.out.println("Oops!! Song ID does not exist in the Data Store!!");
		}
	}
	
	// Method to print the playlist details for the given playlist id
	
	public void printPlaylist(int playlistID){
		if(playlistID < this.playlistID){
			Playlist playlist=playlistArray.get(playlistID);
			System.out.println("Playllist ID: "+playlistID);
			System.out.println("Playlist Name: "+playlist.getPlaylistName());
			System.out.println("Playlist Songs ");
			ArrayList<Song> songList=new ArrayList<Song>();
			ListIterator<Integer> listIterator = playlist.getSongIDs().listIterator();
	        while (listIterator.hasNext()) {
	            int songID=listIterator.next();
	            Song song=this.songList.get(songID);
	            songList.add(song);
	        }
	        this.printArrayList(songList);
		}
		else{
			System.out.println("Oops!! Playlist with given playlist ID does not exist in the Data Store!!");
		}
	}
	
	// Method to print the list of songs
	public void printArrayList(ArrayList<Song> list){
		System.out.println("SongID\tSong Artist \t\t\t\tSong Title");
		System.out.println("-------------------------------------------------------------------------");
		for(Song s:list){
			System.out.format("%4d   %-40s%-40s",s.getSongId(),s.getSongArtist(),s.getSongTitle());
			System.out.println();
		}
	}	
	
	// Method to check for valid song id
	public boolean isSongIdAvailable(int songID){
		boolean returnValue=false;
		if(songID<this.songID)
			returnValue=true;
		return returnValue;
	}
	
	// Method to check for the valid playlist id
	public boolean isPlaylistIdAvailable(int playlistID){
		boolean returnValue=false;
		if(playlistID<this.playlistID)
			returnValue=true;
		return returnValue;
	}
}
