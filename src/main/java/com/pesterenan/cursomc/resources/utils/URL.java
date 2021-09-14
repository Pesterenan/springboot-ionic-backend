package com.pesterenan.cursomc.resources.utils;

import java.util.ArrayList;
import java.util.List;

public class URL {

	public static List<Long> decodeLongList(String s) {
		List<Long> list = new ArrayList<Long>();
		String[] vet = s.split(",");
		for (int i = 0; i < vet.length; i++) {
			list.add(Long.parseLong(vet[i]));
		}
		return list;
		// Mesma instrução usando lambda function
		// return Arrays.asList( s.split(",")).stream().map(
		// 		x -> Long.parseLong(x)).collect(Collectors.toList());
	}
}
