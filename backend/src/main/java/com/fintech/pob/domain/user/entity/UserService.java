package com.fintech.pob.domain.user.entity;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class UserService {

    private static final String API_URL = "https://fineopenapi.ssafy.io/ssafy/api/v1/member/";

    public void createUserAccount(String apiKey, String userId) {
        // 요청 JSON 생성
        JSONObject requestJson = new JSONObject();
        requestJson.put("apiKey", apiKey);
        requestJson.put("userId", userId);

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
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("@@@@@계정 생성 성공:" + response.getBody());

            } else {
                System.out.println("@@@@ 실패 :" + response.getStatusCode());
            }


        } catch (Exception e) {
            System.out.println("@@@@@@@ 오류 발생 @@@@@ : " + e.getMessage());
        }
    }
}
