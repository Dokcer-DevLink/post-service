package com.goorm.devlink.postservice.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.goorm.devlink.postservice.config.properties.vo.KakaoAddressVo;
import feign.form.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class KakaoAddressUtil {

    private final KakaoAddressVo kakaoAddressVo;
    private final RestTemplate restTemplate;

    public List<String> findAddressList(String location){
        // 에러처리 필요
        String response = restTemplate.exchange(request(location),String.class).getBody();
        System.out.println(response);
        return parseResponse(response);

    }

    private RequestEntity request(String location){
        return new RequestEntity(headers(), HttpMethod.GET,url(location));
    }
    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",kakaoAddressVo.getSecretKey());
        return headers;
    }

    private URI url(String location){
        return UriComponentsBuilder.fromHttpUrl(kakaoAddressVo.getUrl())
                .queryParam("query",location)
                .encode(CharsetUtil.UTF_8)
                .build()
                .toUri();
    }
    private List<String> parseResponse(String response){
        return convertJsonArrayToList(getJsonArray(response));
    }

    private JsonArray getJsonArray(String response){
        JsonParser jsonParser = new JsonParser();
        JsonObject parsedResponse = (JsonObject) jsonParser.parse(response);
        return parsedResponse.get("documents").getAsJsonArray();
    }

    private List<String> convertJsonArrayToList(JsonArray jsonArray){
        List<String> result = new ArrayList<>();
        jsonArray.forEach(jsonElement -> {
            String address = jsonElement.getAsJsonObject().get("address")
                    .getAsJsonObject().get("address_name")
                    .toString().replaceAll("\"","");
            result.add(address);
        });
        return result;
    }

}
