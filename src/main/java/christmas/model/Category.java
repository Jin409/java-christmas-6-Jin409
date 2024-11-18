package christmas.model;

import java.util.Arrays;

public enum Category {
    DESSERT("디저트"),
    MAIN("메인"),
    APPETIZER("에피타이저"),
    DRINK("음료");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public static Category findByValue(final String comparedValue) {
        return Arrays.stream(Category.values())
                .filter(category -> category.value.equals(comparedValue))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 카테고리입니다."));
    }
}
