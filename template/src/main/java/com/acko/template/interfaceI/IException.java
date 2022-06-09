package com.acko.template.interfaceI;

public interface IException {

    default String getMessage(Object e) {
        return ((Exception) e).getMessage();
    }
}
