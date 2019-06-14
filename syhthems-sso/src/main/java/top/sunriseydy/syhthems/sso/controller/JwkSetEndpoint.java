package top.sunriseydy.syhthems.sso.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * JWT Set Endpoint
 *
 * @author SunriseYDY
 * @date 2019-04-24 15:48
 */
@FrameworkEndpoint
@RequestMapping("/sso")
public class JwkSetEndpoint {
    private KeyPair keyPair;

    public JwkSetEndpoint(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey(Principal principal) {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
