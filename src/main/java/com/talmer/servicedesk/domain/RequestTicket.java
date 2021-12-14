package com.talmer.servicedesk.domain;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.talmer.servicedesk.domain.enums.Priority;
import com.talmer.servicedesk.domain.enums.Status;
import com.talmer.servicedesk.dto.AuthorDTO;
import com.talmer.servicedesk.dto.ITAssetDTO;
import com.talmer.servicedesk.dto.ServiceCategoryDTO;
import com.talmer.servicedesk.dto.UpdateCommentDTO;

@Document(collection = "ticket")
public class RequestTicket {

	@Id
	private String id;
	
	private String title;
	
	private String description;
	
	private Date timestamp;
	
	private Priority priority;
	
	private Status status;
	
	private ServiceCategoryDTO serviceCategoryDTO;
	
	private ITAssetDTO targetAssetDTO;
	
	private AuthorDTO requester;
	
	private AuthorDTO attendant;
	
	private Set<UpdateCommentDTO> updates;
	
	public RequestTicket() {}

	public RequestTicket(String title, String description, Date timestamp, Status status,
			ServiceCategoryDTO serviceCategoryDTO, AuthorDTO requester) {
		this.title = title;
		this.description = description;
		this.timestamp = timestamp;
		this.status = status;
		this.serviceCategoryDTO = serviceCategoryDTO;
		this.requester = requester;
	}

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

	public ServiceCategoryDTO getServiceCategoryDTO() {
		return serviceCategoryDTO;
	}

	public void setServiceCategoryDTO(ServiceCategoryDTO serviceCategoryDTO) {
		this.serviceCategoryDTO = serviceCategoryDTO;
	}

	public ITAssetDTO getTargetAssetDTO() {
		return targetAssetDTO;
	}

	public void setTargetAssetDTO(ITAssetDTO targetAssetDTO) {
		this.targetAssetDTO = targetAssetDTO;
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
