package com.mockit.mockserver;

import com.mockit.mockserver.entity.MockRequest;
import com.mockit.mockserver.repository.MockServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MockServerController {

    @Autowired
    private MockServiceRepository mockService;

    @RequestMapping("/")
    public String mockServer(Model model) {

        List<MockRequest> mockServicesList = mockService.findAll();

        model.addAttribute("mockServicesList", mockServicesList);
        return "mock-server-dashboard";

    }

}
