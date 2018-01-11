package com.yd.manager.service;

import com.yd.manager.dto.record.*;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.TimeRange;
import lombok.NonNull;

import java.util.List;

public interface AccessRecordService {

    AccessRecordDTO getAccessRecordDTO(TimeRange timeRange, List<Long> stores);

    UserAccessRecordDTO getUserAccessRecordDTO(long userId, TimeRange timeRange, List<Long> stores);

    List<UserAccessRecordDTO> listUserAccessRecordDTO(@NonNull List<Long> users, TimeRange timeRange, List<Long> stores);

    StoreAccessRecordDTO getStoreAccessRecordDTO(long storeId, TimeRange timeRange);

    List<StoreAccessRecordDTO> listStoreAccessRecordDTO(TimeRange timeRange, List<Long> stores);

    List<UserStoreAccessRecordDTO> listUserStoreAccessRecordDTO(long userId, TimeRange timeRange, List<Long> stores);

    List<AccessRecordDateDTO> listBetweenByStores(List<Long> stores, DateRange dateRange);

    List<AccessRecordDateDTO> listForRecentByStores(List<Long> stores);

    List<AccessRecordDateDTO> listForWeekByStores(List<Long> stores);

    List<AccessRecordDateDTO> listForMonthByStores(List<Long> stores);

    List<AccessRecordDateDTO> listForSeasonByStores(List<Long> stores);

    List<AccessRecordDateDTO> listBetweenByStore(long storeId, DateRange dateRange);

    List<AccessRecordDateDTO> listForRecentByStore(long storeId);

    List<AccessRecordDateDTO> listForWeekByStore(long storeId);

    List<AccessRecordDateDTO> listForMonthByStore(long storeId);

    List<AccessRecordDateDTO> listForSeasonByStore(long storeId);

    List<AccessRecordDateDTO> listBetweenByUser(long userId, DateRange dateRange, List<Long> stores);

    List<AccessRecordDateDTO> listForRecentByUser(long userId, List<Long> stores);

    List<AccessRecordDateDTO> listForWeekByUser(long userId, List<Long> stores);

    List<AccessRecordDateDTO> listForMonthByUser(long userId, List<Long> stores);

    List<AccessRecordDateDTO> listForSeasonByUser(long userId, List<Long> stores);

}
