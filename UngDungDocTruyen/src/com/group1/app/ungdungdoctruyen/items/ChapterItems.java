package com.group1.app.ungdungdoctruyen.items;

public class ChapterItems {
    private String Chapter;
    private String urlOnline;
    private String urlDown;
    
    public ChapterItems(){};
    
	public ChapterItems(String chapter, String urlOnline, String urlDown) {
		Chapter = chapter;
		this.urlOnline = urlOnline;
		this.urlDown = urlDown;
	}

	public String getUrlOnline() {
		return urlOnline;
	}

	public void setUrlOnline(String urlOnline) {
		this.urlOnline = urlOnline;
	}

	public String getUrlDown() {
		return urlDown;
	}

	public void setUrlDown(String urlDown) {
		this.urlDown = urlDown;
	}

	public String getChapter() {
		return Chapter;
	}

	public void setChapter(String chapter) {
		Chapter = chapter;
	}
    
}
