package com.example.literalura.service;

import java.util.List;

public interface IconvertData {


        <T> T getData(String json,Class<T> classe);

        <T> List<T> getDataList(String json, Class<T> classe);

}
