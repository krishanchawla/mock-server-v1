package com.mockit.mockserver.dto;

import java.util.List;

public class MockRequestDTO {

    private String mockId;

    private String requestType;
    private String responseStatus;
    private int successResponseStatusCode;
    private int failureResponseStatusCode;
    private String contentType;
    private String encoding;
    private String successResponseBody;
    private String failureResponseBody;
    private List<HeaderDTO> headers;
    private int delay;

    public String getMockId() {
        return mockId;
    }

    public void setMockId(String mockId) {
        this.mockId = mockId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }


    public List<HeaderDTO> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HeaderDTO> headers) {
        this.headers = headers;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getSuccessResponseStatusCode() {
        return successResponseStatusCode;
    }

    public void setSuccessResponseStatusCode(int successResponseStatusCode) {
        this.successResponseStatusCode = successResponseStatusCode;
    }

    public int getFailureResponseStatusCode() {
        return failureResponseStatusCode;
    }

    public void setFailureResponseStatusCode(int failureResponseStatusCode) {
        this.failureResponseStatusCode = failureResponseStatusCode;
    }

    public String getSuccessResponseBody() {
        return successResponseBody;
    }

    public void setSuccessResponseBody(String successResponseBody) {
        this.successResponseBody = successResponseBody;
    }

    public String getFailureResponseBody() {
        return failureResponseBody;
    }

    public void setFailureResponseBody(String failureResponseBody) {
        this.failureResponseBody = failureResponseBody;
    }

    @Override
    public String toString() {
        return "MockRequestDTO{" +
                "mockId='" + mockId + '\'' +
                ", requestType='" + requestType + '\'' +
                ", responseStatus='" + responseStatus + '\'' +
                ", successResponseStatusCode=" + successResponseStatusCode +
                ", failureResponseStatusCode=" + failureResponseStatusCode +
                ", contentType='" + contentType + '\'' +
                ", encoding='" + encoding + '\'' +
                ", successResponseBody='" + successResponseBody + '\'' +
                ", failureResponseBody='" + failureResponseBody + '\'' +
                ", headers=" + headers +
                ", delay=" + delay +
                '}';
    }
}
