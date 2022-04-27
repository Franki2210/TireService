package com.runetsoft.demo.service;

import com.runetsoft.demo.error.ServiceException;
import com.runetsoft.demo.model.TireModel;
import com.runetsoft.demo.repository.TireCrudRepository;
import com.runetsoft.demo.service.parser.IFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class TireService {

    private final TireCrudRepository tireCrudRepository;
    private final IFileParser fileParser;

    @Autowired
    public TireService(TireCrudRepository tireCrudRepository, IFileParser fileParser) {
        this.tireCrudRepository = tireCrudRepository;
        this.fileParser = fileParser;
    }

    public void saveTiresFromFile(MultipartFile multipartFile) throws ServiceException {
        List<TireModel> tireModels;

        if (multipartFile.isEmpty())
            throw new ServiceException("MultipartFile is empty");

        InputStream fileStream;
        try {
            fileStream = multipartFile.getInputStream();
            tireModels = fileParser.getTireModelsFromInputStream(fileStream);
        } catch (IOException e) {
            throw new ServiceException("Error when get input stream");
        }

        if (tireModels != null && !tireModels.isEmpty()) {
            tireCrudRepository.saveAll(tireModels);
        } else {
            throw new ServiceException("No tires in file");
        }
    }

    public List<TireModel> getAll() {
        List<TireModel> result = new ArrayList<>();
        tireCrudRepository.findAll().forEach(result::add);
        return result;
    }

}
