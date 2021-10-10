package br.edu.ifba.mac.check4j.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.edu.ifba.mac.check4j.model.Plate;
import br.edu.ifba.mac.check4j.model.Result;

@Service
public class VerifierService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VerifierService.class);

    public String verifyAll() {
        File datasetDir = new File("/home/mauricio/Code/projects/anpr-auth/dataset/");

        LOGGER.info(datasetDir.getAbsolutePath());
        LOGGER.info(Boolean.toString(datasetDir.exists()));

        for(File file : datasetDir.listFiles()) {
            verify(file);
        }

        return "done.";
    }

    private void verify(File file) {
        try {
            Result result = AlprResultFactory.build(file);

            if(result!=null && !result.getResults().isEmpty()) {
                saveResult(file, result);
            } else {
                LOGGER.info("No results");
            }

        } catch(Exception e) {
            LOGGER.info(e.getLocalizedMessage());
        }
    }

    private void saveResult(File file, Result result) throws IOException {
        Plate plate = result.getResults().get(0);
        String plateNumber = plate != null ? plate.getPlate() : "";

        LOGGER.info(plateNumber);

        Path source = file.toPath();
        Path target = new File("/home/mauricio/Code/projects/anpr-auth/dataset/verified/" + file.getName().replace(".jpg", plateNumber+".jpg").replace(".jpeg", plateNumber+".jpeg").replace(".png", plateNumber+".png")).toPath();

        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
    
}
