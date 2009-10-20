package br.com.caelum.rest.server;


public class Order implements Restfull{
	
	private final Long id;
	
	private final String content;
	
	private Status status = Status.WAITING_FOR_PAYMENT;
	
	public Order(Long id, String content) {
		this.id = id;
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public Long getId() {
		return id;
	}

	public void cancel() {
		status = Status.CANCELED;
	}

}
