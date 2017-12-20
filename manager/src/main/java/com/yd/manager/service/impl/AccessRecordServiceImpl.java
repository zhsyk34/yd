package com.yd.manager.service.impl;

import com.yd.manager.dto.*;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.TimeRange;
import com.yd.manager.repository.AccessRecordRepository;
import com.yd.manager.service.AccessRecordService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessRecordServiceImpl implements AccessRecordService {

    private final AccessRecordRepository accessRecordRepository;

    @Override
    public AccessRecordDTO getAccessRecordDTO(TimeRange timeRange, List<Long> stores) {
        long enter = accessRecordRepository.countEnter(timeRange, stores);
        long entrant = accessRecordRepository.countEntrant(timeRange, stores);
        return AccessRecordDTO.from(enter, entrant);
    }

    @Override
    public UserAccessRecordDTO getUserAccessRecordDTO(long userId, TimeRange timeRange, List<Long> stores) {
        AccessRecordCount enter = accessRecordRepository.countEnterByUser(userId, timeRange, stores);
        AccessRecordCount entrant = accessRecordRepository.countEntrantByUser(userId, timeRange, stores);
        return UserAccessRecordDTO.from(userId, enter.getCount(), entrant.getCount());
    }

    @Override
    public List<UserAccessRecordDTO> listUserAccessRecordDTO(List<Long> users, TimeRange timeRange, List<Long> stores) {
        List<AccessRecordCount> enters = accessRecordRepository.listCountEnterGroupByUser(users, timeRange, stores);

        if (CollectionUtils.isEmpty(enters)) {
            return null;
        }

        List<AccessRecordCount> entrants = accessRecordRepository.listCountEntrantGroupByUser(users, timeRange, stores);

        Map<Long, Long> enterMap = enters.stream().collect(toMap(AccessRecordCount::getUserOrStoreId, AccessRecordCount::getCount));
        Map<Long, Long> entrantMap = entrants.stream().collect(toMap(AccessRecordCount::getUserOrStoreId, AccessRecordCount::getCount));

        return enterMap.entrySet().stream().collect(
                ArrayList::new,
                (result, entry) -> {
                    long userId = entry.getKey();
                    result.add(UserAccessRecordDTO.from(userId, entry.getValue(), Optional.ofNullable(entrantMap.get(userId)).orElse(0L)));
                },
                List::addAll
        );
    }

    @Override
    public StoreAccessRecordDTO getStoreAccessRecordDTO(long storeId, TimeRange timeRange) {
        AccessRecordCount enter = accessRecordRepository.countEnterByStore(storeId, timeRange);
        AccessRecordCount entrant = accessRecordRepository.countEntrantByStore(storeId, timeRange);
        return StoreAccessRecordDTO.from(storeId, enter.getCount(), entrant.getCount());
    }

    @Override
    public List<StoreAccessRecordDTO> listStoreAccessRecordDTO(TimeRange timeRange, List<Long> stores) {
        List<AccessRecordCount> enters = accessRecordRepository.listCountEnterGroupByStore(timeRange, stores);

        if (CollectionUtils.isEmpty(enters)) {
            return null;
        }

        List<AccessRecordCount> entrants = accessRecordRepository.listCountEntrantGroupByStore(timeRange, stores);

        Map<Long, Long> enterMap = enters.stream().collect(toMap(AccessRecordCount::getUserOrStoreId, AccessRecordCount::getCount));
        Map<Long, Long> entrantMap = entrants.stream().collect(toMap(AccessRecordCount::getUserOrStoreId, AccessRecordCount::getCount));

        return enterMap.entrySet().stream().collect(
                ArrayList::new,
                (result, entry) -> {
                    long storeId = entry.getKey();
                    result.add(StoreAccessRecordDTO.from(storeId, entry.getValue(), Optional.ofNullable(entrantMap.get(storeId)).orElse(0L)));
                },
                List::addAll
        );
    }

    @Override
    public List<UserStoreAccessRecordDTO> listUserStoreAccessRecordDTO(long userId, TimeRange timeRange, List<Long> stores) {
        List<AccessRecordCount> enters = accessRecordRepository.listEnterByUserAndGroupByStore(userId, timeRange, stores);

        if (CollectionUtils.isEmpty(enters)) {
            return null;
        }

        List<AccessRecordCount> entrants = accessRecordRepository.listEntrantByUserAndGroupByStore(userId, timeRange, stores);

        Map<Long, Long> enterMap = enters.stream().collect(toMap(AccessRecordCount::getUserOrStoreId, AccessRecordCount::getCount));
        Map<Long, Long> entrantMap = entrants.stream().collect(toMap(AccessRecordCount::getUserOrStoreId, AccessRecordCount::getCount));

        return enterMap.entrySet().stream().collect(
                ArrayList::new,
                (result, entry) -> {
                    long storeId = entry.getKey();
                    result.add(UserStoreAccessRecordDTO.from(userId, storeId, entry.getValue(), Optional.ofNullable(entrantMap.get(storeId)).orElse(0L)));
                },
                List::addAll
        );
    }

    @Override
    public List<AccessRecordDateDTO> listBetweenByStores(List<Long> stores, DateRange dateRange) {
        LocalDate begin = dateRange.getBegin();
        LocalDate end = dateRange.getEnd();
        return begin != null && end != null ? this.listBetweenByStores(stores, begin, end) : null;
    }

    @Override
    public List<AccessRecordDateDTO> listForRecentByStores(List<Long> stores) {
        return this.listBetweenByStores(stores, DateRange.recent());
    }

    @Override
    public List<AccessRecordDateDTO> listForWeekByStores(List<Long> stores) {
        return this.listBetweenByStores(stores, DateRange.week());
    }

    @Override
    public List<AccessRecordDateDTO> listForMonthByStores(List<Long> stores) {
        return this.listBetweenByStores(stores, DateRange.month());
    }

    @Override
    public List<AccessRecordDateDTO> listForSeasonByStores(List<Long> stores) {
        return this.listBetweenByStores(stores, DateRange.season());
    }

    @Override
    public List<AccessRecordDateDTO> listBetweenByStore(long storeId, DateRange dateRange) {
        LocalDate begin = dateRange.getBegin();
        LocalDate end = dateRange.getEnd();
        return begin != null && end != null ? this.listBetweenByStore(storeId, begin, end) : null;
    }

    @Override
    public List<AccessRecordDateDTO> listForRecentByStore(long storeId) {
        return this.listBetweenByStore(storeId, DateRange.recent());
    }

    @Override
    public List<AccessRecordDateDTO> listForWeekByStore(long storeId) {
        return this.listBetweenByStore(storeId, DateRange.week());
    }

    @Override
    public List<AccessRecordDateDTO> listForMonthByStore(long storeId) {
        return this.listBetweenByStore(storeId, DateRange.month());
    }

    @Override
    public List<AccessRecordDateDTO> listForSeasonByStore(long storeId) {
        return this.listBetweenByStore(storeId, DateRange.season());
    }

    @Override
    public List<AccessRecordDateDTO> listBetweenByUser(long userId, DateRange dateRange, List<Long> stores) {
        LocalDate begin = dateRange.getBegin();
        LocalDate end = dateRange.getEnd();
        return begin != null && end != null ? this.listBetweenByUser(userId, begin, end, stores) : null;
    }

    @Override
    public List<AccessRecordDateDTO> listForRecentByUser(long userId, List<Long> stores) {
        return this.listBetweenByUser(userId, DateRange.recent(), stores);
    }

    @Override
    public List<AccessRecordDateDTO> listForWeekByUser(long userId, List<Long> stores) {
        return this.listBetweenByUser(userId, DateRange.week(), stores);
    }

    @Override
    public List<AccessRecordDateDTO> listForMonthByUser(long userId, List<Long> stores) {
        return this.listBetweenByUser(userId, DateRange.month(), stores);
    }

    @Override
    public List<AccessRecordDateDTO> listForSeasonByUser(long userId, List<Long> stores) {
        return this.listBetweenByUser(userId, DateRange.season(), stores);
    }

    private List<AccessRecordDateDTO> listBetweenByStore(long storeId, @NonNull LocalDate begin, @NonNull LocalDate end) {
        List<AccessRecordDateDTO> list = new ArrayList<>();

        while (!begin.isAfter(end)) {
            AccessRecordDTO dto = this.getStoreAccessRecordDTO(storeId, DateRange.ofDate(begin).toTimeRange());
            list.add(AccessRecordDateDTO.from(begin, dto));
            begin = begin.plusDays(1);
        }

        return list;
    }

    private List<AccessRecordDateDTO> listBetweenByStores(List<Long> stores, @NonNull LocalDate begin, @NonNull LocalDate end) {
        List<AccessRecordDateDTO> list = new ArrayList<>();

        while (!begin.isAfter(end)) {
            AccessRecordDTO dto = this.getAccessRecordDTO(DateRange.ofDate(begin).toTimeRange(), stores);
            list.add(AccessRecordDateDTO.from(begin, dto));
            begin = begin.plusDays(1);
        }

        return list;
    }

    private List<AccessRecordDateDTO> listBetweenByUser(long userId, @NonNull LocalDate begin, @NonNull LocalDate end, List<Long> stores) {
        List<AccessRecordDateDTO> list = new ArrayList<>();

        while (!begin.isAfter(end)) {
            AccessRecordDTO dto = this.getUserAccessRecordDTO(userId, DateRange.ofDate(begin).toTimeRange(), stores);
            list.add(AccessRecordDateDTO.from(begin, dto));
            begin = begin.plusDays(1);
        }

        return list;
    }
}
