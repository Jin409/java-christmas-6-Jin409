package christmas.model;

import java.util.Arrays;
import java.util.function.Predicate;

public enum Badge {
    SANTA("산타",
            discountedAmount -> discountedAmount >= 20_000, 1),
    REE("트리",
            discountedAmount -> discountedAmount >= 10_000, 2),
    STAR("별", discountedAmount -> discountedAmount >= 5_000, 3);

    private final String name;
    private final Predicate<Long> isAchievable;
    private final int order;

    Badge(String name, Predicate<Long> isAchievable, int order) {
        this.name = name;
        this.isAchievable = isAchievable;
        this.order = order;
    }

    public static Badge findByDiscountedAmount(long discountedAmount) {
        return Arrays.asList(Badge.values()).stream()
                .filter(badge -> badge.isAchievable.test(discountedAmount))
                .findFirst()
                .orElse(null);
    }
}
