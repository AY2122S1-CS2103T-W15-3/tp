package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;

/**
 * Finds and lists all events in SoConnect which have names containing any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class EFindCommand extends Command {

    public static final String COMMAND_WORD = "efind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events which have names containing any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " exam hard CS2103T";

    private final EventNameContainsKeywordsPredicate predicate;

    public EFindCommand(EventNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EFindCommand // instanceof handles nulls
                && predicate.equals(((EFindCommand) other).predicate)); // state check
    }
}
