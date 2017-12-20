package com.yd.manager.repository.custom;

import com.yd.manager.dto.util.DateRange;
import com.yd.manager.repository.SpringTestInit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class AccessRecordDTORepositoryTest extends SpringTestInit {

    @Autowired
    private AccessRecordDTORepository repository;

    @Test
    public void countEnterByUser() {
        System.err.println(repository.countEnterByUser(1L, DateRange.week().toTimeRange(), Arrays.asList(1L, 2L, 3L)));
    }

    @Test
    public void countEntrantByUser() {
        System.err.println(repository.countEntrantByUser(1L, DateRange.week().toTimeRange(), Arrays.asList(1L, 2L, 3L)));
    }

    @Test
    public void listCountEnterGroupByUser() {
        repository.listCountEnterGroupByUser(Arrays.asList(141L, 313L), null, null).forEach(System.err::println);
    }

    @Test
    public void listCountEntrantGroupByUser() {
        repository.listCountEntrantGroupByUser(Arrays.asList(141L, 313L), null, null).forEach(System.err::println);
    }

    @Test
    public void countEnterByStore() {
        System.err.println(repository.countEnterByStore(1L, DateRange.week().toTimeRange()));
    }

    @Test
    public void countEntrantByStore() {
        System.err.println(repository.countEntrantByStore(1L, DateRange.week().toTimeRange()));
    }

    @Test
    public void listCountEnterGroupByStore() {
        repository.listCountEnterGroupByStore(null, Arrays.asList(1L, 9L)).forEach(System.err::println);
    }

    @Test
    public void listCountEntrantGroupByStore() {
        repository.listCountEntrantGroupByStore(null, Arrays.asList(1L, 9L)).forEach(System.err::println);
    }
}