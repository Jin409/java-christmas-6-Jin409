package christmas.dto;

public record OrderRequestDto(
        String menuName,
        long quantity
) {
}
