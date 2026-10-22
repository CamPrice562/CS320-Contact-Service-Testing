/*
 * Contact.java
 * CS 320: Software Test, Automation QA
 * Module Three Milestone: Contact Service
 * Author: Cameron Price
 */

/**
 * A single contact record.
 *
 * The contact ID is assigned once in the constructor and is never updatable, which is
 * why the field is final and there is no setter for it. Every other field runs through
 * the same setter on construction and on update, so a Contact can never be holding a
 * value that breaks the requirements: there is exactly one place each rule is enforced.
 */
public class Contact {

    private static final int ID_MAX_LENGTH = 10;
    private static final int NAME_MAX_LENGTH = 10;
    private static final int ADDRESS_MAX_LENGTH = 30;
    private static final String PHONE_PATTERN = "\\d{10}";

    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        if (contactId == null || contactId.length() > ID_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Contact ID is required and cannot be longer than " + ID_MAX_LENGTH + " characters");
        }
        this.contactId = contactId;
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setAddress(address);
    }

    public String getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "First name is required and cannot be longer than " + NAME_MAX_LENGTH + " characters");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Last name is required and cannot be longer than " + NAME_MAX_LENGTH + " characters");
        }
        this.lastName = lastName;
    }

    /**
     * The requirement is "exactly 10 digits", so a length check alone is not enough.
     * The pattern enforces both the length and that every character is a digit, which
     * rejects values like "555-12-3456" that are ten characters but not ten digits.
     */
    public void setPhone(String phone) {
        if (phone == null || !phone.matches(PHONE_PATTERN)) {
            throw new IllegalArgumentException("Phone is required and must be exactly 10 digits");
        }
        this.phone = phone;
    }

    public void setAddress(String address) {
        if (address == null || address.length() > ADDRESS_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Address is required and cannot be longer than " + ADDRESS_MAX_LENGTH + " characters");
        }
        this.address = address;
    }
}
