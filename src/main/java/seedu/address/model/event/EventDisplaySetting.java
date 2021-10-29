package seedu.address.model.event;

import java.util.Objects;

/**
 * Contains all the display settings for events in the model manager.
 */
public class EventDisplaySetting {
    public static final EventDisplaySetting DEFAULT_SETTING = new EventDisplaySetting(false);
    private final boolean willDisplayStartDateTime;
    private final boolean willDisplayEndDateTime;
    private final boolean willDisplayDescription;
    private final boolean willDisplayAddress;
    private final boolean willDisplayZoomLink;
    private final boolean willDisplayTags;
    private final boolean isViewingFull;

    /**
     * Creates a new {@code EventDisplaySetting} with the given settings.
     * This is usually used in {@code EListCommand}, where the {@code isViewingFull} is always false.
     */
    public EventDisplaySetting(
        boolean willDisplayStartDateTime, boolean willDisplayEndDateTime, boolean willDisplayDescription,
        boolean willDisplayAddress, boolean willDisplayZoomLink, boolean willDisplayTags) {
        this.willDisplayStartDateTime = willDisplayStartDateTime;
        this.willDisplayEndDateTime = willDisplayEndDateTime;
        this.willDisplayDescription = willDisplayDescription;
        this.willDisplayAddress = willDisplayAddress;
        this.willDisplayZoomLink = willDisplayZoomLink;
        this.willDisplayTags = willDisplayTags;
        this.isViewingFull = false;
    }

    /**
     * Creates a new {@code EventDisplaySetting} with the given settings.
     * By default, all willDisplayXXX fields will be true.
     */
    public EventDisplaySetting(boolean isViewingFull) {
        willDisplayStartDateTime = true;
        willDisplayEndDateTime = true;
        willDisplayDescription = true;
        willDisplayAddress = true;
        willDisplayZoomLink = true;
        willDisplayTags = true;
        this.isViewingFull = isViewingFull;
    }

    public boolean isWillDisplayStartDateTime() {
        return willDisplayStartDateTime;
    }

    public boolean isWillDisplayEndDateTime() {
        return willDisplayEndDateTime;
    }

    public boolean isWillDisplayDescription() {
        return willDisplayDescription;
    }

    public boolean isWillDisplayAddress() {
        return willDisplayAddress;
    }

    public boolean isWillDisplayZoomLink() {
        return willDisplayZoomLink;
    }

    public boolean isWillDisplayTags() {
        return willDisplayTags;
    }

    public boolean isViewingFull() {
        return isViewingFull;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDisplaySetting)) {
            return false;
        }
        EventDisplaySetting that = (EventDisplaySetting) o;
        return isWillDisplayStartDateTime() == that.isWillDisplayStartDateTime()
            && isWillDisplayEndDateTime() == that.isWillDisplayEndDateTime()
            && isWillDisplayDescription() == that.isWillDisplayDescription()
            && isWillDisplayAddress() == that.isWillDisplayAddress()
            && isWillDisplayZoomLink() == that.isWillDisplayZoomLink()
            && isWillDisplayTags() == that.isWillDisplayTags()
            && isViewingFull() == that.isViewingFull();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isWillDisplayStartDateTime(), isWillDisplayEndDateTime(), isWillDisplayDescription(),
            isWillDisplayAddress(), isWillDisplayZoomLink(), isWillDisplayTags(), isViewingFull());
    }

    /** For debugging/logging purposes. */
    @Override
    public String toString() {
        return "EventDisplaySetting{"
            + "willDisplayStartDateTime=" + willDisplayStartDateTime
            + ", willDisplayEndDateTime=" + willDisplayEndDateTime
            + ", willDisplayDescription=" + willDisplayDescription
            + ", willDisplayAddress=" + willDisplayAddress
            + ", willDisplayZoomLink=" + willDisplayZoomLink
            + ", willDisplayTags=" + willDisplayTags
            + ", isViewingFull=" + isViewingFull
            + '}';
    }
}
