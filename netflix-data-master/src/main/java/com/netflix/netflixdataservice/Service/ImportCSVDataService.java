package com.netflix.netflixdataservice.Service;

import com.netflix.netflixdataservice.Entity.NetflixData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class ImportCSVDataService {

    public List<NetflixData> importNetflixDataFromCSV(String csvFilePath) throws  Exception {
        List<NetflixData> netflixDataList = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) { // withFirstRecordAsHeader assumes that the first record is a header and continues from the next record which is the actual data.
            for (CSVRecord csvRecord : csvParser) {
                NetflixData netflixData = new NetflixData();
                netflixData.setId(csvRecord.get(3));
                netflixData.setDescription(csvRecord.get(1));

//                String generes = csvRecord.get(2);         /* It is stored in a local variable and then parsed , because when the data in the file is empty, since everything is STRING in the file, when
//                                                            casting to the required data type i got exception when DEBUGGING. It's the same for all param's where casting is done*/
//                if(!generes.isEmpty())
//                    netflixData.setGenres(Arrays.asList(generes));
                String generes = csvRecord.get(2);
                List<String> genresList = new ArrayList<>();

                if (!generes.isEmpty()) {
                    // Remove brackets and whitespace from the string
                    generes = generes.replaceAll("\\[|\\]|\"", "").trim();


                    String[] genreArray = generes.split(",");
                    genresList = Arrays.asList(genreArray);

                }

                netflixData.setGenres(genresList);

                String runTime = csvRecord.get(7);
                if(!runTime.isEmpty())
                    netflixData.setRuntime(Integer.parseInt(runTime));

                netflixData.setType(csvRecord.get(9));
                netflixData.setTitle(csvRecord.get(8));

                String imdbScore = csvRecord.get(4);
                if(!imdbScore.isEmpty())
                    netflixData.setImdb_score(Double.parseDouble(imdbScore));

                netflixData.setAge_certification(csvRecord.get(0));

                String releaseYear = csvRecord.get(6);
                if(!releaseYear.isEmpty())
                    netflixData.setRelease_year(Integer.parseInt(releaseYear));

//                String productionCountries = csvRecord.get(5);
//                if(!productionCountries.isEmpty())
//                    netflixData.setProduction_countries(Arrays.asList(productionCountries));

                  String productionCountries = csvRecord.get(5);
                  List<String> productionCountriesList = new ArrayList<>();
                  if(!productionCountries.isEmpty() && !productionCountries.matches("[]]")){
                      productionCountries = productionCountries.replaceAll("\\[|\\]|\"", "").trim();
                      netflixData.setProduction_countries(Arrays.asList(productionCountries));
                      String[] countriesArray = productionCountries.split(",");
                      productionCountriesList = Arrays.asList(countriesArray);
                  }
                  netflixData.setProduction_countries(productionCountriesList);

                netflixDataList.add(netflixData);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return netflixDataList;

    }
}