package com.goodplatform.insa.util;

import com.goodplatform.insa.security.AriaCipher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtil {

	public static AriaCipher ARIA_CIPHER = new AriaCipher("stellarAria007");
	public static List<HashMap<String, Object>> SETTING_LIST = null;
	public static List<HashMap<String, Object>> CODE_LIST = null;
	public static long LOAD_TIME = 0;
	public static String filePath = "files";

	public static void systemSettingUpdate(Map<String, Object> params, Map<String, Object> data) {
		HashMap<String, Object> settingMap = new HashMap<String, Object>();
		String baseYear = null, baseTerm = null;

		if (CommonUtil.SETTING_LIST != null) {
			for (int i = 0; i < CommonUtil.SETTING_LIST.size(); ++i) {
				HashMap<String, Object> map = CommonUtil.SETTING_LIST.get(i);

				if (FormatUtil.toString(map.get("ST_NAME")).indexOf("TIMESET_") == 0) {
					settingMap.put(map.get("ST_NAME").toString(), map.get("ST_VAL"));
				}
			}

			if (data == null) {
				baseYear = FormatUtil.toStringValue(params.get("year"), FormatUtil.toString(settingMap.get("TIMESET_YEAR")));
				baseTerm = FormatUtil.toStringValue(params.get("term"), FormatUtil.toString(settingMap.get("TIMESET_TERM")));
			} else {
				baseYear = FormatUtil.toStringValue(data.get("YR"), FormatUtil.toString(settingMap.get("TIMESET_YEAR")));
				baseTerm = FormatUtil.toStringValue(data.get("TERM"), FormatUtil.toString(settingMap.get("TIMESET_TERM")));
			}

			for (int i = 0; i < CommonUtil.SETTING_LIST.size(); ++i) {
				HashMap<String, Object> map = CommonUtil.SETTING_LIST.get(i);

				if (FormatUtil.toString(map.get("ST_NAME")).indexOf("TIMESET_") == -1) {
					if ((FormatUtil.toString(map.get("YR")).equals(baseYear) && FormatUtil.toString(map.get("TERM")).equals(baseTerm))
							|| (FormatUtil.toString(map.get("YR")).equals("0000") && FormatUtil.toString(map.get("TERM")).equals("00"))
					) {//DB에서 설정값 가져올 시 연도, 학기 순으로 정렬하기 때문에 기본값(0000)이 먼저 담기고 해당 연도, 학기 값이 있을 시 오버라이드하므로 값은 ST_NAME 기준으로 1개만 존재
						settingMap.put(map.get("ST_NAME").toString(), map.get("ST_VAL"));
					}
				}

			}
		}

		HashMap<String, List<JSONObject>> codeList = new HashMap<String, List<JSONObject>>();
		HashMap<String, Object> codeMap = new HashMap<String, Object>();
		if (CommonUtil.CODE_LIST != null) {
			for (int i = 0; i < CommonUtil.CODE_LIST.size(); ++i) {
				HashMap<String, Object> map = CommonUtil.CODE_LIST.get(i);
				JSONObject map2 = new JSONObject((HashMap<String, Object>) map.clone());

				if ((FormatUtil.toInt(FormatUtil.toString(map.get("YR"))+FormatUtil.toString(map.get("TERM"))) <=FormatUtil.toInt((baseYear.equals("") ? "0000": baseYear)+(baseTerm.equals("") ? "00" : baseTerm))
				)|| (FormatUtil.toString(map.get("YR")).equals("0000") && FormatUtil.toString(map.get("TERM")).equals("00"))
				) {
					if(codeMap.containsKey(FormatUtil.toString(map.get("PRT_CD")))) {
						@SuppressWarnings("unchecked")
						HashMap<String,Object> in_map = (HashMap<String,Object>)codeMap.get(FormatUtil.toString(map.get("PRT_CD")));
						in_map.put(FormatUtil.toString(map.get("CD")),map);
					}else{
						HashMap<String,Object> n_map= new HashMap<String,Object>();
						n_map.put(FormatUtil.toString(map.get("CD")),map);
						codeMap.put(FormatUtil.toString(map.get("PRT_CD")), n_map);
					}

					if(codeList.containsKey(FormatUtil.toString(map2.get("PRT_CD")))) {
						List<JSONObject> in_map2 = codeList.get(FormatUtil.toString(map2.get("PRT_CD")));
						in_map2.add(map2);

					}else{

						List<JSONObject> n_list = new ArrayList<JSONObject>();
						n_list.add(map2);
						codeList.put(FormatUtil.toString(map2.get("PRT_CD")),n_list);
					}
				}
			}
		}

		params.put("SETTING", settingMap);
		params.put("CODE", codeMap);
		params.put("CODELIST",new JSONObject(codeList));
	}

	public static String filePathSet(String path) {

		String r_path = path.substring(0,path.lastIndexOf(File.separator));
		r_path = r_path.substring(0,r_path.lastIndexOf(File.separator)+1);
		r_path += CommonUtil.filePath;

		return r_path;
	}
}
