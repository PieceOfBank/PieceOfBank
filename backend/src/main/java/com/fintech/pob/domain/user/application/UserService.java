package com.fintech.pob.domain.user.application;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private static final String API_URL = "https://finopenapi.ssafy.io/ssafy/api/v1/member/";

    private static final String FIXED_API_KEY = "35e455cd046e44b09bfbe09726ff947a";

    public String createUserAccount(String email) {

        // 요청 JSON 생성
        JSONObject requestJson = new JSONObject();
        requestJson.put("apiKey", FIXED_API_KEY);
        requestJson.put("userId", email);

        // HTTP 요청 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 바디 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson.toString(), headers);

        // API 요청 전송
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                // 응답에서 userName 추출
                JSONObject responseBody = new JSONObject(response.getBody());
                String userKey = responseBody.getString("userKey");
                System.out.println("@@@@ 계정 생성 성공: " + userKey);
                return userKey;

            } else {
                System.out.println("@@@@ 실패 :" + response.getStatusCode());
                throw new RuntimeException("계정 생성 실패: " + response.getStatusCode());

            }


        } catch (Exception e) {
            System.out.println("@@@@@@@ 오류 발생 @@@@@ : " + e.getMessage());
            throw new RuntimeException("API 요청 중 오류 발생: " + e.getMessage());

        }
    }

}
