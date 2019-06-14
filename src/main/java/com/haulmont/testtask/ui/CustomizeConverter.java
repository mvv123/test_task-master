package com.haulmont.testtask.ui;

import com.haulmont.testtask.services.PropertiesService;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import java.util.Properties;

/**
 * Класс для конвертации String в Integer и наоборот
 */
public class CustomizeConverter implements Converter<String, Integer> {

    private Properties mainProperties;

    public CustomizeConverter() {
        mainProperties = PropertiesService.getInstance().getMainProperties();
    }

    @Override
    public Result<Integer> convertToModel(String fieldValue, ValueContext context) {
        try {
            return Result.ok(fieldValue == null ? 0 : Integer.valueOf(fieldValue));
        } catch (NumberFormatException e) {
            return Result.error(mainProperties.getProperty("error.digital"));
        }
    }

    @Override
    public String convertToPresentation(Integer integer, ValueContext context) {
        return integer == null ? "" : String.valueOf(integer);
    }

}
