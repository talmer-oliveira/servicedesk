package com.talmer.servicedesk.domain;

import java.io.Serializable;

import com.talmer.servicedesk.domain.enums.AssetType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="asset")
public class ITAsset implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

    private String tag;

    private Integer type;

    public ITAsset() {}

    public ITAsset(String tag, AssetType type) {
        this.tag = tag;
        setType(type);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public AssetType getType() {
        return AssetType.toEnum(this.type);
    }

    public void setType(AssetType type) {
        this.type = type.getCode();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
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
		ITAsset other = (ITAsset) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

}
