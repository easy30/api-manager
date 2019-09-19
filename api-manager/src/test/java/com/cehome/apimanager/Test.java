package com.cehome.apimanager;

import org.springframework.util.AntPathMatcher;

public class Test {
    @org.junit.Test
    public void test(){
        AntPathMatcher apm = new AntPathMatcher();
        System.out.println(apm.match("/wapmanageApi/ajax/callRecord/findCallInRecordForMByIds","/wapmanageApi/ajax/callRecord/findCallInRecordForMByIds"));

    }
}
