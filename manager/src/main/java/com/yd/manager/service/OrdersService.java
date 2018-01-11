package com.yd.manager.service;

import com.yd.manager.dto.orders.OrdersDTO;
import com.yd.manager.dto.orders.OrdersDateDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.repository.OrdersRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersDTO getBetween(LocalDate begin, LocalDate end, List<Long> stores) {
        return ordersRepository.getOrdersDTO(DateRange.of(begin, end).toTimeRange(), stores);
    }

    public OrdersDTO getForAll(List<Long> stores) {
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

    private List<OrdersDateDTO> listBetween(@NonNull LocalDate begin, @NonNull LocalDate end, List<Long> stores) {
        List<OrdersDateDTO> list = new ArrayList<>();

        while (!begin.isAfter(end)) {
            OrdersDTO dto = ordersRepository.getOrdersDTO(DateRange.ofDate(begin).toTimeRange(), stores);
            list.add(OrdersDateDTO.of(begin, dto));
            begin = begin.plusDays(1);
        }

        return list;
    }

    public List<OrdersDateDTO> listForWeek(List<Long> stores) {
        DateRange week = DateRange.week();
        return listBetween(week.getBegin(), week.getEnd(), stores);
    }

    public List<OrdersDateDTO> listForRecent(List<Long> stores) {
        DateRange recent = DateRange.recent();
        return listBetween(recent.getBegin(), recent.getEnd(), stores);
    }
}
