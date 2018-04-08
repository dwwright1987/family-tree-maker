package com.wright.ftm.wrappers;

import sun.reflect.CallerSensitive;

public class ClassWrapper {
    @CallerSensitive
    public Object forName(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return Class.forName(className).newInstance();
    }
}
