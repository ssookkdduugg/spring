package com.kosta.api.dto;

public class UserInfo {
	private String id;
	private String nickname;
	private String profileImage;
	private String thumbnailImage;
	private String email;
	private String gender;
	
	private String name;
	private String mobile;
	private String address;
	
	private String age;
	private String birthday;
	private String password;
	
	
	
	public UserInfo() {} 
	
	
	public UserInfo(String id, String nickname, String profileImage, String thumbnailImage, String email,
			String gender) {
		
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.thumbnailImage = thumbnailImage;
		this.email = email;
		this.gender = gender;
	}
	
	
	
	//이미지 필드 2 개 없애주기 
	public UserInfo(String id, String nickname,  String email, String gender,
			String name, String mobile, String age, String birthday) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.thumbnailImage = thumbnailImage;
		this.email = email;
		this.gender = gender;
		this.name = name;
		this.mobile = mobile;
		this.age = age;
		this.birthday = birthday;
	}

	



	




	public UserInfo(String id, String nickname, String profileImage, String thumbnailImage, String email, String name,
			String mobile, String address, String password) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.thumbnailImage = thumbnailImage;
		this.email = email;
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.password = password;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getThumbnailImage() {
		return thumbnailImage;
	}
	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
