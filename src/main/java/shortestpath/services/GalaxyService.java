package shortestpath.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shortestpath.constants.GalaxyExceptionConstants;
import shortestpath.exceptions.GalaxyException;
import shortestpath.model.Galaxy;
import shortestpath.model.SuccessIndicator;
import shortestpath.repository.GalaxyRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class GalaxyService {


    private static final Logger logger = LogManager.getLogger();
    private GalaxyRepository galaxyRepository;

    @Autowired
    public GalaxyService(GalaxyRepository galaxyRepository) {
        this.galaxyRepository = galaxyRepository;
    }

    public List<Galaxy> persistGalaxyToDB() {

        String path = "src\\main\\resources\\routes-map.xlsx";

        FileInputStream excelFile = null;
        Workbook workbook = null;
        try {
            excelFile = new FileInputStream(new File(path));
            workbook = new XSSFWorkbook(excelFile);
        } catch (IOException e) {
            logger.error("IOException When opening the Route Map");
        }

        Sheet planetNames = workbook.getSheet("Planet Names");
        Sheet routes = workbook.getSheet("Routes");
        Sheet traffic = workbook.getSheet("Traffic");

        Iterator<Row> routesIterator = routes.rowIterator();

        Iterator<Row> trafficIterator = traffic.rowIterator();

        Iterator<Row> planetNamesIterator = planetNames.iterator();

        List<Galaxy> galaxyList = new ArrayList<>();

        while (routesIterator.hasNext() && trafficIterator.hasNext() && planetNamesIterator.hasNext()) {
            Row routesRow = routesIterator.next();
            Row trafficRow = trafficIterator.next();

            double routeId = routesRow.getCell(0).getNumericCellValue();
            String sourceNode = routesRow.getCell(1).getStringCellValue();
            String destinationNode = routesRow.getCell(2).getStringCellValue();
            double distance = routesRow.getCell(3).getNumericCellValue();
            double trafficValue = trafficRow.getCell(3).getNumericCellValue();
            Galaxy galaxy = new Galaxy(routeId, sourceNode, destinationNode, distance, trafficValue);
            galaxyList.add(galaxy);
            galaxyRepository.save(galaxy);

        }

        return galaxyList;
    }

    public Iterable<Galaxy> getGalaxy() {

        return galaxyRepository.findAll();
    }

    public SuccessIndicator addPlanet(Galaxy planet) {

        SuccessIndicator successIndicator;

        Galaxy galaxyAdded = galaxyRepository.save(planet);

        if (galaxyAdded == null) {
            throw new GalaxyException(GalaxyExceptionConstants.addGalaxyException);
        } else {
            successIndicator = new SuccessIndicator();
            successIndicator.setSuccessIndicator(HttpStatus.CREATED.value());
            successIndicator.setSucessMessage("Planet has been successfully added to galaxy");
        }

        return successIndicator;
    }

}
