package com.piggymetrics.statistic_service.repository.converter;

import com.piggymetrics.statistic_service.domain.timeseries.DataPointId;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataPointIdReaderConverter implements Converter<Document, DataPointId> {

    @Override
    public DataPointId convert(Document document) {
        String account = document.getString("account");
        Date date = document.getDate("date");
        return new DataPointId(account, date);
    }
}
