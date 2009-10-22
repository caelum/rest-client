package br.com.caelum.rest.server;

import java.util.Calendar;
import java.util.List;

public class Order implements Restful {

	final Long id;

	private final String content;
	
	private Calendar payMoment;

	Status status = Status.WAITING_FOR_PAYMENT;

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

	public List<Action> getActions(ActionBuilder builder) {
		builder.use(OrderController.class).view(id);
		if(status.equals(Status.WAITING_FOR_PAYMENT)) {
			builder.use(OrderController.class).pay(id);
			builder.use(OrderController.class).cancel(id);
			builder.use(OrderController.class).updateOrder(id, "");
			// actions.add(new SimpleAction("cancel", "http://localhost:8080/order/" + id));
			//builder.to("cancel").use(OrderController.class).cancel(id);
			// builder.allFor(OrderController.class);
		}
		return builder.actions();
	}
	
	
	public String toXml(Linker linker) {
		return "<order>\n" + "<id>" + id + "</id>\n" + "<status>" + getRealStatus() + "</status>\n" + linker.to(this) + "\n</order>";
	}

	private String getRealStatus() {
		Calendar earlier = Calendar.getInstance();
		earlier.add(Calendar.MINUTE, -5);
		if(status==Status.PROCESSING && payMoment.before(earlier)) {
			status = Status.COMPLETED;
		}
		return status.name();
	}

	public void pay() {
		status = Status.PROCESSING;
		payMoment = Calendar.getInstance();
	}

}
