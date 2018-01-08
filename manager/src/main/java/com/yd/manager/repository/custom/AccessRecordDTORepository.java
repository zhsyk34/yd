package com.yd.manager.repository.custom;

import com.yd.manager.dto.record.AccessRecordCount;
import com.yd.manager.dto.util.TimeRange;
import lombok.NonNull;

import java.util.List;

public interface AccessRecordDTORepository {

    long countEnter(TimeRange timeRange, List<Long> stores);

    long countEntrant(TimeRange timeRange, List<Long> stores);

    long countValid(TimeRange timeRange, List<Long> stores);

    AccessRecordCount countEnterByUser(@NonNull long userId, TimeRange timeRange, List<Long> stores);

    AccessRecordCount countEntrantByUser(@NonNull long userId, TimeRange timeRange, List<Long> stores);

    AccessRecordCount countValidByUser(@NonNull long userId, TimeRange timeRange, List<Long> stores);

    List<AccessRecordCount> listCountEnterGroupByUser(@NonNull List<Long> users, TimeRange timeRange, List<Long> stores);

    List<AccessRecordCount> listCountEntrantGroupByUser(@NonNull List<Long> users, TimeRange timeRange, List<Long> stores);

    List<AccessRecordCount> listCountValidGroupByUser(@NonNull List<Long> users, TimeRange timeRange, List<Long> stores);

    AccessRecordCount countEnterByStore(@NonNull long storeId, TimeRange timeRange);

    AccessRecordCount countEntrantByStore(@NonNull long storeId, TimeRange timeRange);

    AccessRecordCount countValidByStore(@NonNull long storeId, TimeRange timeRange);

    List<AccessRecordCount> listCountEnterGroupByStore(TimeRange timeRange, List<Long> stores);

    List<AccessRecordCount> listCountEntrantGroupByStore(TimeRange timeRange, List<Long> stores);

    List<AccessRecordCount> listCountValidGroupByStore(TimeRange timeRange, List<Long> stores);

    List<AccessRecordCount> listEnterByUserAndGroupByStore(@NonNull long userId, TimeRange timeRange, List<Long> stores);

    List<AccessRecordCount> listEntrantByUserAndGroupByStore(@NonNull long userId, TimeRange timeRange, List<Long> stores);

    List<AccessRecordCount> listValidByUserAndGroupByStore(@NonNull long userId, TimeRange timeRange, List<Long> stores);
}
