package com.piggymetrics.statistic_service.repository.converter;

import com.piggymetrics.statistic_service.domain.timeseries.DataPointId;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DataPointIdWriterConverter implements Converter<DataPointId, Document> {

    @Override
    public Document convert(DataPointId id) {
        Document document = new Document();
        document.put("account", id.getAccount());
        document.put("date", id.getDate());
        return document;
    }
}
