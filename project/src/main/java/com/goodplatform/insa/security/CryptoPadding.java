package com.goodplatform.insa.security;

//암호화에서 블럭 사이즈를 맞추기 위해 사용되는 Padding을 추상화 한 Interface
public interface CryptoPadding {
	
	//요청한 Block Size를 맞추기 위해 Padding을 추가한다.
	public byte[] addPadding(byte[] source, int blockSize);
	
	//요청한 Block Size를 맞추기 위해 추가 된 Padding을 제거한다.
	public byte[] removePadding(byte[] source, int blockSize);
}
