package com.piggymetrics.statistic_service.domain.timeseries;

import java.io.Serializable;
import java.util.Date;

public class DataPointId implements Serializable {

    private String account;
    private Date date;

    public DataPointId() {}

    public DataPointId(String account, Date date) {
        this.account = account;
        this.date = date;
    }

    public String getAccount() { return account; }
    public Date getDate() { return date; }
}
