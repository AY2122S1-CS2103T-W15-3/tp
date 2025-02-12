package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.InvalidDateTimeRangeException;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * An event is considered unique by comparing using {@code Event#isSameEvent(Event)}. As such, adding and updating of
 * events uses Event#isSameEvent(Event) for equality so as to ensure that the event being added or updated is
 * unique in terms of event name in the UniqueEventList. However, the removal of an event uses Event#equals(Object) so
 * as to ensure that the event with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#isSameEvent(Event)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    /**
     * Adds an event to the list.
     * The event must not already exist in the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        if (toAdd.getEndDateAndTime() != null && toAdd.getEndDateAndTime().isBefore(toAdd.getStartDateAndTime())) {
            throw new InvalidDateTimeRangeException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the list.
     * The event name of {@code editedEvent} must not be the same as another existing event in the list.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }
        if (!target.isSameEvent(editedEvent) && contains(editedEvent)) {
            throw new DuplicateEventException();
        }

        if (editedEvent.getEndDateAndTime() != null
            && editedEvent.getEndDateAndTime().isBefore(editedEvent.getStartDateAndTime())) {
            throw new InvalidDateTimeRangeException();
        }

        internalList.set(index, editedEvent);
    }

    /**
     * Removes the equivalent event from the list.
     * The event must exist in the list.
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    public void setEvents(UniqueEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        if (!eventsAreUnique(events)) {
            throw new DuplicateEventException();
        }
        internalList.setAll(events);
    }

    /**
     * Sorts the events based on whether they are {@code isMarked} and {@code startDateTime}
     */
    public void sortEvents() {
        internalList.sort(Comparator.comparing(Event::getStartDateAndTime));
        internalList.sort(Comparator.comparing(Event::getIsMarked, Comparator.reverseOrder()));
    }

    public void resetEvents() {
        internalList.clear();
    }

    /**
     * Moves marked events to the top of the list.
     * Places the newly marked events or replaces newly unmarked events
     * in the order specified in {@code events} and
     * based on {@code isMarked} which signals whether this method is called by
     * EMarkCommand or otherwise.
     */
    public void rearrangeEventsInOrder(List<Event> events, boolean isMarked) {
        ObservableList<Event> tempList = FXCollections.observableArrayList();
        if (isMarked) {
            tempList.addAll(events);
            tempList.addAll(internalList.filtered(event -> !events.contains(event)));
        } else {
            tempList.addAll(internalList.filtered(Event::getIsMarked));
            tempList.addAll(internalList.filtered(e -> !e.getIsMarked()));
        }
        internalList.clear();
        internalList.addAll(tempList);
    }

    /**
     * Update the UUID map in events.
     */
    public void updateEventMap() {
        for (Event event : internalList) {
            Event.addToMap(event);
        }
    }

    /**
     * Create a copy of a uniqueEventList
     *
     * @return a copy of a uniqueEventList
     */
    public ObservableList<Event> copy() {
        List<Event> eventList = new ArrayList<>(internalList);
        return FXCollections.observableArrayList(eventList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueEventList // instanceof handles nulls
                && internalList.equals(((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code events} contains only unique events.
     */
    private boolean eventsAreUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameEvent(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
