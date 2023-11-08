package com.kosta.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.api.dao.UserDao;
import com.kosta.api.dto.Token;
import com.kosta.api.dto.UserInfo;

@Service
public class NaverLoginService {
	
	@Autowired
	private UserDao userDao;

   // Documents>네이버로그인>API명세 2. callback.jsp의 코드
   public UserInfo naverLogin(String code, String state) throws Exception {
      Token token = getAccessToken(code, state);
      UserInfo loginUser = getUserInfo(token); //네이버에서 로그인한 정보가져와서 
      UserInfo userInfo = userDao.selectUser(loginUser.getId()); //우리 테이블에 동일한 아이디 있나 확인하고 
      if(userInfo==null) { //아이디가 없으면 회원가입 되어있지않기에
    	  userDao.insertUserByNaver(loginUser); //회원가입함.
    	  userInfo = loginUser;
      }      
      return userInfo;
   }
   
   
   public Token getAccessToken(String code, String state) throws Exception {
      
      String clientId = "GeaPAv7X8Q58pPjEI9oE";
      String clientSecret = "iSZbV96LPo";
      String redirectURI = URLEncoder.encode("http://localhost:8090/api/naverlogin", "UTF-8");
      StringBuilder apiURL = new StringBuilder("https://nid.naver.com/oauth2.0/token");
      apiURL.append("?grant_type=authorization_code");
      apiURL.append("&client_id=" + clientId);
      apiURL.append("&client_secret=" + clientSecret);
      apiURL.append("&redirect_uri=" + redirectURI);
      apiURL.append("&code=" + code);
      apiURL.append("&state=" + state);


      URL url = new URL(apiURL.toString());
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      int responseCode = con.getResponseCode();
      BufferedReader br;
      System.out.println("*** responseCode=" + responseCode);
      
      if (responseCode == 200) { // 정상 호출
         br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else { // 에러 발생
         br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      String inputLine;
      StringBuffer res = new StringBuffer();
      while ((inputLine = br.readLine()) != null) {
         res.append(inputLine);
      }
      br.close();
      
      System.out.println("*** res.toString():\n" + res.toString()); // JSON파싱을 진행하기 위해 가져온 토큰을 출력하여 확인한다
      // {"access_token":"...","refresh_token":"...","token_type":"bearer","expires_in":"3600"}
      
      if (responseCode != 200) {
         throw new Exception(res.toString()); // 에러 메시지
      }
      
      // 가져온 토큰을 파싱처리하여 토큰객체 생성
      Token token = new Token();
      JSONParser parser = new JSONParser();
      JSONObject tokenObj = (JSONObject) parser.parse(res.toString());
      token.setAccessToken((String) tokenObj.get("access_token"));
      token.setTokenType((String) tokenObj.get("token_type"));
      
      return token;
   }
   
   public UserInfo getUserInfo(Token token) throws Exception {
      
      URL url = new URL("https://openapi.naver.com/v1/nid/me");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Authorization", token.getTokenType() + " " + token.getAccessToken() );
      
      BufferedReader br;
      int responseCode = con.getResponseCode();
      if (responseCode == 200) { // 정상 호출
         br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else { // 에러 발생
         br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      StringBuffer resBuilder = new StringBuffer();
      String line;
      while ((line = br.readLine()) != null) {
         resBuilder.append(line);
      }
      
      
      br.close();
      con.disconnect();
      System.out.println("*** resBuilder.toString():\n" + resBuilder.toString());
      
      JSONParser parser = new JSONParser();
      JSONObject resObj = (JSONObject) parser.parse(resBuilder.toString());
      JSONObject user = (JSONObject) resObj.get("response");
      String id = (String)user.get("id");
      String nickname = (String)user.get("nickname");
      String age = (String)user.get("age");
      String gender = (String)user.get("gender");
      String email = (String)user.get("email");
      String mobile = (String)user.get("mobile");
      String name = (String)user.get("name");
      String birthday = (String)user.get("birthday");
      
      
      UserInfo userInfo = new UserInfo(id,nickname,email,gender,name,mobile,age,birthday);
      return userInfo;
   }

}