package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.testutil.TypicalAddressBook;
import seedu.address.testutil.TypicalIndexes;

class EUnlinkCommandTest {
    private final AddressBook typicalAddressBook = TypicalAddressBook.getTypicalAddressBook();
    private final Model typicalModel = new ModelManager(typicalAddressBook, new UserPrefs());

    /**
     * Links event 0 to contact 0 and 1. Links event 1 to contact 2. Links event 3 to contact 1.
     */
    @BeforeEach
    public void setUp() {
        setUp(typicalModel);
    }

    /**
     * Links event 0 to contact 0, 1, 3. Links event 1 to contact 2. Links event 3 to contact 1.
     */
    private void setUp(Model model) {
        model.linkEventAndContact(
            model.getFilteredEventList().get(0),
            model.getFilteredContactList().get(0));
        model.linkEventAndContact(
            model.getFilteredEventList().get(0),
            model.getFilteredContactList().get(1));
        model.linkEventAndContact(
            model.getFilteredEventList().get(0),
            model.getFilteredContactList().get(3));
        model.linkEventAndContact(
            model.getFilteredEventList().get(1),
            model.getFilteredContactList().get(2));
        model.linkEventAndContact(
            model.getFilteredEventList().get(2),
            model.getFilteredContactList().get(1));
    }

    private String generateStringFromSet(Set<Contact> set) {
        assert !set.isEmpty();
        StringBuilder result = new StringBuilder();
        for (Contact contact : set) {
            result.append(contact.getName());
            result.append(", ");
        }
        result.replace(result.length() - 2, result.length(), "");
        return result.append(".").toString();
    }

    @Test
    public void execute_singleUnlink_success() {
        EUnlinkCommand eUnlinkCommand = new EUnlinkCommand(
            INDEX_FIRST_EVENT,
            Set.of(TypicalIndexes.INDEX_FIRST_PERSON), false);
        Event eventToUnlink = typicalModel.getFilteredEventList().get(0);
        Contact contactToUnlink = typicalModel.getFilteredContactList().get(0);
        Model newModel = new ModelManager(typicalModel.getAddressBook(), new UserPrefs());
        setUp(newModel);
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(0));
        String commandSuccessMessage = String.format(EUnlinkCommand.MESSAGE_SUCCESS,
            eventToUnlink.getName(), "", contactToUnlink.getName() + ".");
        assertCommandSuccess(eUnlinkCommand, typicalModel, commandSuccessMessage, newModel);
    }

    @Test
    public void execute_multipleUnlink_success() {
        EUnlinkCommand eUnlinkCommand = new EUnlinkCommand(
            INDEX_FIRST_EVENT,
            Set.of(TypicalIndexes.INDEX_FIRST_PERSON, TypicalIndexes.INDEX_SECOND_PERSON), false);
        Event eventToUnlink = typicalModel.getFilteredEventList().get(0);
        Contact contact1ToUnlink = typicalModel.getFilteredContactList().get(0);
        Contact contact2ToUnlink = typicalModel.getFilteredContactList().get(1);
        Set<Contact> setOfContacts = Set.of(contact1ToUnlink, contact2ToUnlink);
        Model newModel = new ModelManager(typicalModel.getAddressBook(), new UserPrefs());
        setUp(newModel);
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(0));
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(1));
        String commandSuccessMessage = String.format(EUnlinkCommand.MESSAGE_SUCCESS,
            eventToUnlink.getName(), "s", generateStringFromSet(setOfContacts));
        assertCommandSuccess(eUnlinkCommand, typicalModel, commandSuccessMessage, newModel);
    }

    @Test
    public void execute_allUnlink_success() {
        EUnlinkCommand eUnlinkCommand = new EUnlinkCommand(
            INDEX_FIRST_EVENT,
            Set.of(), true);
        Event eventToUnlink = typicalModel.getFilteredEventList().get(0);
        Model newModel = new ModelManager(typicalModel.getAddressBook(), new UserPrefs());
        setUp(newModel);
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(0));
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(1));
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(3));
        String commandSuccessMessage = String.format(
            EUnlinkCommand.MESSAGE_SUCCESS_CLEAR_ALL,
            eventToUnlink.getName());
        assertCommandSuccess(eUnlinkCommand, typicalModel, commandSuccessMessage, newModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        EUnlinkCommand eUnlinkCommand = new EUnlinkCommand(
            Index.fromZeroBased(100),
            Set.of(), true);
        assertCommandFailure(eUnlinkCommand, typicalModel, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        EUnlinkCommand eUnlinkCommand2 = new EUnlinkCommand(
            INDEX_FIRST_EVENT,
            Set.of(INDEX_SECOND_PERSON, Index.fromZeroBased(101)), false);
        assertCommandFailure(eUnlinkCommand2, typicalModel, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void testEquals() {
        EUnlinkCommand eUnlinkCommand1 = new EUnlinkCommand(INDEX_FIRST_EVENT, Set.of(), true);
        assertEquals(eUnlinkCommand1, eUnlinkCommand1);

        EUnlinkCommand eUnlinkCommand2 = new EUnlinkCommand(INDEX_FIRST_EVENT, new HashSet<>(), true);
        assertEquals(eUnlinkCommand1, eUnlinkCommand2);

        EUnlinkCommand eUnlinkCommand3 = new EUnlinkCommand(INDEX_FIRST_EVENT,
            Set.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), false);
        EUnlinkCommand eUnlinkCommand4 = new EUnlinkCommand(INDEX_SECOND_EVENT,
            Set.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), false);
        Set<Index> indexSet = new HashSet<>();
        indexSet.add(INDEX_SECOND_PERSON);
        indexSet.add(INDEX_FIRST_PERSON);
        EUnlinkCommand eUnlinkCommand5 = new EUnlinkCommand(INDEX_SECOND_EVENT, indexSet, false);
        assertEquals(eUnlinkCommand5, eUnlinkCommand4);
        assertNotEquals(eUnlinkCommand3, eUnlinkCommand5);
        assertNotEquals(eUnlinkCommand4, eUnlinkCommand3);
        assertNotEquals(eUnlinkCommand1, eUnlinkCommand5);
    }

    @Test
    public void constructor_invalid_failure() {
        assertThrows(AssertionError.class, () -> new EUnlinkCommand(INDEX_FIRST_EVENT, Set.of(), false));
        assertThrows(
            AssertionError.class, () -> new EUnlinkCommand(INDEX_FIRST_EVENT, Set.of(INDEX_FIRST_PERSON), true));
        assertThrows(NullPointerException.class, () -> new EUnlinkCommand(INDEX_FIRST_EVENT, null, false));
        assertThrows(NullPointerException.class, () -> new EUnlinkCommand(null, Set.of(), false));
    }
}