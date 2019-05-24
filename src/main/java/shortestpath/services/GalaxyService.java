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
import shortestpath.exceptions.GalaxyIOException;
import shortestpath.model.Galaxy;
import shortestpath.model.SuccessIndicator;
import shortestpath.repository.GalaxyRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class GalaxyService implements GalaxyInterface {


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

    /**
     * This method persists galaxy data into Derby Database
     *
     * @return list of galaxy object
     */
    @Override
    public List<Galaxy> persistGalaxyToDerby() {

        List<Galaxy> galaxyList = readRouteMapSpreadSheet();

        logger.info("Routes Worksheet::" + routesWorkSheet);

        galaxyList.forEach(galaxy -> galaxyRepository.save(galaxy));

        return galaxyList;
    }

    @Override
    public Iterable<Galaxy> getGalaxy() {

        return galaxyRepository.findAll();
    }

    @Override
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

    /**
     * This methods returns the list of all planets
     *
     * @return
     */
    @Override
    public List<String> getAllPlanets() {

        List<String> planetNames = new ArrayList<>();
        try {
            FileInputStream excelFile = new FileInputStream(new File(routeMapFilePath));
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet planetNamesWorkSheet = workbook.getSheet(planetNamesWorksheet);

            Iterator<Row> planetNamesIterator = planetNamesWorkSheet.iterator();

            boolean firstRow = true;
            while (planetNamesIterator.hasNext()) {
                Row planetNamesRow = planetNamesIterator.next();
                if (firstRow) {
                    firstRow = false;
                } else {
                    String planetName = planetNamesRow.getCell(1).getStringCellValue();
                    planetNames.add(planetName);
                }
            }
            excelFile.close();
            workbook.close();
        } catch (IOException exception) {
            throw new GalaxyIOException("Error opening the file");
        }

        return planetNames;
    }

    private List<Galaxy> readRouteMapSpreadSheet() {

        logger.info("method called...");
        logger.info("Route Map Path:" + routeMapFilePath);
        logger.info("Planet Names:" + planetNamesWorksheet);

        List<Galaxy> galaxyList = new ArrayList<>();

        try {
            FileInputStream excelFile = new FileInputStream(new File(routeMapFilePath));

            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet planetNames = workbook.getSheet(planetNamesWorksheet);
            Sheet routes = workbook.getSheet(routesWorkSheet);
            Sheet traffic = workbook.getSheet(trafficWorkSheet);

            Iterator<Row> planetNamesIterator = planetNames.iterator();

            Map<String, String> planetNamesMap = planetNamesMap(planetNamesIterator);

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
                    String sourcePlanetName = planetNamesMap.get(sourceNode);
                    String destinationNode = routesRow.getCell(2).getStringCellValue();
                    String destinatonPlanetName = planetNamesMap.get(destinationNode);
                    double distance = routesRow.getCell(3).getNumericCellValue();
                    double trafficValue = trafficRow.getCell(3).getNumericCellValue();
                    Galaxy galaxy = new Galaxy(routeId, sourcePlanetName, destinatonPlanetName, distance, trafficValue);
                    galaxyList.add(galaxy);
                }

            }

            excelFile.close();
            workbook.close();
        } catch (IOException excelption) {
            throw new GalaxyIOException("IOEXception when opening the route map file");
        }

        return galaxyList;
    }

    public SuccessIndicator deletePlanet(Galaxy planet) {

        if (ObjectUtils.isEmpty(planet)) {
            throw new GalaxyException(GalaxyExceptionConstants.PLANET_CANNOT_BE_NULL);
        }

        galaxyRepository.delete(planet);

        SuccessIndicator successIndicator = new SuccessIndicator();
        successIndicator.setSuccessIndicatorFlag(1);
        successIndicator.setSucessMessage("Specified Planet has been removed from the galaxy");

        return successIndicator;
    }

    public Map<String, String> planetNamesMap(Iterator<Row> planetNamesIterator) {

        boolean firstRow = true;
        Map<String, String> planetsMap = new HashMap<>();
        while (planetNamesIterator.hasNext()) {

            Row planetNamesRow = planetNamesIterator.next();

            if (firstRow) {
                firstRow = false;
            } else {
                String planetNode = planetNamesRow.getCell(0).getStringCellValue();
                String planetName = planetNamesRow.getCell(1).getStringCellValue();
                planetsMap.put(planetNode, planetName);
            }
        }
        return planetsMap;
    }
}
