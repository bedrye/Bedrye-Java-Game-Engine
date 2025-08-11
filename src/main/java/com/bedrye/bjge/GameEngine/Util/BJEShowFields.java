package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.Objects.BJEUIObject;

import java.lang.reflect.Field;

public class BJEShowFields {


    void getClassVars() {
        Field[] fields = BJEUIObject. class.getFields();
        for (Field field: fields) {
            field.getClass().getSimpleName();
            field.getName();
        }

    }
}
