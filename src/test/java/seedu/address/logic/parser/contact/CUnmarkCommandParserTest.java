package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_INDEXES_PROVIDED;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.CUnmarkCommand;


class CUnmarkCommandParserTest {
    private CUnmarkCommandParser parser = new CUnmarkCommandParser();

    @Test
    public void parse_validArgs_returnsUnmarkCommand() {
        List<Index> indexes = new ArrayList<>();
        indexes.addAll(Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(8)));
        assertParseSuccess(parser, "1            2 8", new CUnmarkCommand(indexes));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CUnmarkCommand.MESSAGE_USAGE)); //EP empty argument
        assertParseFailure(parser, "a", MESSAGE_INVALID_INDEX); //EP non-integer argument
        assertParseFailure(parser, "1 1 2", MESSAGE_DUPLICATE_INDEXES_PROVIDED);
    }
}
