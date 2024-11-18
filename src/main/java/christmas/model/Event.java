package christmas.model;

import java.time.LocalDate;

public interface Event {
    long getDiscountedAmount(LocalDate visitDate);
}
