package com.kosta.api.dto;


public class ElecCharge {
	private Long csId;
	private String csNm;
	private String addr;
	private String lat;
	private String longi; 
	private Long cpId;
	private String cpNm;
	private String chargeTp;
	private String cpTp;
	private String statUpdatetime;
	private String cpStat;
	
	
	
	
	public ElecCharge(Long csId, String csNm, String addr, String lat, String longi, Long cpId, String cpNm,
			String chargeTp, String cpTp, String statUpdatetime, String cpStat) {
		this.csId = csId;
		this.csNm = csNm;
		this.addr = addr;
		this.lat = lat;
		this.longi = longi;
		this.cpId = cpId;
		this.cpNm = cpNm;
		this.chargeTp = chargeTp;
		this.cpTp = cpTp;
		this.statUpdatetime = statUpdatetime;
		this.cpStat = cpStat;
	}
	
	
	public Long getCsId() {
		return csId;
	}
	public void setCsId(Long csId) {
		this.csId = csId;
	}
	public String getCsNm() {
		return csNm;
	}
	public void setCsNm(String csNm) {
		this.csNm = csNm;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getLongi() {
		return longi;
	}
	public void setLongi(String longi) {
		this.longi = longi;
	}
	public Long getCpId() {
		return cpId;
	}
	public void setCpId(Long cpId) {
		this.cpId = cpId;
	}
	public String getCpNm() {
		return cpNm;
	}
	public void setCpNm(String cpNm) {
		this.cpNm = cpNm;
	}
	public String getChargeTp() {
		return chargeTp;
	}
	public void setChargeTp(String chargeTp) {
		this.chargeTp = chargeTp;
	}
	public String getCpTp() {
		return cpTp;
	}
	public void setCpTp(String cpTp) {
		this.cpTp = cpTp;
	}
	public String getStatUpdatetime() {
		return statUpdatetime;
	}
	public void setStatUpdatetime(String statUpdatetime) {
		this.statUpdatetime = statUpdatetime;
	}
	public String getCpStat() {
		return cpStat;
	}
	public void setCpStat(String cpStat) {
		this.cpStat = cpStat;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	
	
	
	

}
