package com.bankofamerica.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest {

    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("rememberMe") private Boolean rememberMe;
    @JsonProperty("deviceId") private String deviceId;
    @JsonProperty("clientId") private String clientId;

    private LoginRequest() {}

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Boolean getRememberMe() { return rememberMe; }
    public String getDeviceId() { return deviceId; }
    public String getClientId() { return clientId; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final LoginRequest r = new LoginRequest();
        public Builder username(String v) { r.username = v; return this; }
        public Builder password(String v) { r.password = v; return this; }
        public Builder rememberMe(Boolean v) { r.rememberMe = v; return this; }
        public Builder deviceId(String v) { r.deviceId = v; return this; }
        public Builder clientId(String v) { r.clientId = v; return this; }
        public LoginRequest build() { return r; }
    }

    @Override
    public String toString() {
        return "LoginRequest{username='" + username + "', password='[REDACTED]', rememberMe=" + rememberMe + ", clientId='" + clientId + "'}";
    }
}
