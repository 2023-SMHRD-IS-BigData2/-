package com.cyberguardians.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cyberguardians.entity.Javas_Url;
import com.cyberguardians.repository.UrlRepository;

@Controller
public class UrlController {

	@Autowired
	private UrlRepository urlRepository;

	public Javas_Url selectUrl(String url) {
		url = normalizeUrl(url);
		Javas_Url select = urlRepository.findByUrl(url);
		return select;
	}

	public void insertUrl(String url, int result) {
		Javas_Url newUrl = new Javas_Url();
		newUrl.setUrl(url);
		newUrl.setUrl_result(result);
		urlRepository.save(newUrl);
	}
	
	private String normalizeUrl(String url) {
	    if (url != null) {
	        // URL을 표준 형식으로 변환하는 로직을 여기에 추가합니다.
	        // HTTPS:// 또는 HTTP:// 로 시작하는 경우를 함께 확인하고 처리합니다.
	        if (url.startsWith("https://")) {
	            url = "http://" + url.substring(8); // HTTPS:// 로 시작하는 경우 HTTP:// 로 변경합니다.
	        } else if (!url.startsWith("http://")) {
	            url = "http://" + url; // HTTP:// 또는 HTTPS:// 로 시작하지 않으면 앞에 추가합니다.
	        }
	        // URL의 끝 부분이 슬래시로 끝나지 않는 경우 슬래시를 추가합니다.
	        if (!url.endsWith("/")) {
	            url = url + "/"; // 슬래시로 끝나지 않으면 끝에 추가합니다.
	        }
	        // URL을 모두 소문자로 변환합니다.
	        url = url.toLowerCase();
	    }
	    return url;
	}
	
}
