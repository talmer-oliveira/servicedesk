package com.talmer.servicedesk.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class RequestTicketDTO {

    @JsonProperty(access = Access.READ_ONLY)
	private String id;
    
    @NotNull(message = "Não pode ser nulo")
    @Size(min = 3, max = 100, message = "No mínimo 3 e no máximo 100 caracteres")
    private String title;

    @NotNull(message = "Não pode ser nulo")
    @Size(min = 3, max = 600, message = "No mínimo 3 e no máximo 600 caracteres")
    private String descryption;

    @NotNull(message = "Não pode ser nulo")
    private String serviceCategoryName;

    private String ITAssetTag;

    public RequestTicketDTO() {}

    public RequestTicketDTO(String title, String descryption, String serviceCategoryName, String iTAssetTag) {
        this.title = title;
        this.descryption = descryption;
        this.serviceCategoryName = serviceCategoryName;
        ITAssetTag = iTAssetTag;
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

    public String getDescryption() {
        return descryption;
    }

    public void setDescryption(String descryption) {
        this.descryption = descryption;
    }

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    public String getITAssetTag() {
        return ITAssetTag;
    }

    public void setITAssetTag(String iTAssetTag) {
        ITAssetTag = iTAssetTag;
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
        RequestTicketDTO other = (RequestTicketDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
