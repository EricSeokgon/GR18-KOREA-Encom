
package com.ncom.bpwb.util;

import java.util.List;

import org.apache.commons.collections4.ListUtils;

public class AsyncUtil {

	/**
	 * 쓰레드 갯수만큼 균등하게 나누어 준다.
	 * @param list
	 * @param threadSize
	 * @return
	 */
	public static <T> List<List<T>> partition(final List<T> list, final int threadSize) {

		// int threadSize=1; //테스트 용.. 하번만 실행시키기 위해
		int divideSize = (list.size() / threadSize) + 1;
//		System.out.println("itemListMap.size():"+list.size());
//		System.out.println(divideSize);
		return  ListUtils.partition(list, divideSize);
	}

}
