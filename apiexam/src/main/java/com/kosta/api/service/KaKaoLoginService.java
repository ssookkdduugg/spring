package com.kosta.api.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.api.dao.UserDao;
import com.kosta.api.dto.UserInfo;

@Service
public class KaKaoLoginService {
   
   // 1. (로그인버튼->정보제공동의->)카카오서버로부터 인가코드를 받는다
   // 2. 인가코드를 통해 토큰을 받는다 (Bearer Token타입의 Access Token을 받음)
   // 3. 토큰을 통해 사용자 정보를 받는다
   // 4. 사용자 정보가 DB에 존재하면 로그인처리 / 존재하지 않으면 DB에 저장
	
	@Autowired
	private UserDao userDao;
   // 2+3.
   public UserInfo kakaoLogin(String code) throws Exception {
      String token = getAccessToken(code);
    //  System.out.println("token: " + token);
      UserInfo kakaoInfo = getUserInfo(token);
     UserInfo userInfo =    userDao.selectUser(kakaoInfo.getId());
     if(userInfo ==null) {
    	 userDao.insertUserByKakao(kakaoInfo);
    	 userInfo = kakaoInfo;
     }
      return userInfo;
   }
   
   // 2. postman에서 했던거 java코드로 , 카카오로 부터 token받는거 
   public String getAccessToken(String code) throws Exception {
      
      StringBuilder urlBuilder = new StringBuilder("https://kauth.kakao.com/oauth/token");
      //이 주소는 카카오로그인 ->rest api -> 토큰 받기에있음 
      
      URL url = new URL(urlBuilder.toString());
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      // 요청 헤더
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
      conn.setDoOutput(true); // 출력스트림 활성화(POST요청이므로 파라미터를 요청 바디에 넣기 위해)
      
      // 파라미터 생성 (출력스트림을 이용하여 요청 바디에 세팅
      // GET방식 요청이 아니라 POST방식 요청이므로 파라미터를 URL문자열(urlBuilder)에 append하지 않고 요청 바디에 세팅한다
      // cf. 카카오디벨로퍼스>문서>REST API 참고하여 액세스토큰방식 요청의 파라미터들을 설정
      //token을 얻기 이해 필요한것들 
      // post방식은 body에 넣어줘야하고 body에넣어줄려면 url이 뒤에 붙는게아니라(get)  stream으로 꽂아주는거임
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
      StringBuilder param = new StringBuilder();
      param.append("grant_type=" + "authorization_code");   
      // param.append("&client_secret=" + "");
      param.append("&client_id=" + "6995e278624264293de03a555a01e3b0"); // 미리 발급한 개인 앱키
      param.append("&redirect_uri=" + "http://localhost:8090/api/kakaologin");
      param.append("&code=" + code);
      
      // 생성한 파라미터를 출력스트림에 쓰기(요청바디에 넣기)
      bw.write(param.toString());
      bw.flush();
      
      // 입력스트림을 통해 읽기
      BufferedReader br;
      int resultCode = conn.getResponseCode();
      if(200<=resultCode && resultCode<=300) { // 정상
         br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      } else { // 에러
         br = new BufferedReader(new InputStreamReader(conn.getErrorStream())); // 에러일때는 에러스트림을 이용하여 읽는다
      }
      
      
      StringBuilder resBuilder = new StringBuilder();
      String line;
      while((line=br.readLine())!=null) {
         resBuilder.append(line);
      }
      br.close();
      conn.disconnect();
      //System.out.println("------서비스메서드 getAccessToken에서 출력\n***resBuilder.toString():\n" + resBuilder.toString());
      
      // 읽은 json데이터를 파싱처리
      JSONParser parser = new JSONParser();
      JSONObject tokenObj = (JSONObject) parser.parse(resBuilder.toString());
      String token = (String) tokenObj.get("access_token");
      
      return token;
   }
   
   
   // 3.
   public UserInfo getUserInfo(String token) throws Exception { //카카오로부터 token으로 사용자 정보 얻어오기 , 데이터 주고 받는거 무조건 json타입
      
      StringBuilder urlBuilder = new StringBuilder("https://kapi.kakao.com/v2/user/me");
         
       URL url = new URL(urlBuilder.toString());
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
       conn.setRequestMethod("GET");
       conn.setRequestProperty("Authorization", "Bearer " + token);
       conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
       
       // response
       BufferedReader br;
       int resultCode = conn.getResponseCode();
       //System.out.println("------서비스메서드 getUserInfo에서 출력\n***resultCode: " + resultCode);
       
       if(resultCode >= 200 && resultCode <= 300) { // 정상
          br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
       } else {
          // 에러일 경우, 에러의 스트림을 가져옴
          br = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
       }
       
       // 버퍼가 null일 때까지, 한 줄 단위로 resBuilder에 입력
       StringBuilder resBuilder = new StringBuilder();
       String line;
       while((line = br.readLine())!=null) {
          resBuilder.append(line);
       }
       
       br.close();
       conn.disconnect();
       System.out.println("***resBuilder.toString():\n" + resBuilder.toString());

       JSONParser parser = new JSONParser(); //api를 이용한 parsing처리 중요!!!!!
       JSONObject user = (JSONObject) parser.parse(resBuilder.toString());
       String id = (Long) user.get("id")+"";
       JSONObject properties = (JSONObject) user.get("properties");
       String nickname = (String) properties.get("nickname");
       String profileImage = (String) properties.get("profile_image");
       String thumbnailImage = (String) properties.get("thumbnail_image");
       
       JSONObject kakaoAccount = (JSONObject) user.get("kakao_account");
       String email = (String) kakaoAccount.get("email");
       String gender = (String) kakaoAccount.get("gender");
       
       UserInfo userInfo = new UserInfo(id, nickname, profileImage, thumbnailImage, email, gender);
             
       return userInfo;

   }
}