package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Remark {
    public static final String MESSAGE_CONSTRAINTS = "Remarks can take any values, and it can be blank";


    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param remark A remark.
     */
    public Remark(String remark) {
        value = remark;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
