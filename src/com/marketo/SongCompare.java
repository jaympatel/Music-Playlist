package com.marketo;

import java.util.Comparator;

public class SongCompare implements Comparator<Song> {

		String songAttribute;
	    public SongCompare(String songAttribute) {
	        this.songAttribute=songAttribute;
	    }
	    
	    public int compare(Song a, Song b) {
	    	
	    	int returnValue=0;
	    	if(this.songAttribute.equals("artist"))
	    		returnValue=a.getSongArtist().compareTo(b.getSongArtist());
	    	if(this.songAttribute.equals("title"))
	    		returnValue=a.getSongTitle().compareTo(b.getSongTitle());
	    	return returnValue;
	    }


	}

