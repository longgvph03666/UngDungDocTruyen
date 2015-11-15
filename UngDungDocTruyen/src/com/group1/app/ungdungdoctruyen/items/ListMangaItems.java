package com.group1.app.ungdungdoctruyen.items;

public class ListMangaItems {
	private String headline;
	private String reporterName;
	private String date;
	private String url;
    private String author;
    private int position;
    
    public ListMangaItems(){
    	
    }
    
    
	public ListMangaItems(String headline, String reporterName,
			String url, String author, int position) {
		this.headline = headline;
		this.reporterName = reporterName;	
		this.url = url;
		this.author = author;
		this.position = position;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "[ headline=" + headline + ", reporter Name=" + reporterName
				+ " , date=" + date + "]";
	}
}
