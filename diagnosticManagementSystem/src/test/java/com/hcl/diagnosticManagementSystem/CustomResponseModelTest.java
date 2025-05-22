package com.hcl.diagnosticManagementSystem;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.hcl.diagnosticManagementSystem.apiResponseModel.CustomResponseModel;

class CustomResponseModelTest {

    @Test
    void testAllArgsConstructor() {
        CustomResponseModel<String> response = new CustomResponseModel<>(true, "Success", "Test Data");

        assertTrue(response.isSuccess());
        assertEquals("Success", response.getMessage());
        assertEquals("Test Data", response.getData());
    }
    
    /*
    @Test
    void testNoArgsConstructorAndSetters() {
        CustomResponseModel<String> response = new CustomResponseModel<>();
        response.setSuccess(false);
        response.setMessage("Failed");
        response.setData("Error Data");

        assertFalse(response.isSuccess());
        assertEquals("Failed", response.getMessage());
        assertEquals("Error Data", response.getData());
    }
    */

    @Test
    void testGenericTypeWithCustomObject() {
        DummyData dummy = new DummyData(101, "TestUser");
        CustomResponseModel<DummyData> response = new CustomResponseModel<>(true, "OK", dummy);

        assertEquals("OK", response.getMessage());
        assertEquals(dummy, response.getData());
        assertTrue(response.isSuccess());
    }

    static class DummyData {
        private int id;
        private String name;

        public DummyData(int id, String name) {
            this.id = id;
            this.name = name;
        }


        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof DummyData)) return false;
            DummyData other = (DummyData) obj;
            return this.id == other.id && this.name.equals(other.name);
        }

        @Override
        public int hashCode() {
            return id + name.hashCode();
        }
    }
}
