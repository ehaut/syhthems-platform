package top.sunriseydy.syhthems.web.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import top.sunriseydy.syhthems.common.properties.OAuthProperties;
import top.sunriseydy.syhthems.common.properties.SyhthemsProperties;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author SunriseYDY
 * @date 2019-05-16 21:31
 */
@RestController
public class UserController extends BaseController {

    private final RestTemplate restTemplate;

    private final OAuthProperties oAuthProperties;

    private final SyhthemsProperties syhthemsProperties;

    public UserController(RestTemplateBuilder restTemplateBuilder, OAuthProperties oAuthProperties, SyhthemsProperties syhthemsProperties) {
        this.syhthemsProperties = syhthemsProperties;
        restTemplateBuilder.basicAuthentication(oAuthProperties.getClientId(), oAuthProperties.getClientSecret());
        this.restTemplate = restTemplateBuilder.build();
        this.oAuthProperties = oAuthProperties;
    }

    @PostMapping(value = "/oauth/token")
    public String tokenEndpoint(HttpServletRequest request) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        Map<String, String[]> requestMap = request.getParameterMap();
        requestMap.forEach((s, strings) -> {
            body.add(s, strings[0]);
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(this.oAuthProperties.getClientId(), this.oAuthProperties.getClientSecret(), Charset.forName("UTF-8"));
        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(body, headers);
        return this.restTemplate.postForObject(this.syhthemsProperties.getSecurity().getSsoServer() + "/oauth/token",
                httpEntity,
                String.class);
    }
}
