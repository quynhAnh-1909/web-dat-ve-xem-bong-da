package model;

import java.util.List;

public class Ticket {
	private Match match;
    private String type;
    private int quantity;
    private List<String> seats;
    private int totalPrice;
	public Ticket(Match match, String type, int quantity, List<String> seats, int totalPrice) {
		super();
		this.match = match;
		this.type = type;
		this.quantity = quantity;
		this.seats = seats;
		this.totalPrice = totalPrice;
	}

}
