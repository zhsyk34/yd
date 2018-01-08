package com.yd.manager.service;

import com.yd.manager.dto.record.*;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.TimeRange;
import lombok.NonNull;

import java.util.List;

public interface AccessRecordService {

    //所有访问量
    AccessRecordDTO getAccessRecordDTO(TimeRange timeRange, List<Long> stores);

    //用户所有访问量
    UserAccessRecordDTO getUserAccessRecordDTO(long userId, TimeRange timeRange, List<Long> stores);

    //用户组所有访问量
    List<UserAccessRecordDTO> listUserAccessRecordDTO(@NonNull List<Long> users, TimeRange timeRange, List<Long> stores);

    //店铺所有访问量
    StoreAccessRecordDTO getStoreAccessRecordDTO(long storeId, TimeRange timeRange);

    //店铺组所有访问量
    List<StoreAccessRecordDTO> listStoreAccessRecordDTO(TimeRange timeRange, List<Long> stores);

    //用户在店铺的访问量
    //UserStoreAccessRecordDTO getUserStoreAccessRecordDTO(long userId, long storeId, TimeRange timeRange);

    //用户在各店铺访问量
    List<UserStoreAccessRecordDTO> listUserStoreAccessRecordDTO(long userId, TimeRange timeRange, List<Long> stores);

    /*所有店铺*/
    List<AccessRecordDateDTO> listBetweenByStores(List<Long> stores, DateRange dateRange);

    List<AccessRecordDateDTO> listForRecentByStores(List<Long> stores);

    List<AccessRecordDateDTO> listForWeekByStores(List<Long> stores);

    List<AccessRecordDateDTO> listForMonthByStores(List<Long> stores);

    List<AccessRecordDateDTO> listForSeasonByStores(List<Long> stores);

    /*根据店铺*/
    List<AccessRecordDateDTO> listBetweenByStore(long storeId, DateRange dateRange);

    List<AccessRecordDateDTO> listForRecentByStore(long storeId);

    List<AccessRecordDateDTO> listForWeekByStore(long storeId);

    List<AccessRecordDateDTO> listForMonthByStore(long storeId);

    List<AccessRecordDateDTO> listForSeasonByStore(long storeId);

    /*根据用户*/
    List<AccessRecordDateDTO> listBetweenByUser(long userId, DateRange dateRange, List<Long> stores);

    List<AccessRecordDateDTO> listForRecentByUser(long userId, List<Long> stores);

    List<AccessRecordDateDTO> listForWeekByUser(long userId, List<Long> stores);

    List<AccessRecordDateDTO> listForMonthByUser(long userId, List<Long> stores);

    List<AccessRecordDateDTO> listForSeasonByUser(long userId, List<Long> stores);

}
