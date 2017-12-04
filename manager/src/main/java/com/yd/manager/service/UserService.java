package com.yd.manager.service;

import com.yd.manager.dto.UserOrdersCollectByDateDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public UserOrdersCollectByDateDTO getForToday(long userId, List<Long> stores) {
        return userRepository.getUserOrderCollectByDateDTO(userId, LocalDate.now(), stores);
    }

    public List<UserOrdersCollectByDateDTO> listForWeek(long userId, List<Long> stores) {
        return this.listForDateRange(userId, DateRange.week(), stores);
    }

    private List<UserOrdersCollectByDateDTO> listForDateRange(long userId, DateRange dateRange, List<Long> stores) {
        LocalDate begin = dateRange.getBegin();
        LocalDate end = dateRange.getEnd();

        //此处查询必须为闭区间
        return begin != null && end != null ? this.listForDateRange(userId, begin, end, stores) : null;
    }

    public List<UserOrdersCollectByDateDTO> listForDateRange(long userId, @NonNull LocalDate begin, @NonNull LocalDate end, List<Long> stores) {
        List<UserOrdersCollectByDateDTO> list = new ArrayList<>();

        while (!begin.isAfter(end)) {
            Optional.ofNullable(userRepository.getUserOrderCollectByDateDTO(userId, begin, stores)).ifPresent(list::add);
            begin = begin.plusDays(1);
        }

        return list;
    }

    public List<UserOrdersCollectByDateDTO> listForMonth(long userId, List<Long> stores) {
        return this.listForDateRange(userId, DateRange.month(), stores);
    }

    public List<UserOrdersCollectByDateDTO> listForSeason(long userId, List<Long> stores) {
        return this.listForDateRange(userId, DateRange.season(), stores);
    }
}