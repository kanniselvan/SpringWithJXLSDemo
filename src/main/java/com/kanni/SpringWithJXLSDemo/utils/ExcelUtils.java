package com.kanni.SpringWithJXLSDemo.utils;

import lombok.experimental.UtilityClass;


public class ExcelUtils {

    // ${fun:validateHttp(data.http)}
    public String validateHttp(boolean flag){
        if(flag)
            return "Yes";
        return "No";
    }
}
