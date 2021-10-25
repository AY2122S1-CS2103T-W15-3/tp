package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.ELinkCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CAddCommand object
 */
public class ELinkCommandParser implements Parser<ELinkCommand> {

    private static final Pattern ELINK_COMMAND_FORMAT = Pattern.compile("(?<index>\\S+)(?<remainingArgs>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the ELinkCommand
     * and returns an ELinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ELinkCommand parse(String args) throws ParseException {
        final Matcher matcher = ELINK_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ELinkCommand.MESSAGE_USAGE));
        }
        final String index = matcher.group("index");
        final String remainingArgs = matcher.group("remainingArgs");

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(remainingArgs, PREFIX_CONTACT);
        if (!matcher.matches() || !arePrefixesPresent(argMultimap, PREFIX_CONTACT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ELinkCommand.MESSAGE_USAGE));
        }

        try {
            Index eventIndex = ParserUtil.parseIndex(index);
            List<String> listOfValues = argMultimap.getAllValues(PREFIX_CONTACT);
            Set<Index> contactIndexes = new HashSet<>();
            for (String value : listOfValues) {
                contactIndexes.add(ParserUtil.parseIndex(value));
            }
            return new ELinkCommand(eventIndex, contactIndexes);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ELinkCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
