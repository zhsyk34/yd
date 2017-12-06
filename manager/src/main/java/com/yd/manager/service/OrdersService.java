package com.yd.manager.service;

import com.yd.manager.dto.OrdersDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersDTO getForDateRange(LocalDate begin, LocalDate end, List<Long> stores) {
        return ordersRepository.getOrdersDTO(DateRange.of(begin, end).toTimeRange(), stores);
    }

    public OrdersDTO getUntilNow(List<Long> stores) {
        return ordersRepository.getOrdersDTO(null, stores);
    }

    public OrdersDTO getForToday(List<Long> stores) {
        return ordersRepository.getOrdersDTO(DateRange.today().toTimeRange(), stores);
    }

    public OrdersDTO getForWeek(List<Long> stores) {
        return ordersRepository.getOrdersDTO(DateRange.week().toTimeRange(), stores);
    }

    public OrdersDTO getForMonth(List<Long> stores) {
        return ordersRepository.getOrdersDTO(DateRange.month().toTimeRange(), stores);
    }

    public OrdersDTO getForSeason(List<Long> stores) {
        return ordersRepository.getOrdersDTO(DateRange.season().toTimeRange(), stores);
    }

}
