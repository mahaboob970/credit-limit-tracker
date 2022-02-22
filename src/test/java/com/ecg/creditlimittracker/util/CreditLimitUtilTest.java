package com.ecg.creditlimittracker.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CreditLimitUtilTest {

	@Test
	public void testConvertDollarToCent() {
		assertEquals(0.0, CreditLimitUtil.convertDollarToCent(null));
		assertEquals(1.0, CreditLimitUtil.convertDollarToCent("100"));
	}

}
