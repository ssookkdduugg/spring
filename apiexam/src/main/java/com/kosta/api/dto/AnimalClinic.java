package com.kosta.api.dto;

public class AnimalClinic {
	// 서울시열린데이터광장의 오픈데이터api에 대한 매개체인 DTO
	
	// 필드
	private String trdStateNm; // 영업상태명
	private String siteTel; // 전화번호
	private String rdnwhlAddr; // 도로명주소
	private String bplcNm; // 사업장명
	private String x; // 좌표정보(x)
	private String y; // 좌표정보(y)
	// cf. 테이블에 그냥 보여주기만 할 것이므로 필드를 String으로 선언한것 (제공된 JSON형식 데이터는 일단 문자열이기도한데, x/y 좌표를 굳이 실수타입로 하지 않은 이유)

	
	// 생성자
	public AnimalClinic(String trdStateNm, String siteTel, String rdnwhlAddr, String bplcNm, String x, String y) {
		this.trdStateNm = trdStateNm;
		this.siteTel = siteTel;
		this.rdnwhlAddr = rdnwhlAddr;
		this.bplcNm = bplcNm;
		this.x = x;
		this.y = y;
	}
	
	// getter, setter
	public String getTrdStateNm() {
		return trdStateNm;
	}
	public void setTrdStateNm(String trdStateNm) {
		this.trdStateNm = trdStateNm;
	}
	public String getSiteTel() {
		return siteTel;
	}
	public void setSiteTel(String siteTel) {
		this.siteTel = siteTel;
	}
	public String getRdnwhlAddr() {
		return rdnwhlAddr;
	}
	public void setRdnwhlAddr(String rdnwhlAddr) {
		this.rdnwhlAddr = rdnwhlAddr;
	}
	public String getBplcNm() {
		return bplcNm;
	}
	public void setBplcNm(String bplcNm) {
		this.bplcNm = bplcNm;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	
}
