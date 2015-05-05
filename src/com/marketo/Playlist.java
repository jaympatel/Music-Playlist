package com.marketo;

import java.util.LinkedList;

public class Playlist {

	private int playlistID;
	private String playlistName;
	private LinkedList<Integer> songIDs;
	
	public LinkedList<Integer> getSongIDs() {
		return songIDs;
	}
	public void setSongIDs(LinkedList<Integer> songIDs) {
		this.songIDs = songIDs;
	}
	public int getPlaylistID() {
		return playlistID;
	}
	public void setPlaylistID(int playlistID) {
		this.playlistID = playlistID;
	}
	public String getPlaylistName() {
		return playlistName;
	}
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	
	// Method to delete the given song id from the playlist if exist
	void deleteSong(Integer songID){
		if(this.songIDs.contains(songID)){
			this.songIDs.remove(songID);
			System.out.println("Song removed from the playlist!!");
		}
		else{
			System.out.println("Oops!! Song is not on the playlist!");
		}
	}
	
	// Method to insert the given song id in the playlist if not already present
	void insertSong(Integer songID){
		if(this.songIDs.contains(songID)){
			System.out.println("Song already present in the Playlist!");
		}
		else{
			this.songIDs.add(songID);
			System.out.println("Song added to the playlist!!");
		}
	}
	
}
