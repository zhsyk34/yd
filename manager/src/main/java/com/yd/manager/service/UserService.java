package com.yd.manager.service;

import com.yd.manager.dto.DateRange;
import com.yd.manager.dto.UserOrderCollectByDateDTO;
import com.yd.manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public List<UserOrderCollectByDateDTO> listForDateRange(long userId, LocalDate begin, LocalDate end, List<Long> stores) {
        List<UserOrderCollectByDateDTO> list = new ArrayList<>();

        while (!begin.isAfter(end)) {
            list.add(userRepository.getUserOrderCollectByDateDTO(userId, begin, stores));
            begin = begin.plusDays(1);
        }

        return list;
    }

    public UserOrderCollectByDateDTO getForToday(long userId, List<Long> stores) {
        return userRepository.getUserOrderCollectByDateDTO(userId, LocalDate.now(), stores);
    }

    public List<UserOrderCollectByDateDTO> listForWeek(long userId, List<Long> stores) {
        return this.listForDateRange(userId, DateRange.week(), stores);
    }

    public List<UserOrderCollectByDateDTO> listForMonth(long userId, List<Long> stores) {
        return this.listForDateRange(userId, DateRange.month(), stores);
    }

    public List<UserOrderCollectByDateDTO> listForSeason(long userId, List<Long> stores) {
        return this.listForDateRange(userId, DateRange.season(), stores);
    }

    private List<UserOrderCollectByDateDTO> listForDateRange(long userId, DateRange dateRange, List<Long> stores) {
        LocalDate begin = dateRange.getBegin();
        LocalDate end = dateRange.getEnd();

        //此处查询必须为闭区间
        return begin != null && end != null ? this.listForDateRange(userId, begin, end, stores) : null;
    }
}
