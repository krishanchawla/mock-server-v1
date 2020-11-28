package com.mockit.MockServer;

import com.mockit.MockServer.dto.HeaderDTO;
import com.mockit.MockServer.dto.MockRequestDTO;
import com.mockit.MockServer.entity.MockRequest;
import com.mockit.MockServer.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class MockRestController {

    @Autowired
    private MockService service;

    @RequestMapping(value = "/mock/{mockId}")
    public ResponseEntity<?> mockRequest(@PathVariable String mockId) {

        MockRequest entity = service.getMock(mockId);
        MultiValueMap<String, String> headers = getHeadersFromEntity(entity);

        HttpStatus httpStatus = (entity.getResponseStatus().equals("SUCCESS"))
                ? HttpStatus.valueOf(entity.getSuccessResponseStatusCode())
                : HttpStatus.valueOf(entity.getFailureResponseStatusCode());

        String body = (entity.getResponseStatus().equals("SUCCESS"))
                ? entity.getSuccessResponseBody()
                : entity.getFailureResponseBody();

        if (entity.getDelay() > 0) {
            try {
                Thread.sleep(entity.getDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(body, headers, httpStatus);
    }

    @RequestMapping(value = "api/addMock", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody MockRequestDTO request) {
        MockRequest createdMockRequest = service.addMock(request);
        return new ResponseEntity<>(createdMockRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "api/getMock/{mockId}", method = RequestMethod.GET)
    public ResponseEntity<?> view(@PathVariable String mockId) {
        MockRequest mockRequest = service.getMock(mockId);
        return new ResponseEntity<>(mockRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "api/deleteMock/{mockId}", method = RequestMethod.GET)
    public ResponseEntity<?> delete(@PathVariable String mockId) {
        service.deleteMock(mockId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "api/modifyMock", method = RequestMethod.POST)
    public ResponseEntity<?> update(@RequestBody MockRequestDTO request) {
        MockRequest updatedMockRequest = service.updateMock(request);
        return new ResponseEntity<>(updatedMockRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "api/updateMockDelay", method = RequestMethod.POST)
    public ResponseEntity<?> updateMockDelay(@RequestParam(name = "mockId") String mockId, @RequestParam(name = "value") Integer delay) {
        service.updateMockRequestDelay(mockId, delay);
        return new ResponseEntity<>(delay, HttpStatus.OK);
    }

    @RequestMapping(value = "api/updateMockResponseStatus", method = RequestMethod.POST)
    public ResponseEntity<?> updateMockResponseStatus(@RequestParam(name = "mockId") String mockId, @RequestParam(name = "value") String status) {
        service.updateMockRequestStatus(mockId, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private MultiValueMap<String, String> getHeadersFromEntity(MockRequest entity) {
        MultiValueMap<String, String> headers = new HttpHeaders();

        for (HeaderDTO header : entity.getHeaders()) {
            headers.add(header.getName(), header.getValue());
        }

        if (StringUtils.isNotEmpty(entity.getContentType())) {
            headers.add("Content-Type", entity.getContentType());
            headers.add("Accept-Encoding", entity.getEncoding());
        }
        return headers;
    }

}
