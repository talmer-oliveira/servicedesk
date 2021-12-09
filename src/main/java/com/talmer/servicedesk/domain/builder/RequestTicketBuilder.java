package com.talmer.servicedesk.domain.builder;

import java.util.Date;

import com.talmer.servicedesk.domain.RequestTicket;
import com.talmer.servicedesk.domain.ServiceCategory;
import com.talmer.servicedesk.dto.AuthorDTO;

public class RequestTicketBuilder {

	private RequestTicket requestTicket;

	public RequestTicketBuilder() {
		requestTicket = new RequestTicket();
	}
	
	public RequestTicketBuilder title(String title) {
		requestTicket.setTitle(title);
		return this;
	}
	
	public RequestTicketBuilder description(String description) {
		requestTicket.setDescription(description);
		return this;
	}
	
	public RequestTicketBuilder timestamp(Date timestamp) {
		requestTicket.setTimestamp(timestamp);
		return this;
	}
	
	public RequestTicketBuilder status(Integer status) {
		requestTicket.setStatus(status);
		return this;
	}
	
	public RequestTicketBuilder serviceCategory(ServiceCategory serviceCategory) {
		requestTicket.setServiceCategory(serviceCategory);
		return this;
	}
	
	public RequestTicketBuilder requester(AuthorDTO requester) {
		requestTicket.setRequester(requester);
		return this;
	}
}
