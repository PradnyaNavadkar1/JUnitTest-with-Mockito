package com.junit.springboottesting.service;

import com.junit.springboottesting.exception.ResourceNotFoundException;
import com.junit.springboottesting.model.Employee;
import com.junit.springboottesting.repository.EmployeeRepository;
import com.junit.springboottesting.service.impl.EmployeeServiceImpl;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    //first mock the repository
    @Mock
    private EmployeeRepository employeeRepository;
    //inject mock impl class
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    Employee employee;

    @BeforeEach
    public void setUp() {
        employee = Employee.builder()
                .id(1L)
                .email("pradny.d@gmail.com")
                .firstName("pradnya")
                .lastName("Navadkar")
                .build();

    }


    @DisplayName("Junit test for saveEmployeeMethod (positive test)")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObejct() {
        //EmployeeRepository employeeRepository=mock(EmployeeRepository.class);

        /**
         * stubbing  for the findByEmail & save methods ....
         *
         */
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());

        when(employeeRepository.save(employee)).thenReturn(employee);

        //actual method test...
        employeeService.saveEmployee(employee);

        // verify the exceptions
        verify(employeeRepository, times(1)).findByEmail(employee.getEmail());

    }

    @DisplayName("Junit test for saveEmployeeMethod which throw Exception(negative test)")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnException() {
        /**
         * stubbing  for the findByEmail & save methods ....
         *
         */
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));

        //this for the exception
        Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));

        // verify the exceptions
        verify(employeeRepository, never()).save(employee);
    }

    @DisplayName("junit test for getAllEmployee(Positive Test)")
    @Test
    public void givenEmployeeList_whenGetAllEmployee_thenReturnEmployeeList() {

        Employee employee = Employee.builder()
                .firstName("Radha")
                .lastName("Siara")
                .email("readh.@gmail.com")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("vrunda")
                .lastName("patil")
                .email("vrunda.@gmail.com")
                .build();



        /**
         * stubbing  for the findByEmail & save methods ....
         *
         */
        when(employeeRepository.findAll()).thenReturn(List.of(employee, employee1));
        List<Employee> allEmployee = employeeService.getAllEmployee();
        Assertions.assertEquals(allEmployee.size(),2);

    }
    @DisplayName("junit test for getEmployeeById(Positive Test)")
    @Test
    public void givenEmployeeById_whenGetEmployeeById_thenReturnEmployeObject(){
        long employeeId=1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
       Employee employeeById = employeeService.getEmployeeById(employee.getId());
        Assertions.assertEquals(employeeId,employee.getId());

    }
    @DisplayName("junit test for getEmployeeById(Positive Test)")
    @Test
    public void givenEmployeeById_whenSaveEmployeeById_thenReturnEmployeObject(){
        long employeeId=1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Employee employeeById = employeeService.getEmployeeById(employee.getId());
        Assertions.assertEquals(employeeId,employee.getId());

    }



}

