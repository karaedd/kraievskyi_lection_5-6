package com.kraievskyi.lection.task1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kraievskyi.lection.task1.model.TotalFine;
import com.kraievskyi.lection.task1.model.TotalFines;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ReadWrite {
    private static final String OUTPUT_FILE_PATH = "src/main/resources/task1/out/output.xml";
    private static final String INPUT_FILE_PATH = "src/main/resources/task1/input";

    public static File[] getFiles() {
        File file = new File(INPUT_FILE_PATH);
        return file.listFiles((dir, name) -> name.endsWith(".json"));
    }

    public List<TotalFine> readFiles(File file) {
        byte[] data;
        TotalFine[] totalFines;
        List<TotalFine> totalFineList;
        try {
            data = Files.readAllBytes(Paths.get(String.valueOf(file)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file", e);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            totalFines = mapper.readValue(data, TotalFine[].class);
        } catch (IOException e) {
            throw new RuntimeException("Can't map data", e);
        }
        totalFineList = Arrays.asList(totalFines);
        return totalFineList;
    }

    public void writeFileToXml(TotalFines totalFines) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TotalFines.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(totalFines, new File(OUTPUT_FILE_PATH));
        } catch (JAXBException e) {
            throw new RuntimeException("Can't write data to xml", e);
        }
    }
}
