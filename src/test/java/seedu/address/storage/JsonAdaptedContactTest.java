package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedContactTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_TELEGRAM_HANDLE = BENSON.getAddress().toString();
    private static final String VALID_ZOOM_LINK = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_UUID = UUID.randomUUID().toString();
    private static final List<String> VALID_LINKED_EVENTS = new ArrayList<>();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedContact person = new JsonAdaptedContact(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    VALID_TELEGRAM_HANDLE, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContact person = new JsonAdaptedContact(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
            VALID_TELEGRAM_HANDLE, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedContact person =
                new JsonAdaptedContact(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TELEGRAM_HANDLE,
                    VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_returnsPerson() throws Exception {
        Contact contactWithNullPhone = new PersonBuilder().withPhone(null).build();
        JsonAdaptedContact person = new JsonAdaptedContact(contactWithNullPhone);
        assertEquals(contactWithNullPhone, person.toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedContact person =
                new JsonAdaptedContact(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TELEGRAM_HANDLE,
                    VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedContact person = new JsonAdaptedContact(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
            VALID_TELEGRAM_HANDLE, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedContact person =
                new JsonAdaptedContact(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TELEGRAM_HANDLE,
                    VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_returnsPerson() throws Exception {
        Contact contactWithNullAddress = new PersonBuilder().withAddress(null).build();
        JsonAdaptedContact person = new JsonAdaptedContact(contactWithNullAddress);
        assertEquals(contactWithNullAddress, person.toModelType());
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedContact person =
                new JsonAdaptedContact(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TELEGRAM_HANDLE,
                    VALID_ZOOM_LINK, invalidTags, VALID_UUID, VALID_LINKED_EVENTS);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
