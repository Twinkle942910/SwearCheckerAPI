package com.filter.textcorrector.api;

import org.jose4j.json.JsonUtil;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.json.JSONException;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

public class OAuth2Test {

    protected JwtConsumer jwtConsumer;

    protected static final Logger logger = LoggerFactory.getLogger(OAuth2Test.class);

    @Before
    public void setup() {
        jwtConsumer = new JwtConsumerBuilder().setSkipAllValidators().setDisableRequireSignature().setSkipSignatureVerification()
                .build();
    }

    protected void logJWTClaims(JwtContext jwtContext) {
        logger.info(prettyPrintJson(JsonUtil.toJson(jwtContext.getJwtClaims().getClaimsMap())));
    }

    protected void logJson(String json) {
        logger.info(prettyPrintJson(json));
    }

    protected String prettyPrintJson(String flatJson) {
        try {
            return (new JSONObject(flatJson).toString(3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
