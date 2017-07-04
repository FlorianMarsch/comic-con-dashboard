package vo;

public class Event {
	private String price_range;
	private String url;
	private Logo logo;
	private Ticket ticket_availability;
	private Date start;
	private Date end;
	private Name name;
	private Venue venue;
	
	public String getPrice_range() {
		return price_range;
	}
	public void setPrice_range(String price_range) {
		this.price_range = price_range;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Logo getLogo() {
		return logo;
	}
	public void setLogo(Logo logo) {
		this.logo = logo;
	}
	public Ticket getTicket_availability() {
		return ticket_availability;
	}
	public void setTicket_availability(Ticket ticket_availability) {
		this.ticket_availability = ticket_availability;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public Venue getVenue() {
		return venue;
	}
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	
	
}
