package com.cyberguardians.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Javas_Url {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long url_index;
	
	@Column(length = 1000, nullable = false)
	private String url;
	
	@Column(nullable = false)
	private int url_result;
	
	public void normalizeUrl() {
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
	}

}
