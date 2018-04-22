package com.wright.ftm.wrappers;

public class ClassWrapper {
    public Object forName(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return Class.forName(className).newInstance();
    }
}
