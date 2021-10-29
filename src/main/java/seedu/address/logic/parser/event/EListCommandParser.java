package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.stream.Stream;

import seedu.address.logic.commands.event.EListCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDisplaySetting;

/**
 * Parses input arguments and creates a new EListCommand object
 */
public class EListCommandParser implements Parser<EListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EListCommand
     * and returns an EListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_DESCRIPTION,
                        PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG);
        if (anyPrefixValueNotEmpty(argMultimap, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_DESCRIPTION,
                PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EListCommand.MESSAGE_USAGE));
        }
        EventDisplaySetting displaySetting;
        if (noPrefixesPresent(argMultimap)) {
            displaySetting = EventDisplaySetting.DEFAULT_SETTING;
        } else {
            displaySetting = new EventDisplaySetting(
                argMultimap.getValue(PREFIX_START_TIME).isPresent(),
                argMultimap.getValue(PREFIX_END_TIME).isPresent(),
                argMultimap.getValue(PREFIX_DESCRIPTION).isPresent(),
                argMultimap.getValue(PREFIX_ADDRESS).isPresent(),
                argMultimap.getValue(PREFIX_ZOOM).isPresent(),
                argMultimap.getValue(PREFIX_TAG).isPresent()
            );
        }
        return new EListCommand(displaySetting);
    }

    /**
     * Checks if a {@code Prefix} prefix present in {@code ArgumentMultimap} has a non-empty value
     */
    private static boolean anyPrefixValueNotEmpty(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(prefix -> argumentMultimap.getValue(prefix).isPresent())
                .anyMatch(prefix -> !argumentMultimap.getValue(prefix).get().isEmpty());
    }

    /**
     * Returns true if none of the prefixes contains present {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean noPrefixesPresent(ArgumentMultimap argumentMultimap) {
        return Stream.of(PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_DESCRIPTION, PREFIX_ADDRESS,
                PREFIX_ZOOM, PREFIX_TAG)
                    .allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }
}
