package com.yd.manager.service;

import com.yd.manager.dto.orders.UserOrdersDTO;
import com.yd.manager.dto.orders.UserOrdersDateDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.repository.UserRepository;
import com.yd.manager.util.TimeUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public Page<UserOrdersDTO> pageUserOrdersDTO(String nameOrPhone, LocalDate begin, LocalDate end, List<Long> stores, Pageable pageable) {
        return userRepository.pageUserOrdersDTO(nameOrPhone, DateRange.of(begin, end).toTimeRange(), stores, pageable);
    }

    public UserOrdersDateDTO getForToday(long userId, List<Long> stores) {
        return userRepository.getUserOrdersDateDTO(userId, LocalDate.now(), stores);
    }

    private List<UserOrdersDateDTO> listBetween(long userId, DateRange dateRange, List<Long> stores) {
        LocalDate begin = dateRange.getBegin();
        LocalDate end = dateRange.getEnd();

        return begin != null && end != null ? this.listBetween(userId, begin, end, stores) : null;
    }

    public List<UserOrdersDateDTO> listBetween(long userId, @NonNull LocalDate begin, @NonNull LocalDate end, List<Long> stores) {
        List<UserOrdersDateDTO> list = new ArrayList<>();

        while (!begin.isAfter(end)) {
            UserOrdersDateDTO dto = userRepository.getUserOrdersDateDTO(userId, begin, stores);
            list.add(Optional.ofNullable(dto).orElse(UserOrdersDateDTO.of(TimeUtils.format(begin), userId)));
            begin = begin.plusDays(1);
        }

        return list;
    }

    public List<UserOrdersDateDTO> listForRecent(long userId, List<Long> stores) {
        return this.listBetween(userId, DateRange.recent(), stores);
    }

    public List<UserOrdersDateDTO> listForWeek(long userId, List<Long> stores) {
        return this.listBetween(userId, DateRange.week(), stores);
    }

    public List<UserOrdersDateDTO> listForMonth(long userId, List<Long> stores) {
        return this.listBetween(userId, DateRange.month(), stores);
    }

    public List<UserOrdersDateDTO> listForSeason(long userId, List<Long> stores) {
        return this.listBetween(userId, DateRange.season(), stores);
    }

    public long countRange(LocalDate begin, LocalDate end, List<Long> stores) {
        return userRepository.countByCreateTime(DateRange.of(begin, end).toTimeRange(), stores);
    }

    public long countToday(List<Long> stores) {
        return userRepository.countByCreateTime(DateRange.today().toTimeRange(), stores);
    }

    public List<Long> countRecent(List<Long> stores) {
        return Arrays.asList(
                userRepository.countByCreateTime(DateRange.ofDate(LocalDate.now().minusDays(1)).toTimeRange(), stores),
                userRepository.countByCreateTime(DateRange.today().toTimeRange(), stores)
        );
    }
}
