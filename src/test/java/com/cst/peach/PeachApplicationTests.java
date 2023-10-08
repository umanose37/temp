package com.cst.peach;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class PeachApplicationTests {

	@Test
	void contextLoads() {

	}

	void main() {
		Map<String, String> map = new HashMap<>();
		map.put("color", "red");
		map.put("size", "XL");

		System.out.println(map.toString());
		if (map.containsKey("qty")) System.out.println("qty");
		if (!map.containsKey("qty")) System.out.println("not qty");

		// test comment 20231008231900
		System.out.println("1");

	}

}
