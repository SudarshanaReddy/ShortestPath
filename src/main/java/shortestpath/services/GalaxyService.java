package shortestpath.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import shortestpath.constants.GalaxyExceptionConstants;
import shortestpath.exceptions.GalaxyException;
import shortestpath.model.Galaxy;
import shortestpath.model.Planet;
import shortestpath.model.SuccessIndicator;
import shortestpath.repository.GalaxyRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class GalaxyService implements GalaxyInterface{


    private static final Logger logger = LogManager.getLogger();
    private GalaxyRepository galaxyRepository;

    @Value("${pathto.routemap.file}")
    private String routeMapFilePath;

    @Value("${routes.worksheet}")
    private String routesWorkSheet;

    @Value("${traffic.worksheet}")
    private String trafficWorkSheet;

    @Value("${planetnames.worksheet}")
    private String planetNamesWorksheet;

    @Autowired
    public GalaxyService(GalaxyRepository galaxyRepository) {

        this.galaxyRepository = galaxyRepository;
    }

    @Override
    public List<Galaxy> persistGalaxyToDerby() {

        List<Galaxy> galaxyList = readRouteMapSpreadSheet();

        logger.info("Routes Worksheet::"+routesWorkSheet);

        galaxyList.forEach(galaxy -> galaxyRepository.save(galaxy));

        return galaxyList;
    }

    public Iterable<Galaxy> getGalaxy() {

        return galaxyRepository.findAll();
    }

    public SuccessIndicator addPlanet(Galaxy planet) {

        SuccessIndicator successIndicator;

        Galaxy galaxyAdded = galaxyRepository.save(planet);

        if (galaxyAdded == null) {
            throw new GalaxyException(GalaxyExceptionConstants.ADD_GALAXY_EXCEPTION);
        } else {
            successIndicator = new SuccessIndicator();
            successIndicator.setSuccessIndicatorFlag(HttpStatus.CREATED.value());
            successIndicator.setSucessMessage("Planet has been successfully added to galaxy");
        }

        return successIndicator;
    }

    private List<Galaxy> readRouteMapSpreadSheet() {

        logger.info("method called...");
        logger.info("Route Map Path:"+routeMapFilePath);
        logger.info("Planet Names:"+planetNamesWorksheet);

        List<Galaxy> galaxyList = new ArrayList<>();

        try {
            FileInputStream excelFile = new FileInputStream(new File(routeMapFilePath));
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet routes = workbook.getSheet(routesWorkSheet);
            Sheet traffic = workbook.getSheet(trafficWorkSheet);

            Iterator<Row> routesIterator = routes.rowIterator();

            Iterator<Row> trafficIterator = traffic.rowIterator();


            boolean firstRowFlag = true;

            while (routesIterator.hasNext() && trafficIterator.hasNext()) {

                Row routesRow = routesIterator.next();
                Row trafficRow = trafficIterator.next();
                if (firstRowFlag) {
                    firstRowFlag = false;
                } else {
                    double routeId = routesRow.getCell(0).getNumericCellValue();
                    String sourceNode = routesRow.getCell(1).getStringCellValue();
                    String destinationNode = routesRow.getCell(2).getStringCellValue();
                    double distance = routesRow.getCell(3).getNumericCellValue();
                    double trafficValue = trafficRow.getCell(3).getNumericCellValue();
                    Galaxy galaxy = new Galaxy(routeId, sourceNode,destinationNode,distance,trafficValue);
                    galaxyList.add(galaxy);
                }

            }
        }catch (IOException e) {
            logger.error("IOException When opening the Route Map");
        }

        return galaxyList;
    }

    public SuccessIndicator deletePlanet(Galaxy planet) {

        if(ObjectUtils.isEmpty(planet)){
            throw new GalaxyException(GalaxyExceptionConstants.PLANET_CANNOT_BE_NULL);
        }

        galaxyRepository.delete(planet);

        SuccessIndicator successIndicator = new SuccessIndicator();
        successIndicator.setSuccessIndicatorFlag(1);
        successIndicator.setSucessMessage("Specified Planet has been removed from the galaxy");

        return successIndicator;
    }
}
