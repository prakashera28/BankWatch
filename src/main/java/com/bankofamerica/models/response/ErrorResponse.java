package com.bankofamerica.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    @JsonProperty("errorCode") private String errorCode;
    @JsonProperty("errorMessage") private String errorMessage;
    @JsonProperty("errorDescription") private String errorDescription;
    @JsonProperty("timestamp") private String timestamp;
    @JsonProperty("traceId") private String traceId;

    public ErrorResponse() {}

    public String getErrorCode() { return errorCode; }
    public String getErrorMessage() { return errorMessage; }
    public String getErrorDescription() { return errorDescription; }
    public String getTimestamp() { return timestamp; }
    public String getTraceId() { return traceId; }

    public void setErrorCode(String v) { errorCode = v; }
    public void setErrorMessage(String v) { errorMessage = v; }
    public void setErrorDescription(String v) { errorDescription = v; }
    public void setTimestamp(String v) { timestamp = v; }
    public void setTraceId(String v) { traceId = v; }

    @Override
    public String toString() {
        return "ErrorResponse{errorCode='" + errorCode + "', errorMessage='" + errorMessage + "'}";
    }
}
