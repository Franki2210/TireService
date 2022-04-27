package com.runetsoft.demo.service.parser;

import com.runetsoft.demo.model.TireModel;

import java.io.InputStream;
import java.util.List;

public interface IFileParser {
    List<TireModel> getTireModelsFromInputStream(InputStream inputStream);
}
