package com.goodplatform.insa.tld;


import com.goodplatform.insa.util.FormatUtil;

import java.text.DecimalFormat;

public class StringTag {

	public static String roundCal(String str, Integer powNum) {
		try {
			return FormatUtil.toNumber(FormatUtil.round(str.replaceAll(",", ""), powNum)).toString();
		} catch (Exception e) {
			return str;
		}
	}
	
	public static String roundCalComma(String str, Integer powNum) {
		try {
			DecimalFormat formatter = new DecimalFormat("###,###.##");
			return formatter.format(FormatUtil.toNumber(FormatUtil.round(str.replaceAll(",", ""), powNum))).toString();
		} catch (Exception e) {
			return str;
		}
	}

	public static String moneyKorean(String amt) {
		try {
				String amt_msg = "";
				String[] arrayNum = {"", "일","이","삼","사","오","육","칠","팔","구"};
				String[] arrayUnit = {"","십","백","천","만","십만","백만","천만","억","십억","백업","천억","조","십조","백조","천조","경","십경","백경","천경","해","십해","백해","천해"};

				if(amt.length() > 0){
					int len = amt.length();

					String[] arrayStr = new String[len];
					String hanStr = "";
					String tmpUnit = "";
					for(int i = 0; i < len; i++){
						arrayStr[i] = amt.substring(i, i+1);
					}
					int code = len;
					for(int i = 0; i < len; i++){
						code--;
						tmpUnit = "";
						if(arrayNum[Integer.parseInt(arrayStr[i])] != ""){
							tmpUnit = arrayUnit[code];
							if(code > 4){
								if((Math.floor(code/4) == Math.floor((code-1)/4)
										&& arrayNum[Integer.parseInt(arrayStr[i+1])] != "") ||
										(Math.floor(code/4) == Math.floor((code-2)/4)
												&& arrayNum[Integer.parseInt(arrayStr[i+2])] != "")) {
									tmpUnit = arrayUnit[code].substring(0,1);
								}
							}
						}
						hanStr += arrayNum[Integer.parseInt(arrayStr[i])]+tmpUnit;
					}
					amt_msg = hanStr;
			}else{
				amt_msg = amt;
			}
			return amt_msg;
		} catch (Exception e) {
			return amt;
		}
	}

	public static String floorCal(String str) {
		try {
			@SuppressWarnings("deprecation")
			Double v = new Double(Math.floor(FormatUtil.toNumber(str.replaceAll(",", "")).doubleValue()));
			
			if (v.doubleValue() > Integer.MAX_VALUE) {
				return v.toString();
			} else {
				return Integer.toString(v.intValue());
			}
		} catch (Exception e) {
			return str;
		}
	}
	
	public static String ceilCal(String str) {
		try {
			@SuppressWarnings("deprecation")
			Double v = new Double(Math.ceil(FormatUtil.toNumber(str.replaceAll(",", "")).doubleValue()));
			
			if (v.doubleValue() > Integer.MAX_VALUE) {
				return v.toString();
			} else {
				return Integer.toString(v.intValue());
			}
		} catch (Exception e) {
			return str;
		}
	}
}
