package com.junit.springboottesting.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit.springboottesting.model.Employee;
import com.junit.springboottesting.repository.EmployeeRepository;
import com.junit.springboottesting.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeIntegrationControllerTest {


    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    Employee employee;

    @Test
    public void testCreateEmployee() throws Exception {
        //given -precondition or setup
        employee = Employee.builder()
                .firstName("Pradnya")
                .lastName("Navadkar")
                .email("pradnya.n@gmail.com")
                .build();


        //when action on behavior that we are going to test
        //end point test
        ResultActions response = mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        //then verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));




    }
    @Test
    public void testGetEmployee() throws Exception {
        //given -precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("Pradnya")
                .lastName("Navadkar")
                .email("pradnya.n@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Pradnya1")
                .lastName("Navadkar2")
                .email("pradnya1.n@gmail.com")
                .build();
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);

        employeeRepository.saveAll(list);


        //end point test
        ResultActions response = mockMvc.perform(get("/api/employee"));

        //then verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",CoreMatchers.is(list.size())));



    }
    @Test
    public void testGetEmployeeById() throws Exception {

        Long employeeId=1L;
        //given -precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("Pradnya")
                .lastName("Navadkar")
                .email("pradnya.n@gmail.com")
                .build();

        employeeRepository.save(employee1);
        //when action on behavior that we are going to test
        //end point test
        ResultActions response = mockMvc.perform(get("/api/employee/{id}",employeeId));

        //then verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee1.getFirstName())));


    }
}
















