package com.kosta.api.dto;

public class UserInfo {
	private Long id;
	private String nickname;
	private String profileImage;
	private String thumbnailImage;
	private String email;
	private String gender;
	
	

	
	public UserInfo(Long id, String nickname, String profileImage, String thumbnailImage, String email,
			String gender) {
		
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.thumbnailImage = thumbnailImage;
		this.email = email;
		this.gender = gender;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	
	

}
