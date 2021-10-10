package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class CDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        CDeleteCommand cDeleteCommand = new CDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(cDeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        assertCommandSuccess(cDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        CDeleteCommand cDeleteCommand = new CDeleteCommand(outOfBoundIndex);

        assertCommandFailure(cDeleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        CDeleteCommand cDeleteCommand = new CDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(cDeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(cDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        CDeleteCommand cDeleteCommand = new CDeleteCommand(outOfBoundIndex);

        assertCommandFailure(cDeleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CDeleteCommand deleteFirstCommand = new CDeleteCommand(INDEX_FIRST_PERSON);
        CDeleteCommand deleteSecondCommand = new CDeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        CDeleteCommand deleteFirstCommandCopy = new CDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredContactList(p -> false);

        assertTrue(model.getFilteredContactList().isEmpty());
    }
}