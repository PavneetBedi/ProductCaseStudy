package com.amdocs.CaseStudy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
	
	@Id
	private long pid;
	private long price;
	private String pname;
	
	public Product() {
		super();
	}

	public Product(long pid, long price, String pname) {
		super();
		this.pid = pid;
		this.price = price;
		this.pname = pname;
	}



	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
}
