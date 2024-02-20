package com.cyberguardians.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cyberguardians.entity.Javas_Url;

@Repository
public interface UrlRepository extends JpaRepository<Javas_Url, String> {
	
	public Javas_Url findByUrl(String url);
	
	@Query(value = "insert into Url values (0, :result, :url)", nativeQuery = true)
	public void insertUrl(String url, int result);

	default Javas_Url saveAndNormalize(Javas_Url entity) {
		entity.normalizeUrl();
		return save(entity);
	}
	
}
