package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.Objects.Editor.UI.BJEUIInspector;

import java.lang.reflect.Field;

public class BJEShowFields {


    void getClassVars() {
        Field[] fields = BJEUIInspector. class.getFields();
        for (Field field: fields) {
            field.getClass().getSimpleName();
            field.getName();
        }

    }
}
