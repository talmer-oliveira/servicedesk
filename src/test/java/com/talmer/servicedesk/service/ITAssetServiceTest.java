package com.talmer.servicedesk.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Optional;

import com.talmer.servicedesk.domain.ITAsset;
import com.talmer.servicedesk.domain.enums.AssetType;
import com.talmer.servicedesk.dto.ITAssetDTO;
import com.talmer.servicedesk.repository.ITAssetRepository;
import com.talmer.servicedesk.service.exception.ITAssetAlreadyRegisteredException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ITAssetServiceTest {
    
    @Mock
    private ITAssetRepository assetRepository;

    @InjectMocks
    private ITAssetService assetService;

    @Test
	public void whenITAssetIsInformedThenItShouldBeCreated() throws ITAssetAlreadyRegisteredException{
        ITAssetDTO expectAssetDTO = new ITAssetDTO("COMPUTER-01", 1);
        ITAsset expectedSavedAsset = new ITAsset("COMPUTER-01", AssetType.HARDWARE);
        
        when(assetRepository.findByTag(expectAssetDTO.getTag())).thenReturn(Optional.empty());
		when(assetRepository.save(any(ITAsset.class))).thenReturn(expectedSavedAsset);

        ITAsset savedAsset = assetService.createAsset(expectAssetDTO);

        assertThat(savedAsset.getTag(), is(equalTo(expectedSavedAsset.getTag())));
		assertThat(savedAsset.getType(), is(equalTo(expectedSavedAsset.getType())));
    }

    @Test
	public void whenAlreadyRegisteredItAssetIsInformedThenAnExceptionShouldBeThrown(){
		ITAssetDTO expectAssetDTO = new ITAssetDTO("COMPUTER-01", 1);
        ITAsset duplicatedSavedAsset = new ITAsset("COMPUTER-01", AssetType.HARDWARE);
		
		when(assetRepository.findByTag(expectAssetDTO.getTag())).thenReturn(Optional.of(duplicatedSavedAsset));
		
		Assertions.assertThrows(ITAssetAlreadyRegisteredException.class, () -> assetService.createAsset(expectAssetDTO));
	}
}
