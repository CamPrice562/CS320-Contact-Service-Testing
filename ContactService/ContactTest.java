/*
 * ContactTest.java
 * CS 320: Software Test, Automation QA
 * Module Three Milestone: Contact Service
 * Author: Cameron Price
 */

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the five Contact class requirements. Each requirement gets a test for the
 * boundary that is allowed and a test for each way it can be broken, because a rule
 * that is only tested with obviously bad input does not prove the boundary is right.
 */
class ContactTest {

    @Test
    @DisplayName("A contact built from valid values keeps every field")
    void testContactCreatedWithValidValues() {
        Contact contact = new Contact("C1000", "Cameron", "Price", "5622533505", "123 Main St, Long Beach CA");
        assertAll(
                () -> assertEquals("C1000", contact.getContactId()),
                () -> assertEquals("Cameron", contact.getFirstName()),
                () -> assertEquals("Price", contact.getLastName()),
                () -> assertEquals("5622533505", contact.getPhone()),
                () -> assertEquals("123 Main St, Long Beach CA", contact.getAddress()));
    }

    // ---------------------------------------------------------- requirement 1: ID
    @Test
    @DisplayName("Contact ID of exactly 10 characters is allowed")
    void testContactIdAtMaxLength() {
        Contact contact = new Contact("1234567890", "Cameron", "Price", "5622533505", "123 Main St");
        assertEquals("1234567890", contact.getContactId());
    }

    @Test
    @DisplayName("Contact ID longer than 10 characters is rejected")
    void testContactIdTooLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("12345678901", "Cameron", "Price", "5622533505", "123 Main St"));
    }

    @Test
    @DisplayName("Null contact ID is rejected")
    void testContactIdNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact(null, "Cameron", "Price", "5622533505", "123 Main St"));
    }

    // -------------------------------------------------- requirement 2: firstName
    @Test
    @DisplayName("First name of exactly 10 characters is allowed")
    void testFirstNameAtMaxLength() {
        Contact contact = new Contact("C1000", "ABCDEFGHIJ", "Price", "5622533505", "123 Main St");
        assertEquals("ABCDEFGHIJ", contact.getFirstName());
    }

    @Test
    @DisplayName("First name longer than 10 characters is rejected")
    void testFirstNameTooLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C1000", "ABCDEFGHIJK", "Price", "5622533505", "123 Main St"));
    }

    @Test
    @DisplayName("Null first name is rejected")
    void testFirstNameNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C1000", null, "Price", "5622533505", "123 Main St"));
    }

    // --------------------------------------------------- requirement 3: lastName
    @Test
    @DisplayName("Last name of exactly 10 characters is allowed")
    void testLastNameAtMaxLength() {
        Contact contact = new Contact("C1000", "Cameron", "ABCDEFGHIJ", "5622533505", "123 Main St");
        assertEquals("ABCDEFGHIJ", contact.getLastName());
    }

    @Test
    @DisplayName("Last name longer than 10 characters is rejected")
    void testLastNameTooLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C1000", "Cameron", "ABCDEFGHIJK", "5622533505", "123 Main St"));
    }

    @Test
    @DisplayName("Null last name is rejected")
    void testLastNameNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C1000", "Cameron", null, "5622533505", "123 Main St"));
    }

    // ------------------------------------------------------ requirement 4: phone
    @Test
    @DisplayName("Phone of exactly 10 digits is allowed")
    void testPhoneExactlyTenDigits() {
        Contact contact = new Contact("C1000", "Cameron", "Price", "5622533505", "123 Main St");
        assertEquals("5622533505", contact.getPhone());
    }

    @Test
    @DisplayName("Phone with fewer than 10 digits is rejected")
    void testPhoneTooShort() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C1000", "Cameron", "Price", "562253350", "123 Main St"));
    }

    @Test
    @DisplayName("Phone with more than 10 digits is rejected")
    void testPhoneTooLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C1000", "Cameron", "Price", "56225335051", "123 Main St"));
    }

    @Test
    @DisplayName("Phone that is 10 characters but not 10 digits is rejected")
    void testPhoneNonNumeric() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C1000", "Cameron", "Price", "562-253-35", "123 Main St"));
    }

    @Test
    @DisplayName("Null phone is rejected")
    void testPhoneNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C1000", "Cameron", "Price", null, "123 Main St"));
    }

    // ---------------------------------------------------- requirement 5: address
    @Test
    @DisplayName("Address of exactly 30 characters is allowed")
    void testAddressAtMaxLength() {
        String address = "123456789012345678901234567890";
        Contact contact = new Contact("C1000", "Cameron", "Price", "5622533505", address);
        assertEquals(address, contact.getAddress());
    }

    @Test
    @DisplayName("Address longer than 30 characters is rejected")
    void testAddressTooLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C1000", "Cameron", "Price", "5622533505", "1234567890123456789012345678901"));
    }

    @Test
    @DisplayName("Null address is rejected")
    void testAddressNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("C1000", "Cameron", "Price", "5622533505", null));
    }

    // ------------------------------------------------- setters enforce the rules
    @Test
    @DisplayName("Setters apply valid updates")
    void testSettersAcceptValidValues() {
        Contact contact = new Contact("C1000", "Cameron", "Price", "5622533505", "123 Main St");
        contact.setFirstName("Cam");
        contact.setLastName("P");
        contact.setPhone("1112223333");
        contact.setAddress("456 Ocean Blvd");
        assertAll(
                () -> assertEquals("Cam", contact.getFirstName()),
                () -> assertEquals("P", contact.getLastName()),
                () -> assertEquals("1112223333", contact.getPhone()),
                () -> assertEquals("456 Ocean Blvd", contact.getAddress()));
    }

    @Test
    @DisplayName("Setters reject invalid updates and leave the contact unchanged")
    void testSettersRejectInvalidValues() {
        Contact contact = new Contact("C1000", "Cameron", "Price", "5622533505", "123 Main St");
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> contact.setFirstName("ABCDEFGHIJK")),
                () -> assertThrows(IllegalArgumentException.class, () -> contact.setLastName(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> contact.setPhone("12345")),
                () -> assertThrows(IllegalArgumentException.class, () -> contact.setAddress(null)));
        assertEquals("Cameron", contact.getFirstName());
    }
}
