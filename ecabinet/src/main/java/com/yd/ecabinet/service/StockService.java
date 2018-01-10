package com.yd.ecabinet.service;

import com.yd.ecabinet.domain.Stock;
import lombok.NonNull;

import java.util.Collection;
import java.util.Map;

public interface StockService {

    Stock fromEntry(@NonNull Map.Entry<String, Integer> entry);

    Collection<Stock> fromMap(@NonNull Map<String, Integer> map);

    Collection<Stock> getStocks();

    void setStocks(@NonNull Collection<Stock> stocks);

    Collection<Stock> getRemainStocks();

    Collection<Stock> getDeltaStocks();

}
