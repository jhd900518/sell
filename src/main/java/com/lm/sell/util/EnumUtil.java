package com.lm.sell.util;

import com.lm.sell.enums.CodeEnum;
import org.aspectj.apache.bcel.classfile.Code;

public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
