package com.hcl.diagnosticManagementSystem.utiltiy;

public class ValidationUtil {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNegative(Integer number) {
        return number != null && number < 0;
    }
}
