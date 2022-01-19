package com.goodplatform.insa.service;

import com.goodplatform.insa.mapper.CodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Service("codeService")
public class CodeService {

    @Autowired
    CodeMapper codeMapper;

    public List<HashMap<String, Object>> selectCodeList(HashMap<String, Object> params) throws Exception {
        return codeMapper.selectCodeList(params);
    }

    public List<HashMap<String, Object>> selectCodeList(HttpServletRequest request, HashMap<String, Object> params) throws Exception {
        return this.selectCodeList(params);
    }

}
