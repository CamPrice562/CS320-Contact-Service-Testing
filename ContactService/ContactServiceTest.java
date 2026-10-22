/*
 * ContactServiceTest.java
 * CS 320: Software Test, Automation QA
 * Module Three Milestone: Contact Service
 * Author: Cameron Price
 */

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the three ContactService requirements: add with a unique ID, delete per ID,
 * and update the four updatable fields per ID.
 */
class ContactServiceTest {

    private ContactService service;
    private Contact contact;

    @BeforeEach
    void setUp() {
        service = new ContactService();
        contact = new Contact("C1000", "Cameron", "Price", "5622533505", "123 Main St");
    }

    // -------------------------------------------------------- requirement 1: add
    @Test
    @DisplayName("A contact can be added and retrieved by ID")
    void testAddContact() {
        service.addContact(contact);
        assertAll(
                () -> assertEquals(1, service.getContactCount()),
                () -> assertNotNull(service.getContact("C1000")),
                () -> assertEquals("Cameron", service.getContact("C1000").getFirstName()));
    }

    @Test
    @DisplayName("Adding a second contact with a duplicate ID is rejected")
    void testAddDuplicateIdRejected() {
        service.addContact(contact);
        Contact duplicate = new Contact("C1000", "Someone", "Else", "9998887777", "999 Other St");
        assertThrows(IllegalArgumentException.class, () -> service.addContact(duplicate));
        assertEquals("Cameron", service.getContact("C1000").getFirstName());
    }

    @Test
    @DisplayName("Contacts with different IDs are both stored")
    void testAddMultipleContacts() {
        service.addContact(contact);
        service.addContact(new Contact("C2000", "Alex", "Reed", "5551234567", "456 Ocean Blvd"));
        assertEquals(2, service.getContactCount());
    }

    @Test
    @DisplayName("Adding a null contact is rejected")
    void testAddNullContact() {
        assertThrows(IllegalArgumentException.class, () -> service.addContact(null));
    }

    // ----------------------------------------------------- requirement 2: delete
    @Test
    @DisplayName("A contact can be deleted by ID")
    void testDeleteContact() {
        service.addContact(contact);
        service.deleteContact("C1000");
        assertAll(
                () -> assertEquals(0, service.getContactCount()),
                () -> assertNull(service.getContact("C1000")));
    }

    @Test
    @DisplayName("Deleting only removes the contact that was asked for")
    void testDeleteLeavesOtherContacts() {
        service.addContact(contact);
        service.addContact(new Contact("C2000", "Alex", "Reed", "5551234567", "456 Ocean Blvd"));
        service.deleteContact("C1000");
        assertAll(
                () -> assertEquals(1, service.getContactCount()),
                () -> assertNotNull(service.getContact("C2000")));
    }

    @Test
    @DisplayName("Deleting an ID that does not exist is rejected")
    void testDeleteMissingContact() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("NOPE"));
    }

    // ----------------------------------------------------- requirement 3: update
    @Test
    @DisplayName("All four updatable fields can be updated per contact ID")
    void testUpdateAllUpdatableFields() {
        service.addContact(contact);
        service.updateFirstName("C1000", "Cam");
        service.updateLastName("C1000", "Jordan");
        service.updatePhone("C1000", "1112223333");
        service.updateAddress("C1000", "456 Ocean Blvd");
        Contact updated = service.getContact("C1000");
        assertAll(
                () -> assertEquals("Cam", updated.getFirstName()),
                () -> assertEquals("Jordan", updated.getLastName()),
                () -> assertEquals("1112223333", updated.getPhone()),
                () -> assertEquals("456 Ocean Blvd", updated.getAddress()),
                () -> assertEquals("C1000", updated.getContactId()));
    }

    @Test
    @DisplayName("Updating an ID that does not exist is rejected")
    void testUpdateMissingContact() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("NOPE", "Cam")),
                () -> assertThrows(IllegalArgumentException.class, () -> service.updateLastName("NOPE", "Jordan")),
                () -> assertThrows(IllegalArgumentException.class, () -> service.updatePhone("NOPE", "1112223333")),
                () -> assertThrows(IllegalArgumentException.class, () -> service.updateAddress("NOPE", "456 Ocean")));
    }

    @Test
    @DisplayName("An invalid update is rejected and the stored contact does not change")
    void testUpdateWithInvalidValueRejected() {
        service.addContact(contact);
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("C1000", "ABCDEFGHIJK"));
        assertEquals("Cameron", service.getContact("C1000").getFirstName());
    }

    @Test
    @DisplayName("Retrieving an ID that does not exist returns null")
    void testGetMissingContactReturnsNull() {
        assertNull(service.getContact("NOPE"));
    }
}
