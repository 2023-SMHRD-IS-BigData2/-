package com.cyberguardians.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberguardians.entity.Javas_Url;
import com.cyberguardians.repository.UrlRepository;

@Service
public class UrlService {
	
	@Autowired
	private UrlRepository urlRepository;
	
	public Javas_Url saveUrl(String url) {
		Javas_Url entity = new Javas_Url();
		entity.setUrl(url);
		return urlRepository.saveAndNormalize(entity);
	}
	
}
