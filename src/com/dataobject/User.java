package com.dataobject;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 
 * @author suresh chaudhari
 *
 */

@Document(collection="user")
public class User {


	@Id
	private ObjectId id;
	
	@Field("login_name")
	private String loginName;

	
	@Field("create_date")
	private Long createDate;
	
	@Field("first_name")
	private String firstName;
	
	@Field("last_name")
	private String lastName;

	private Address address;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public User() {

	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginName=" + loginName + ", createDate=" + createDate + ", firstName="
				+ firstName + ", lastName=" + lastName + ", address=" + address + "]";
	}
	
}