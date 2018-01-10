package com.yd.ecabinet.service;

import com.yd.ecabinet.domain.Stock;
import com.yd.ecabinet.util.JsonUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimpleStockService implements StockService {

    private final TagService tagService;

    private Collection<Stock> stocks;

    private static Map<String, Integer> mapToPair(@NonNull Collection<Stock> stocks) {
        return stocks.stream().collect(toMap(Stock::getCode, Stock::getCount));
    }

    private static Collection<Stock> delta(Map<String, Integer> original, Map<String, Integer> current) {
        return original.entrySet().stream()
                .map(o -> Stock.of(o.getKey(), o.getValue() - Optional.ofNullable(current.get(o.getKey())).orElse(0)))
                .filter(s -> s.getCount() > 0).collect(toList());
    }

    @Override
    public Stock fromEntry(Map.Entry<String, Integer> entry) {
        return Stock.of(entry.getKey(), entry.getValue());
    }

    @Override
    public void setStocks(Collection<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public Collection<Stock> fromMap(Map<String, Integer> map) {
        return map.entrySet().stream().map(this::fromEntry).collect(toList());
    }

    @Override
    public Collection<Stock> getStocks() {
        return this.stocks;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Stock> getRemainStocks() {
        String json = tagService.read();
        return fromMap(JsonUtils.parseJson(json, Map.class));
    }

    @Override
    public Collection<Stock> getDeltaStocks() {
        Collection<Stock> remainStocks = this.getRemainStocks();

        Map<String, Integer> original = mapToPair(this.getStocks());
        Map<String, Integer> current = mapToPair(remainStocks);

        Collection<Stock> delta = delta(original, current);

        logger.debug("原库存:{}", original);
        logger.debug("现库存:{}", current);
        logger.debug("订单:{}", delta);

        this.setStocks(remainStocks);
        return delta;
    }

}
