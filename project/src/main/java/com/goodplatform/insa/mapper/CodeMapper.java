package com.goodplatform.insa.mapper;

import com.goodplatform.insa.annotation.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CodeMapper {

    public List<HashMap<String, Object>> selectCodeList(HashMap<String, Object> params) throws Exception;
}
