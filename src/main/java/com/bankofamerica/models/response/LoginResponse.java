package com.bankofamerica.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {

    @JsonProperty("accessToken") private String accessToken;
    @JsonProperty("tokenType") private String tokenType;
    @JsonProperty("expiresIn") private Integer expiresIn;
    @JsonProperty("refreshToken") private String refreshToken;
    @JsonProperty("scope") private String scope;
    @JsonProperty("userId") private String userId;
    @JsonProperty("sessionId") private String sessionId;

    public LoginResponse() {}

    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }
    public Integer getExpiresIn() { return expiresIn; }
    public String getRefreshToken() { return refreshToken; }
    public String getScope() { return scope; }
    public String getUserId() { return userId; }
    public String getSessionId() { return sessionId; }

    public void setAccessToken(String v) { accessToken = v; }
    public void setTokenType(String v) { tokenType = v; }
    public void setExpiresIn(Integer v) { expiresIn = v; }
    public void setRefreshToken(String v) { refreshToken = v; }
    public void setScope(String v) { scope = v; }
    public void setUserId(String v) { userId = v; }
    public void setSessionId(String v) { sessionId = v; }

    @Override
    public String toString() {
        return "LoginResponse{accessToken='" + (accessToken != null ? "[PRESENT]" : "null") + "', tokenType='" + tokenType + "', expiresIn=" + expiresIn + "}";
    }
}
