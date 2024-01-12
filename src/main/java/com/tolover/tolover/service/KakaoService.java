package com.tolover.tolover.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tolover.tolover.exception.BaseException;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import static com.tolover.tolover.exception.BaseResponseStatus.INVALID_ACCESS_KAKAO;

@Log4j2
@Service
public class KakaoService {
    public long checkKakaoUser(String token) throws BaseException {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        Map<String, Object> resMap = null;
        ObjectMapper mapper = new ObjectMapper();

        //kakao_access_token 으로 사용자 유효한지 확인
        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            if(responseCode != 200){
                log.error("만료되거나 유효하지 않은 KAKAO ACCESS TOKEN 입니다.");
                throw new BaseException(INVALID_ACCESS_KAKAO);
            }
            InputStream inputStream = conn.getInputStream();
            resMap = mapper.readValue(inputStream, Map.class);	//결과 받아옴.


            try{
                long kakaoIdx = Long.parseLong(String.valueOf(resMap.get("id")));
                return kakaoIdx;
            }catch (NumberFormatException e){
                long aa= Long.parseLong("2516106831");

                log.error(resMap.get("id"));
                log.error(e);
            }


        } catch (MalformedURLException | ProtocolException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        }
        return 0;
    }
}
