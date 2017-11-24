package com.yd.manager.utils;

import de.ailis.pherialize.Pherialize;

import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class PhpUtils {

    public static List<Long> parse(String s) {

//        System.err.println(">>>>>>>>>>>>>" + s);
//        Mixed mixed = Pherialize.unserialize(s);
//        MixedArray array = mixed.toArray();
//        System.err.println(array);
//
//        return Collections.emptyList();
        return Pherialize.unserialize(s).toArray().values().stream().map(o -> Long.parseLong(o.toString())).collect(toList());
    }
}
