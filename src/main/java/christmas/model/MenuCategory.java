package christmas.model;

import java.util.Arrays;

public enum MenuCategory {
    DESSERT("디저트"),
    MAIN("메인"),
    APPETIZER("에피타이저"),
    DRINK("음료");

    private final String value;

    MenuCategory(String value) {
        this.value = value;
    }

    public static MenuCategory findByValue(final String comparedValue) {
        return Arrays.stream(MenuCategory.values())
                .filter(menuCategory -> menuCategory.value.equals(comparedValue))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 카테고리입니다."));
    }
}
