//package com.example.webapp.controller;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
//import com.example.webapp.dto.UserDTO;
//import com.example.webapp.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@ContextConfiguration(classes = {WebAppController.class})
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(WebAppController.class)
//@ActiveProfiles("qa")
//class WebAppControllerTest {
//  @MockBean private UserService userService;
//
//  //@Autowired MockMvc mvc;
//  @Autowired private WebAppController webAppController;
//  /** Method under test: {@link WebAppController#createUser(UserDTO)} */
//  @Test
//  void testCreateUserBadRequest() throws Exception {
//    UserDTO userDTO = new UserDTO();
//    userDTO.setFirstName("Jane");
//    userDTO.setLastName("Doe");
//    userDTO.setPassword("password");
//    userDTO.setUsername("janedoe");
//    String content = (new ObjectMapper()).writeValueAsString(userDTO);
//    MockHttpServletRequestBuilder requestBuilder =
//        MockMvcRequestBuilders.post("/v1/user")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(content);
//    //    mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().is(400));
//    // }
//    ResultActions actualPerformResult =
//        MockMvcBuilders.standaloneSetup(webAppController).build().perform(requestBuilder);
//    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
//  }
//
//  @Test
//  void testCreateUserSuccess() throws Exception {
//    UserDTO userDTO = new UserDTO();
//    userDTO.setFirstName("Jane");
//    userDTO.setLastName("Doe");
//    userDTO.setPassword("password");
//    userDTO.setUsername("janedoe@example.com");
//    String content = (new ObjectMapper()).writeValueAsString(userDTO);
//    MockHttpServletRequestBuilder requestBuilder =
//        MockMvcRequestBuilders.post("/v1/user")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(content);
//    ResultActions actualPerformResult =
//        MockMvcBuilders.standaloneSetup(webAppController).build().perform(requestBuilder);
//    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(201));
//  }
//
//  @Test
//  void testCreateUserBadFirstName() throws Exception {
//    UserDTO userDTO = new UserDTO();
//    userDTO.setFirstName("Jane name is greater than 20 characters");
//    userDTO.setLastName("Doe");
//    userDTO.setPassword("password");
//    userDTO.setUsername("janedoe@example.com");
//    String content = (new ObjectMapper()).writeValueAsString(userDTO);
//    MockHttpServletRequestBuilder requestBuilder =
//        MockMvcRequestBuilders.post("/v1/user")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(content);
//    ResultActions actualPerformResult =
//        MockMvcBuilders.standaloneSetup(webAppController).build().perform(requestBuilder);
//    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
//  }
//}
