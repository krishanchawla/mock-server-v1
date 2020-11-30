package com.mockit.mockserver.entity;

import com.mockit.mockserver.dto.HeaderDTO;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MOCK_SERVICES")
public class MockRequest {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @Column(name = "mockId")
    private String mockId;

    private String requestType;
    private String responseStatus;
    private int successResponseStatusCode;
    private int failureResponseStatusCode;
    private String contentType;
    private String encoding;
    private String successResponseBody;
    private String failureResponseBody;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "mockRequestId")
    private List<HeaderDTO> headers;

    private int delay;

    public String getMockId() {
        return mockId;
    }

    public void setMockId(String mockId) {
        this.mockId = mockId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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


}
