package com.junit.springboottesting.repository;


import com.junit.springboottesting.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EmployeeRepositoryTest {

     private Employee employee;
    @Autowired
    private EmployeeRepository employeeRepository;
    //
    @BeforeEach //given
    public void setup(){
     employee=Employee.builder()
             .firstName("Pradnya")
             .lastName("navadkar")
             .email("pradnya@gmail.com")
             .build();

        Employee saveEmployee = employeeRepository.save(employee);

    }



    @DisplayName("this is junit positive test case positive test ")
    @Test
    public void testFindByEmail(){

    // given when then
        //when

        Employee employee1 = employeeRepository.findByEmail(employee.getEmail()).get();

        //then

        Assertions.assertEquals("pradnya@gmail.com",employee1.getEmail());


    }




}
