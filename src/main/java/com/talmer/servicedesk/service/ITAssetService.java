package com.talmer.servicedesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.talmer.servicedesk.domain.ITAsset;
import com.talmer.servicedesk.domain.enums.AssetType;
import com.talmer.servicedesk.dto.ITAssetDTO;
import com.talmer.servicedesk.repository.ITAssetRepository;
import com.talmer.servicedesk.service.exception.ITAssetAlreadyRegisteredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITAssetService {
    
    @Autowired
    private ITAssetRepository assetRepository;

    public ITAsset createAsset(ITAssetDTO assetDTO) throws ITAssetAlreadyRegisteredException{
        verifyIfExists(assetDTO.getTag());
        ITAsset asset = fromDTO(assetDTO);
        return assetRepository.save(asset);
    }

    public ITAssetDTO findByTag(String tag){
        Optional<ITAsset> optional = assetRepository.findByTag(tag);
        if(optional.isPresent()){
            ITAsset asset = optional.get();
            return new ITAssetDTO(asset.getTag(), asset.getType().getCode());
        }
        else{
            throw new ITAssetNotFoundException("Ativo de TI não encontrado: " + tag);
        }
    }

    public List<ITAssetDTO> findAll(){
		return assetRepository.findAll()
						.stream()
						.map(a -> new ITAssetDTO(a.getTag(), a.getType().getCode()))
						.collect(Collectors.toList());
	}

    private void verifyIfExists(String tag) throws ITAssetAlreadyRegisteredException{
        Optional<ITAsset> optional = assetRepository.findByTag(tag);
        if(optional.isPresent()){
            throw new ITAssetAlreadyRegisteredException(tag);
        }
    }

    ITAsset fromDTO(ITAssetDTO assetDTO){
        return new ITAsset(assetDTO.getTag(), AssetType.toEnum(assetDTO.getType()));
    }
}
