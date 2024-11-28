package com.netflix.netflixdataservice.controllerTest;

import com.mongodb.assertions.Assertions;
import com.netflix.netflixdataservice.Controller.NetflixController;
import com.netflix.netflixdataservice.Entity.NetflixData;
import com.netflix.netflixdataservice.Service.NetflixService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.junit.MockitoJUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.Collections;

@SpringBootTest
public class NetflixControllerTest {

    @Mock
    NetflixService service;
    @Autowired
    NetflixController netflixController;

    NetflixData newData = new NetflixData("100","18","above 18 years can watch", Collections.EMPTY_LIST,Collections.EMPTY_LIST,2018,140,"Fast and Furious","Thriller",8.5);

    @Test
    public void givenData_thenSaveDataIntoDatabase()
    {
        netflixController.insertNetflixData(newData).getBody();
        Mockito.when(service.saveNetflixData(newData)).thenReturn(newData);
        //Assertions.assertTrue(!newData.getTitle().isEmpty());
        Assertions.assertNotNull(newData);

    }
}
