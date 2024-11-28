package com.netflix.netflixdataservice.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.netflix.netflixdataservice.Service.NetflixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.netflix.netflixdataservice.Entity.*;
import com.netflix.netflixdataservice.Service.ImportCSVDataService;
import org.springframework.web.multipart.MultipartFile;

@RestController

public class ImportDataFromCSVController {

	@Autowired
	ImportCSVDataService csvDataService;
	@Autowired
	NetflixService netflixService;

	List<NetflixData> netflixDataList = new ArrayList<>();

	@RequestMapping(value = "/importDataFromCsvFile" , method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
	public List<NetflixData> importDataFromCSVFile(@RequestParam("csvFile") MultipartFile file) throws Exception {
		try {
			//public static File createTempFile(String PREFIX, String SUFFIX)
			File tempFile = File.createTempFile("temp",null); //I created this temp file bcz, when i am calling the importNetflixDataFromCSV() method i need to pass the location of the file that i want to import, when i passed the file directly i got an error since i passed the entire file.

			file.transferTo(tempFile);
			netflixDataList = csvDataService.importNetflixDataFromCSV(tempFile.getAbsolutePath());
//			for (NetflixData eachrow : netflixDataList)
//				netflixService.saveNetflixData(eachrow);   // takes more time, since each row is being saved individually.

			netflixService.saveAllRecordsInCsvFileAtOnce(netflixDataList);

		} catch (Exception e){
			System.out.println(e);
		}
		if(!netflixDataList.isEmpty())
			return netflixDataList;
		else
			return null;
	}
}
