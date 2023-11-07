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

import com.kosta.api.dto.ElecCharge;
import com.kosta.api.dto.PageInfo;

@Service
public class ElecChargeService {

   public List<ElecCharge> elecChargeList(PageInfo pageInfo) throws Exception {
      
      int itemsPerPage = 10;
      int pagesPerGroup = 10;
      int startIdx = (pageInfo.getCurPage()-1)*itemsPerPage+1;
      System.out.println("startIdx:" + startIdx);
            
      // 1. api요청을 위한 url 문자열 생성
      // https://api.odcloud.kr/api/EvInfoServiceV2/v1/getEvSearchList?page=1&perPage=10&serviceKey=(인증키) 
      StringBuilder urlBuilder = new StringBuilder("https://api.odcloud.kr/api/EvInfoServiceV2/v1/getEvSearchList");
      urlBuilder.append("?page=" + URLEncoder.encode(pageInfo.getCurPage()+"", "UTF-8")); // 요청시작위치(페이징 시작번호)
      urlBuilder.append("&perPage=" + URLEncoder.encode(itemsPerPage+"", "UTF-8")); // 페이지당 아이템수
      urlBuilder.append("&returnType=" + URLEncoder.encode("JSON", "UTF-8"));
      // 서비스 인증키
      // urlBuilder.append("&serviceKey=" + URLEncoder.encode("YtFsREHPezgJEYK0w8OrwvRagwtVyibxcU0NfHCCPjMb2fZZY9ls7%2Bv5WcDozJjn5VxDcUJQhyr7qOPqrv1Eww%3D%3D", "UTF-8")); // (1) 
      // urlBuilder.append("&serviceKey=" + "YtFsREHPezgJEYK0w8OrwvRagwtVyibxcU0NfHCCPjMb2fZZY9ls7%2Bv5WcDozJjn5VxDcUJQhyr7qOPqrv1Eww%3D%3D"); // (2)
      urlBuilder.append("&serviceKey=" + URLEncoder.encode("TMHJgLxLzu8SDdK7TG0D/Zfmx29vp5/bkhCKIa3DjAgTwbS0goztU+ZREkShMzLGMJT2dddsXPg3knCfhtHEtQ==", "UTF-8")); // (3) 인코더 안에 디코더키 넣으면 정상적으로 작동 
      
      
      
      System.out.println("생성된 url문자열: " + urlBuilder);
      
      // 2. <요청> url 객체 생성 후 연결
      URL url = new URL(urlBuilder.toString());
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
      
      // 5. 가져온 json데이터를 파싱처리
      List<ElecCharge> elecChargeList = new ArrayList<ElecCharge>();
      JSONParser parser = new JSONParser();
      JSONObject mobj = (JSONObject) parser.parse(resBuilder.toString());
      Long totalCount = (Long) mobj.get("totalCount");
      JSONArray data = (JSONArray) mobj.get("data");
      
      // 6. 객체배열data에서 각 행마다 정보를 추출하고 그 정보로 ElecCharge객체를 생성하여 리스트에 추가
      for(int i=0; i<data.size(); i++) {
         JSONObject elecChargeJson = (JSONObject) data.get(i);
         
         Long csId = (Long) elecChargeJson.get("csId"); // 충전기ID
         String csNm = (String) elecChargeJson.get("csNm"); // 충전소명칭
         String addr = (String) elecChargeJson.get("addr"); // 충전소주소
         String lat = (String) elecChargeJson.get("lat"); // 위도
         String longi = (String) elecChargeJson.get("longi"); // 경도
         Long cpId = (Long) elecChargeJson.get("cpId"); // 충전기명칭
         String cpNm = (String) elecChargeJson.get("cpNm"); // 충전기명칭
         String chargeTp = (String) elecChargeJson.get("chargeTp"); // 충전기타입
         String cpTp = (String) elecChargeJson.get("cpTp"); // 충전방식
         String statUpdatetime = (String) elecChargeJson.get("statUpdatetime");
         String cpStat = (String) elecChargeJson.get("cpStat"); // 충전기상태
         
         elecChargeList.add(new ElecCharge(csId, csNm, addr, lat, longi, cpId, cpNm, chargeTp, cpTp, statUpdatetime, cpStat));
      }
      
      // 7. 페이지정보 계산
      int allPage = (int)Math.ceil(totalCount.doubleValue()/itemsPerPage);
      int startPage = (pageInfo.getCurPage()-1)/pagesPerGroup*pagesPerGroup+1;
       int endPage = Math.min(startPage+pagesPerGroup-1,allPage);
       pageInfo.setAllPage(allPage);
       pageInfo.setStartPage(startPage);
       pageInfo.setEndPage(endPage);
       if(pageInfo.getCurPage()>allPage) pageInfo.setCurPage(allPage); // 게시글 삭제시 예외처리
      
      return elecChargeList;
      
      
/*
요청URL로 브라우저에 요청시의 데이터 형식을 참고
{
  "currentCount": 10,
  "data": [
    {
      "addr": "경상북도 김천시 어모면 중왕리 551 어모면사무소",
      "chargeTp": "2",
      "cpId": 10611,
      "cpNm": "급속02",
      "cpStat": "1",
      "cpTp": "7",
      "csId": 4437,
      "csNm": "어모면사무소",
      "lat": "36.1805740877666",
      "longi": "128.119034422461",
      "statUpdatetime": "2022-06-08"
    },
    ....
  ],
  "matchCount": 3106,
  "page": 1,
  "perPage": 10,
  "totalCount": 3106
}
*/
      
   }
}