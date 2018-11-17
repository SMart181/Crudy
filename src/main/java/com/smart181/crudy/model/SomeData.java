package com.smart181.crudy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SomeData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String someText;
	private int someInteger;
	private double someDouble;
	private boolean someBoolean;
	
	//@JsonFormat(pattern="yyyy-MM-dd")
	private Date someDate;
	
	// TODO NEXT
	//@OneToOne
	//private SomeData someData;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSomeText() {
		return someText;
	}
	
	public void setSomeText(String someText) {
		this.someText = someText;
	}
	
	public int getSomeInteger() {
		return someInteger;
	}
	
	public void setSomeInteger(int someInteger) {
		this.someInteger = someInteger;
	}
	
	public double getSomeDouble() {
		return someDouble;
	}
	
	public void setSomeDouble(double someDouble) {
		this.someDouble = someDouble;
	}
	
	public boolean isSomeBoolean() {
		return someBoolean;
	}
	
	public void setSomeBoolean(boolean someBoolean) {
		this.someBoolean = someBoolean;
	}
	
	public Date getSomeDate() {
		return someDate;
	}
	
	public void setSomeDate(Date someDate) {
		this.someDate = someDate;
	}
	
	/*public SomeData getSomeData() {
		return someData;
	}
	
	public void setSomeData(SomeData someData) {
		this.someData = someData;
	}*/
}
