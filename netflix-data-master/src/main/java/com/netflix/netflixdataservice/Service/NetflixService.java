package com.netflix.netflixdataservice.Service;

import com.netflix.netflixdataservice.Entity.NetflixData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NetflixService {
	


	NetflixData saveNetflixData(NetflixData newdata);

	NetflixData updateNetflixData(String title , NetflixData newData);

	Optional<List<NetflixData>> findByTitle(String title);

	void deleteMovieAndShowData(String title);

	List<NetflixData> getAllRecords();

	void saveAllRecordsInCsvFileAtOnce(List<NetflixData> netflixDataList);

	Page<NetflixData> getRecordsWithPagination(int offset, int numberOfRecords);

	Page<NetflixData> getRecordsWithPaginationAndSorting(int offset, int pageSize, String field);

//	int checkTheCountOfTile(List<NetflixData> eachrecord);
}
