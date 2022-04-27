package com.runetsoft.demo.service.parser.impl;

import com.runetsoft.demo.model.CharacteristicModel;
import com.runetsoft.demo.model.TireModel;
import com.runetsoft.demo.service.parser.IFileParser;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FileWithTireParser implements IFileParser {

    private final static Pattern patternRunFlat =
            Pattern.compile("RunFlat|Run Flat|ROF|ZP|SSR|ZPS|HRS|RFT");
    private final static Pattern patternSeason =
            Pattern.compile("Зимние \\(шипованные\\)|Внедорожные|Летние|Зимние \\(нешипованные\\)|Всесезонные");
    private final static Pattern patternTubeType =
            Pattern.compile("TL|TT|TL/TT");
    private final static Pattern patternWidthHeightDiameter =
            Pattern.compile("\\d+/\\d+[A-Z]\\d+");
    private final static Pattern patternLoadSpeed =
            Pattern.compile("\\d+[A-Z]");
    private final static Pattern patternBrand =
            Pattern.compile("Nokian|BFGoodrich|Pirelli|Toyo|Continental|Hankook|Mitas");
    private final static Pattern patternAlphabeticCharacter =
            Pattern.compile("[a-zA-Z]");
    private final static Pattern patternNumeric =
            Pattern.compile("[0-9]+");

    @Override
    public List<TireModel> getTireModelsFromInputStream(InputStream inputStream) {
        List<TireModel> tireModels = new ArrayList<>();

        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                getTireModelFromLine(line).ifPresent(tireModels::add);
            }
        }

        return tireModels;
    }

    private Optional<TireModel> getTireModelFromLine(String line) {
        String lineCopy = line;

        TireModel tireModel = new TireModel();
        CharacteristicModel characteristicModel = new CharacteristicModel();

        int index = 0;
        TextWithIndex brandTextWithIndex = getTextWithIndexByPattern(patternBrand, lineCopy, 0);

        index = getNextIndex(index, brandTextWithIndex);
        TextWithIndex widthHeightDiameterTextWithIndex =
                getTextWithIndexByPattern(patternWidthHeightDiameter, lineCopy, index);

        index = getNextIndex(index, widthHeightDiameterTextWithIndex);
        TextWithIndex loadSpeedTextWithIndex =
                getTextWithIndexByPattern(patternLoadSpeed, lineCopy, index);

        index = getNextIndex(index, loadSpeedTextWithIndex);
        TextWithIndex runFlatTextWithIndex =
                getTextWithIndexByPattern(patternRunFlat, lineCopy, index);

        index = getNextIndex(index, runFlatTextWithIndex);
        TextWithIndex tubeTypeTextWithIndex =
                getTextWithIndexByPattern(patternTubeType, lineCopy, index);

        index = getNextIndex(index, tubeTypeTextWithIndex);
        TextWithIndex seasonTextWithIndex =
                getTextWithIndexByPattern(patternSeason, lineCopy, index);

        String model = lineCopy.substring(brandTextWithIndex.end, widthHeightDiameterTextWithIndex.start).trim();
        if (model.isEmpty())
            model = null;

        boolean isCorrect =
                allFound(brandTextWithIndex, widthHeightDiameterTextWithIndex, loadSpeedTextWithIndex, seasonTextWithIndex)
                && model != null;

        List<String> widthHeightDesignDiameter = getNumbersAndAlphabeticCharacters(widthHeightDiameterTextWithIndex.text);
        Integer width = null;
        Integer height = null;
        Integer diameter = null;
        String design = null;
        if (!widthHeightDesignDiameter.isEmpty()) {
            width = Integer.parseInt(widthHeightDesignDiameter.get(0));
            height = Integer.parseInt(widthHeightDesignDiameter.get(1));
            diameter = Integer.parseInt(widthHeightDesignDiameter.get(2));
            design = widthHeightDesignDiameter.get(3);
        }

        List<String> loadSpeed = getNumbersAndAlphabeticCharacters(loadSpeedTextWithIndex.text);
        Integer loadIndex = null;
        String speedIndex = null;
        if (!loadSpeed.isEmpty()) {
            loadIndex = Integer.parseInt(loadSpeed.get(0));
            speedIndex = loadSpeed.get(1);
        }

        String abbreviation = null;
        lineCopy = lineCopy.substring(loadSpeedTextWithIndex.end, seasonTextWithIndex.start);
        if (runFlatTextWithIndex.text != null)
            lineCopy = lineCopy.replace(runFlatTextWithIndex.text, "");
        if (tubeTypeTextWithIndex.text != null)
            lineCopy = lineCopy.replace(tubeTypeTextWithIndex.text, "");
        lineCopy = lineCopy.trim();
        if (!lineCopy.isEmpty())
            abbreviation = lineCopy;

        tireModel.setBrand(brandTextWithIndex.text);
        tireModel.setModel(model);
        tireModel.setIsCorrect(isCorrect);

        characteristicModel.setWidth(width);
        characteristicModel.setHeight(height);
        characteristicModel.setDesign(design);
        characteristicModel.setDiameter(diameter);
        characteristicModel.setLoadIndex(loadIndex);
        characteristicModel.setSpeedIndex(speedIndex);
        characteristicModel.setRunFlat(runFlatTextWithIndex.text);
        characteristicModel.setTubeType(tubeTypeTextWithIndex.text);
        characteristicModel.setAbbreviation(abbreviation);
        characteristicModel.setSeason(seasonTextWithIndex.text);

        tireModel.setCharacteristic(characteristicModel);

        if (allFieldsAreNull(tireModel)) {
            return Optional.empty();
        }

        return Optional.of(tireModel);
    }

    private boolean allFieldsAreNull(TireModel tireModel) {
        CharacteristicModel characteristicModel = tireModel.getCharacteristic();
        return tireModel.getBrand() == null &&
                tireModel.getModel() == null &&
                characteristicModel.getAbbreviation() == null &&
                characteristicModel.getWidth() == null &&
                characteristicModel.getHeight() == null &&
                characteristicModel.getDesign() == null &&
                characteristicModel.getDiameter() == null &&
                characteristicModel.getLoadIndex() == null &&
                characteristicModel.getSpeedIndex() == null &&
                characteristicModel.getSeason() == null;
    }

    static class TextWithIndex {
        private final int start;
        private final int end;
        private final String text;

        public TextWithIndex(String text, int start, int end) {
            this.start = start;
            this.end = end;
            this.text = text;
        }
    }

    private TextWithIndex getTextWithIndexByPattern(Pattern pattern, String text, Integer findFrom) throws IllegalStateException {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find(findFrom)) {
            return new TextWithIndex(matcher.group(), matcher.start(), matcher.end());
        }
        return new TextWithIndex(null, findFrom, findFrom);
    }

    private int getNextIndex(int index, TextWithIndex textWithIndex) {
        return textWithIndex.text == null ? index : textWithIndex.end;
    }

    private List<String> getNumbersAndAlphabeticCharacters(String text) {
        List<String> result = new ArrayList<>();
        if (text == null)
            return result;

        Matcher matcherNumeric = patternNumeric.matcher(text);
        Matcher matcherAlphabetic = patternAlphabeticCharacter.matcher(text);

        while(matcherNumeric.find()) {
            result.add(matcherNumeric.group());
        }
        while(matcherAlphabetic.find()) {
            result.add(matcherAlphabetic.group());
        }

        return result;
    }

    private boolean allFound(TextWithIndex... textWithIndices) {
        return Arrays.stream(textWithIndices).allMatch((el -> el.text != null));
    }
}
