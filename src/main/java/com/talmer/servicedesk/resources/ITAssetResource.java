package com.talmer.servicedesk.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.talmer.servicedesk.domain.ITAsset;
import com.talmer.servicedesk.dto.ITAssetDTO;
import com.talmer.servicedesk.service.ITAssetService;
import com.talmer.servicedesk.service.exception.ITAssetAlreadyRegisteredException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value="/ativos")
public class ITAssetResource {
    
    private ITAssetService assetService;

    @RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','DEV')")
	public ResponseEntity<String> createAsset(@Valid @RequestBody ITAssetDTO assetDTO){
		try {
			ITAsset asset = assetService.createAsset(assetDTO);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(asset.getId()).toUri();
			return ResponseEntity.created(uri).build();
		} catch (ITAssetAlreadyRegisteredException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}

    @RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ITAssetDTO>> findAll(){
		List<ITAssetDTO> assets = assetService.findAll();
		return ResponseEntity.ok().body(assets);
	}
}
