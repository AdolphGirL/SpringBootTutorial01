package com.reyes.tutorial.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 綁定讀取Properties資料
 * ConfigurationProperties，綁定properties資料；
 * 其需要在容器中才會有效，因此需要讓spring boot啟動後掃描到
 * 所以加上Component
 * 
 * prefix = "cpbean" 需要讀取yml檔內的前墜詞，不可大寫
 * 
 * - 支持JSR303數據校驗
 * - 支持複雜類型
 * 
 */

@Component
@ConfigurationProperties(prefix = "propertiesbean")
@Validated
public class PropertiesBean {
	
	private String name;
	private int age;
	private float weight;
	private boolean isP;
	private Date bir;
	private String email;
	
	private Map<String, String> maps;
	private List<String> lists;
	private PropertiesDtlBean dtl;
	
	public PropertiesBean(){
		
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
		return "PropertiesBean [name=" + name + ", age=" + age + ", weight=" + weight + ", isP=" + isP + ", bir=" + bir
				+ ", email=" + email + ", maps=" + maps + ", lists=" + lists + ", dtl=" + dtl + "]";
	}

	

}
