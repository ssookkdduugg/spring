package com.kosta.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.kosta.api.dto.AnimalClinic;
import com.kosta.api.dto.PageInfo;

@Service
public class SeoulApiService { // cf. 인터페이스와 구현클래스를 만드는 것이 권장되나 이 프로젝트는 간단히 클래스만 만들었다
	
	public List<AnimalClinic> animalClinicList(PageInfo pageInfo) throws Exception {
		
		int itemsPerPage = 10;
		int pagesPerGroup = 10;
		int startIdx = (pageInfo.getCurPage()-1)*itemsPerPage+1;
		System.out.println("startIdx:" + startIdx);
		
		// 1. api요청을 위한 url 생성: 기본주소 + 인증키 + 데이터요청타입 + 서비스명 + 페이징시작번호 + 페이징끝번호
		//rest형식이지만 ElecChargeService는 ParameterType ?로 넘겨야함.
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
		urlBuilder.append("/" + URLEncoder.encode("695964515373656f37315752774d63", "UTF-8")); // URL을 구성할 한글 파라미터가 있다면 인코딩 필수
		urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode("LOCALDATA_020301", "UTF-8")); // 서비스명
		urlBuilder.append("/" + URLEncoder.encode(startIdx+"", "UTF-8")); // 요청시작위치(페이징 시작번호)
		urlBuilder.append("/" + URLEncoder.encode(startIdx+itemsPerPage+"", "UTF-8")); // 요청종료위치(페이징 끝번호)
		
		// 2. <요청> url 객체 생성 후 연결
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // HTTP GET요청을 만들기 위해 HttpURLConnection클래스 사용(요청메서드와 컨텐츠타입을 설정)
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		
		// 3. <응답> 입력스트림을 열어 응답데이터를 읽는다 (xml과 json은 문자열 데이터이므로 그에 맞는 스트림을 이용)
		BufferedReader br;
		int resultCode = conn.getResponseCode();
		
		if(200<=resultCode && resultCode<=300) { // 정상
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else { // 에러
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream())); // 에러일때는 에러스트림을 이용하여 읽는다
		}
		
		// 4. 응답데이터를 문자열로 읽어들인 뒤에는 StringBuilder를 이용하여 하나의 문자열로 만든다
		StringBuilder resBuilder = new StringBuilder();
		String line;
		while((line=br.readLine())!=null) {
			resBuilder.append(line);
		}
		br.close();
		conn.disconnect();
		System.out.println(resBuilder.toString());
		
		// 5. 가져온 json데이터를 파싱처리하기 위해 pom.xml에 JSON.simple » 1.1.1 라이브러리 추가함
		List<AnimalClinic> acList = new ArrayList<>();
		JSONParser parser = new JSONParser();
		JSONObject mobj = (JSONObject) parser.parse(resBuilder.toString());
		JSONObject LOCALDATA_020301 = (JSONObject) mobj.get("LOCALDATA_020301");
		Long list_total_count = (Long) LOCALDATA_020301.get("list_total_count");
		JSONArray row = (JSONArray) LOCALDATA_020301.get("row");
		
		// 6. 객체배열row에서 각 행마다 정보를 추출하고 그 정보로 AnimalClinic객체를 생성하여 리스트에 추가
		for(int i=0; i<row.size(); i++) {
			JSONObject acJson = (JSONObject) row.get(i);
			
			String trdStateNm = (String) acJson.get("TRDSTATENM");
			String siteTel = (String) acJson.get("SITETEL");
			String rdnwhlAddr = (String) acJson.get("RDNWHLADDR");
			String bplcNm = (String) acJson.get("BPLCNM");
			String x = (String) acJson.get("X");
			String y = (String) acJson.get("Y");
			
			acList.add(new AnimalClinic(trdStateNm,siteTel,rdnwhlAddr,bplcNm,x,y));
		}
		
		// 7. 페이지정보 계산
		int allPage = (int)Math.ceil(list_total_count.doubleValue()/itemsPerPage);
		int startPage = (pageInfo.getCurPage()-1)/pagesPerGroup*pagesPerGroup+1;
	    int endPage = Math.min(startPage+10-1,allPage);
	    pageInfo.setAllPage(allPage);
	    pageInfo.setStartPage(startPage);
	    pageInfo.setEndPage(endPage);
	    if(pageInfo.getCurPage()>allPage) pageInfo.setCurPage(allPage); // 게시글 삭제시 예외처리
		
		return acList;
		
		
/*
5. 요청URL로 브라우저에 요청시의 데이터 형식을 참고
{
  "LOCALDATA_020301": {
    "list_total_count": 2096,
    "RESULT": {...},
    "row": [...]
  }
}
* */
	}
	
}
