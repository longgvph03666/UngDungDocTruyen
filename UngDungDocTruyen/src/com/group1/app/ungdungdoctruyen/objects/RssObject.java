package com.group1.app.ungdungdoctruyen.objects;


public class RssObject {
	private String title, link, date, images;
	public RssObject(String title, String link, String date, String images){
		this.title = title;
		this.link = link;
		this.date = date;
		this.images = images;
	}
	
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
