package com.flixbus.agency.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.util.Collection;

public class BeanCustomUtil<T> {

    public T copyNonNullProperties(T source, T target) {
        if (source == null || target == null || target.getClass() != source.getClass()) return null;

        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        for (final Field property : target.getClass().getDeclaredFields()) {
            Object providedObject = src.getPropertyValue(property.getName());
            if (providedObject != null && !(providedObject instanceof Collection<?>)) {
                trg.setPropertyValue(property.getName(), providedObject);
            }
        }
        return target;
    }
}
