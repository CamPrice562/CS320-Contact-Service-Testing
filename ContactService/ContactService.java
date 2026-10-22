/*
 * ContactService.java
 * CS 320: Software Test, Automation QA
 * Module Three Milestone: Contact Service
 * Author: Cameron Price
 */

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory store for Contact objects. No database and no user interface, per the
 * milestone requirements.
 *
 * A HashMap keyed on contact ID is used rather than a List because every operation the
 * service supports is "find this one contact by its ID". That turns add, delete, and
 * update into constant time lookups and makes the uniqueness rule free to enforce,
 * since a map cannot hold two entries under the same key.
 */
public class ContactService {

    private final Map<String, Contact> contacts = new HashMap<>();

    /**
     * Adds a contact. The ID must be unique, so an ID already in the map is rejected
     * rather than silently overwriting the existing contact.
     */
    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException(
                    "A contact with ID " + contact.getContactId() + " already exists");
        }
        contacts.put(contact.getContactId(), contact);
    }

    public void deleteContact(String contactId) {
        requireContact(contactId);
        contacts.remove(contactId);
    }

    public void updateFirstName(String contactId, String firstName) {
        requireContact(contactId).setFirstName(firstName);
    }

    public void updateLastName(String contactId, String lastName) {
        requireContact(contactId).setLastName(lastName);
    }

    public void updatePhone(String contactId, String phone) {
        requireContact(contactId).setPhone(phone);
    }

    public void updateAddress(String contactId, String address) {
        requireContact(contactId).setAddress(address);
    }

    public Contact getContact(String contactId) {
        return contacts.get(contactId);
    }

    public int getContactCount() {
        return contacts.size();
    }

    /**
     * Every operation that acts on an existing contact needs the same "does it exist"
     * check, so it lives in one place. Update methods delegate their field validation
     * to the Contact setters rather than repeating the rules here.
     */
    private Contact requireContact(String contactId) {
        Contact contact = contacts.get(contactId);
        if (contact == null) {
            throw new IllegalArgumentException("No contact found with ID " + contactId);
        }
        return contact;
    }
}
