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

}
