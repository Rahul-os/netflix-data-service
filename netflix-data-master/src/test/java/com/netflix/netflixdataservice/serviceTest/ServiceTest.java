package com.netflix.netflixdataservice.serviceTest;

import com.netflix.netflixdataservice.Entity.NetflixData;
import com.netflix.netflixdataservice.Repository.NetflixRepository;
import com.netflix.netflixdataservice.Service.NetflixService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.netflix.netflixdataservice.Entity.NetflixData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceTest {

    @Mock
    NetflixService service ;

    @Mock
    NetflixRepository repository;

    NetflixData newData = new NetflixData("100","18","above 18 years can watch", Collections.EMPTY_LIST,Collections.EMPTY_LIST,2018,140,"Fast and Furious","Thriller",8.5);





    @Test
    public void givenNewData_whenSave_thenSaveInDB()
    {
        service.saveNetflixData(newData);
        when(repository.save(newData)).thenReturn(newData);
        Assertions.assertTrue(!newData.getId().isEmpty());
    }

    @Test
    public void givenUpdatedData_whenUpdate_thenSaveUpdatedData()
    {
        NetflixData updateNetflixData = new NetflixData() ;
        updateNetflixData.setTitle("Fast and Furious");
        updateNetflixData.setRuntime(125);
        updateNetflixData.setDescription("Anyone can watch!!");
        updateNetflixData.setImdb_score(8.0);
        when(repository.updateByTitle("Fast and Furious")).thenReturn(updateNetflixData);
        NetflixData result  = service.updateNetflixData("Fast and Furious" , updateNetflixData);
        Assertions.assertTrue(Boolean.parseBoolean(updateNetflixData.description), result.getDescription());

    }

    @Test
    public void givenTitle_whenFindByTitle_thenReturnNetflixDataObject()
    {
        String title = "Fast and Furious";
        service.findByTitle(title);
        when(repository.findByTitle(title)).thenReturn(Optional.of(List.of(newData)));
        Assertions.assertNotNull(newData);
        Assertions.assertEquals(title,newData.getTitle());
    }
}
