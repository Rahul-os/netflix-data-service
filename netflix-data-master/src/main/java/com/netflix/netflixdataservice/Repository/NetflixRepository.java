package com.netflix.netflixdataservice.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.netflix.netflixdataservice.Entity.NetflixData;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NetflixRepository extends MongoRepository<NetflixData, String> {

    @Query("{'title':?0}")
    NetflixData updateByTitle(String title);

    @Query("{'title':?0}")
    Optional<List<NetflixData>> findByTitle(String title);

    @Query("{'title':?0}")
    List<NetflixData> deleteDataByTitle(String title);
}
