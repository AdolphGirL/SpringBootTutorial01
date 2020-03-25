package com.reyes.tutorial.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 讀取非全局配置(不在application.yml中)的資料
 * - @PropertySource(value = { "" })，value是數組格式，可以加載多個
 *
 */
@Component
@ConfigurationProperties(prefix = "propertysourcebean")
@PropertySource(value = { "classpath:sources-bean.yml" })
public class PropertySourceBean {
	
	private String name;
	private int age;
	private float weight;
	private boolean isP;
	private Date bir;
	private String email;
	
	private Map<String, String> maps;
	private List<String> lists;
	private PropertiesDtlBean dtl;
	
	public PropertySourceBean(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public boolean isP() {
		return isP;
	}

	public void setP(boolean isP) {
		this.isP = isP;
	}

	public Date getBir() {
		return bir;
	}

	public void setBir(Date bir) {
		this.bir = bir;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, String> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, String> maps) {
		this.maps = maps;
	}

	public List<String> getLists() {
		return lists;
	}

	public void setLists(List<String> lists) {
		this.lists = lists;
	}

	public PropertiesDtlBean getDtl() {
		return dtl;
	}

	public void setDtl(PropertiesDtlBean dtl) {
		this.dtl = dtl;
	}

	@Override
	public String toString() {
		return "PropertySourceBean [name=" + name + ", age=" + age + ", weight=" + weight + ", isP=" + isP + ", bir="
				+ bir + ", email=" + email + ", maps=" + maps + ", lists=" + lists + ", dtl=" + dtl + "]";
	}

	
	
}
