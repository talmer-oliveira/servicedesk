package com.talmer.servicedesk.domain;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.talmer.servicedesk.domain.enums.Priority;
import com.talmer.servicedesk.domain.enums.Status;
import com.talmer.servicedesk.dto.AuthorDTO;
import com.talmer.servicedesk.dto.UpdateCommentDTO;

@Document
public class RequestTicket {

	@Id
	private String id;
	
	private String title;
	
	private String description;
	
	private Date timestamp;
	
	private Priority priority;
	
	private Status status;
	
	private ServiceCategory serviceCategory;
	
	private ITAsset targetAsset;
	
	private AuthorDTO requester;
	
	private AuthorDTO attendant;
	
	private Set<UpdateCommentDTO> updates;
	
	public RequestTicket() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = Priority.toEnum(priority);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = Status.toEnum(status);
	}

	public ServiceCategory getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	public ITAsset getTargetAsset() {
		return targetAsset;
	}

	public void setTargetAsset(ITAsset targetAsset) {
		this.targetAsset = targetAsset;
	}

	public AuthorDTO getRequester() {
		return requester;
	}

	public void setRequester(AuthorDTO requester) {
		this.requester = requester;
	}

	public AuthorDTO getAttendant() {
		return attendant;
	}

	public void setAttendant(AuthorDTO attendant) {
		this.attendant = attendant;
	}

	public Set<UpdateCommentDTO> getUpdates() {
		return updates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestTicket other = (RequestTicket) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
