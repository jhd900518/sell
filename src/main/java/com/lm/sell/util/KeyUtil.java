package com.lm.sell.util;

import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一的组建
     *
     * @return 时间+随机数
     */
    public static synchronized String genUnique() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
