package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RemarkTest {
    @Test
    public void equal_remarks() {
        Remark remark = new Remark("Some remark");
        Remark sameRemark = new Remark("Some remark");
        Remark differentRemark = new Remark("Different remark");

        assertTrue(remark.equals(remark));
        assertTrue(remark.equals(sameRemark));
        assertFalse(remark.equals(differentRemark));
    }

}
