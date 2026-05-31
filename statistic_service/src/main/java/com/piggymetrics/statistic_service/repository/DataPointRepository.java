package com.piggymetrics.statistic_service.repository;

import com.piggymetrics.statistic_service.domain.timeseries.DataPoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataPointRepository extends CrudRepository<DataPoint, String> {

    List<DataPoint> findByAccount(String account);
}