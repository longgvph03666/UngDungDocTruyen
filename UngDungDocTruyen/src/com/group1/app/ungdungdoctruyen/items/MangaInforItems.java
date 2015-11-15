package com.group1.app.ungdungdoctruyen.items;

public class MangaInforItems {
       String author;
       String mangaName;
       String summary;
       String genre;
       String url;
       String chapter;
       public MangaInforItems(){}
	public MangaInforItems(String author, String mangaName, String summary,
			String genre, String url, String chapter) {
		
		this.author = author;
		this.mangaName = mangaName;
		this.summary = summary;
		this.genre = genre;
		this.url = url;
		this.chapter = chapter;
	}
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getMangaName() {
		return mangaName;
	}
	public void setMangaName(String mangaName) {
		this.mangaName = mangaName;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
       
       
       
}
