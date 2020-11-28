package com.mockit.MockServer.service;

import com.mockit.MockServer.dto.HeaderDTO;
import com.mockit.MockServer.dto.MockRequestDTO;
import com.mockit.MockServer.entity.MockRequest;
import com.mockit.MockServer.exception.ResourceNotFoundException;
import com.mockit.MockServer.repository.MockServiceRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MockService {

    @Autowired
    private MockServiceRepository repository;

    public MockRequest addMock(MockRequestDTO mockRequestDTO) {
        MockRequest entity = getEntityFromDTO(mockRequestDTO);
        return repository.save(entity);
    }

    public MockRequest getMock(String mockId) {
        return repository.findByMockId(mockId)
                .orElseThrow(() -> new ResourceNotFoundException("MockID " + mockId + " not found"));
    }

    public void deleteMock(String mockId) {
        MockRequest mockRequest = getMock(mockId);
        repository.delete(mockRequest);
    }

    public int updateMockRequestDelay(String mockId, int delay) {
        String filteredMockId = mockId.replace("mockServiceDelay-", "");
        return repository.updateMockServiceDelay(filteredMockId, delay);
    }

    public int updateMockRequestStatus(String mockId, String status) {
        String responseStatus = (status.equalsIgnoreCase("true")) ? "SUCCESS" : "FAILURE";
        return repository.updateMockServiceResponseStatus(mockId.replace("mockServiceDelay-", ""), responseStatus);
    }

    public MockRequest updateMock(MockRequestDTO mockRequestDTO) {

        MockRequest mockRequest = getMock(mockRequestDTO.getMockId());

        mockRequest.setRequestType(mockRequestDTO.getRequestType());
        mockRequest.setResponseStatus(mockRequestDTO.getResponseStatus());
        mockRequest.setSuccessResponseStatusCode(mockRequestDTO.getSuccessResponseStatusCode());
        mockRequest.setFailureResponseStatusCode(mockRequestDTO.getFailureResponseStatusCode());
        mockRequest.setContentType(mockRequestDTO.getContentType());
        mockRequest.setEncoding(mockRequestDTO.getEncoding());
        mockRequest.setSuccessResponseBody(mockRequestDTO.getSuccessResponseBody());
        mockRequest.setFailureResponseBody(mockRequestDTO.getFailureResponseBody());
        mockRequest.setDelay(mockRequestDTO.getDelay());

        mockRequest.getHeaders().clear();
        if (mockRequestDTO.getHeaders() != null) {
            List<HeaderDTO> filteredMockHeaders = mockRequestDTO.getHeaders().stream()
                    .filter(mockHeader -> (mockHeader != null) && (!isEmptyString(mockHeader.getName()) && !isEmptyString(mockHeader.getValue())))
                    .collect(Collectors.toList());

            if (filteredMockHeaders != null) {
                mockRequest.getHeaders().addAll(filteredMockHeaders);
            }
        }

        return repository.save(mockRequest);
    }

    private MockRequest getEntityFromDTO(MockRequestDTO request) {

        MockRequest entity = new MockRequest();

        if (!isEmptyString(request.getMockId())) {
            entity.setMockId(request.getMockId());
        } else {
            entity.setMockId(new ObjectId().toString());
        }

        entity.setRequestType(request.getRequestType());
        entity.setResponseStatus(request.getResponseStatus());
        entity.setSuccessResponseStatusCode(request.getSuccessResponseStatusCode());
        entity.setFailureResponseStatusCode(request.getFailureResponseStatusCode());
        entity.setContentType(request.getContentType());
        entity.setEncoding(request.getEncoding());
        entity.setSuccessResponseBody(request.getSuccessResponseBody());
        entity.setFailureResponseBody(request.getFailureResponseBody());
        entity.setHeaders(request.getHeaders());
        entity.setDelay(request.getDelay());

        return entity;
    }

    public boolean isEmptyString(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
