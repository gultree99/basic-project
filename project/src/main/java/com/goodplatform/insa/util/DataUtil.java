package com.goodplatform.insa.util;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DataUtil {

	public static List<HashMap<String,Object>> jsonArrayStrToHashList(HashMap<String,Object> params,String obj_nm){

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();

		if("".equals(FormatUtil.toString(obj_nm))||"".equals(FormatUtil.toString(params.get(obj_nm)))){
			return list;
		}

		try {

			JSONArray array = new JSONArray(FormatUtil.toString(params.get(obj_nm)));
			list = array.toList().stream().map(m -> ((HashMap<String, Object>) m)).collect(Collectors.toList());

		}catch (Exception e){
			e.printStackTrace();
			return list;
		}

		return list;
	}
}
