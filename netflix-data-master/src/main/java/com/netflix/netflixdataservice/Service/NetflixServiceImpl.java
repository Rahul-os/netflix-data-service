package com.netflix.netflixdataservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.netflix.netflixdataservice.Entity.NetflixData;
import com.netflix.netflixdataservice.Repository.NetflixRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NetflixServiceImpl implements NetflixService{

	@Autowired
	NetflixRepository repo;

	int countA = 0,countB=0,countc=0,countd=0,counte=0;

	@Override
	public NetflixData saveNetflixData(NetflixData newdata) {
		return repo.save(newdata);
	}

	@Override
	public NetflixData updateNetflixData(String title , NetflixData newData) {
		if(!repo.findByTitle(title).isEmpty()) {
			NetflixData updatedData = repo.updateByTitle(title);    // also use 'id' while passing RequestBody from postman inorder to update the already present record.
			//updatedData.setId(newData.getId());
			updatedData.setTitle(newData.getTitle());
			updatedData.setDescription(newData.getDescription());
			updatedData.setRuntime(newData.getRuntime());
			updatedData.setImdb_score(newData.getImdb_score());
			repo.save(updatedData);
			return updatedData;
		} else
			throw new RuntimeException("Enter the proper title to update the details!!");
	}

	@Override
	public Optional<List<NetflixData>> findByTitle(String title) {
		Optional<List<NetflixData>> data = repo.findByTitle(title);
		return  data;

	}

	@Override
	public void deleteMovieAndShowData(String title) {
		List<NetflixData> recordToBeDeleted = repo.deleteDataByTitle(title);  //we can also delete multiple movies which are having the same name by using List<> and then itearte and delete allof them.
		if(recordToBeDeleted != null)
			repo.deleteAll(recordToBeDeleted); // found that Taxi Driver has multiple items so used deleteAll .

	}

	@Override
	public List<NetflixData> getAllRecords() {
		return repo.findAll();
	}

	@Override
	public void saveAllRecordsInCsvFileAtOnce(List<NetflixData> netflixDataList) {
		repo.saveAll(netflixDataList);
	}

	public Page<NetflixData> getRecordsWithPagination(int offset, int numberOfRecords)
	{
		Page<NetflixData> records =  repo.findAll(PageRequest.of(offset, numberOfRecords));
		return records;
	}

	public  Page<NetflixData> getRecordsWithPaginationAndSorting(int offset, int pageSize, String field)
	{
		return repo.findAll(PageRequest.of(offset, pageSize,Sort.by(field)));
	}
//	@Override
//	public int checkTheCountOfTile(List<NetflixData> eachrecord) {
//
//		for(NetflixData eachRecord1 : eachrecord) {
//			if(eachRecord1.getTitle().startsWith(String.valueOf('A')))
//				countA++;
//			else if (eachRecord1.getTitle().startsWith(String.valueOf('B'))) {
//				countB++;
//			}
//			else if(eachRecord1.getTitle().startsWith(String.valueOf('C')))
//				countc++;
//			else if(eachRecord1.getTitle().startsWith(String.valueOf('D')))
//				countd++;
//			else if(eachRecord1.getTitle().startsWith(String.valueOf('E')))
//				counte++;
//		}
//	}
}
