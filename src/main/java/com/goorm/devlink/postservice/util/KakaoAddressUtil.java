package com.goorm.devlink.postservice.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.goorm.devlink.postservice.config.properties.vo.KakaoAddressVo;
import com.goorm.devlink.postservice.entity.Address;
import feign.form.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RequiredArgsConstructor
public class KakaoAddressUtil {

    private final KakaoAddressVo kakaoAddressVo;
    private final RestTemplate restTemplate;
    private final MessageUtil messageUtil;

    public Address createAddress(String location){
        String response = restTemplate.exchange(request(location),String.class).getBody();
        return parseResponse(response,location);
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
    private Address parseResponse(String response,String location){
        return convertJsonToAddress(getJsonArray(response),location);
    }

    private JsonArray getJsonArray(String response){
        JsonParser jsonParser = new JsonParser();
        JsonObject parsedResponse = (JsonObject) jsonParser.parse(response);
        return parsedResponse.get("documents").getAsJsonArray();
    }

    private Address convertJsonToAddress(JsonArray jsonArray, String location){
        if(jsonArray.isEmpty()){ throw new IllegalArgumentException(messageUtil.getIllegalAddressMessage(location)); } // 에러처리하기
        JsonElement jsonElement = jsonArray.get(0);
        JsonObject jsonResponse = jsonElement.getAsJsonObject().get("address").getAsJsonObject();
        String x = jsonResponse.get("x").toString().replaceAll("\"","");
        String y = jsonResponse.get("y").toString().replaceAll("\"","");

        return Address.getInstance(location,Double.valueOf(x),Double.valueOf(y));

    }

}
