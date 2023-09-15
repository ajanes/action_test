import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.ContactDatabase;

public class ContactDatabaseTest {

    @Test
    public void testAddAndRetrieveContact() {
        ContactDatabase db = new ContactDatabase();
        db.createTable();
        db.addContact("Alice", "1112223333");

        // Retrieve the contact and check the phone number
        String phone = db.getContactByName("Alice");
        assertEquals("1112223333", phone);
    }

    @Test
    public void testRetrieveNonexistentContact() {
        ContactDatabase db = new ContactDatabase();
        db.createTable();

        // Try to retrieve a contact that doesn't exist
        String phone = db.getContactByName("Bob");
        assertNull(phone);
    }

    @Test
    public void testAddAndRetrieveMultipleContacts() {
        ContactDatabase db = new ContactDatabase();
        db.createTable();
        
        // Add multiple contacts
        db.addContact("Charlie", "4445556666");
        db.addContact("David", "7778889999");

        // Retrieve the contacts and check the phone numbers
        assertEquals("4445556666", db.getContactByName("Charlie"));
        assertEquals("7778889999", db.getContactByName("David"));
    }
}
