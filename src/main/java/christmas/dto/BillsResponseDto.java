package christmas.dto;

import java.util.List;

public record BillsResponseDto(
        List<OrderedMenuDto> orderedMenus,
        long originPrice,
        List<OfferedMenuDto> offeredMenuDtos,
        List<DiscountedHistoryDto> discountedHistoryDtos,
        long totalDiscountedAmount,
        long totalDiscountedPrice
) {
    public static BillsResponseDto from(List<OrderedMenuDto> orderedMenus, long originPrice,
                                        List<OfferedMenuDto> offeredMenuDtos,

                                        List<DiscountedHistoryDto> discountedHistoryDtos) {
        long totalDiscountedAmount = 0;
        if (discountedHistoryDtos != null) {
            totalDiscountedAmount = discountedHistoryDtos.stream()
                    .mapToLong(DiscountedHistoryDto::discountedAmount)
                    .sum();
        }
        long totalDiscountedPrice = originPrice - totalDiscountedAmount;

        return new BillsResponseDto(orderedMenus, originPrice, offeredMenuDtos, discountedHistoryDtos,
                totalDiscountedAmount, totalDiscountedPrice);
    }

    public record OrderedMenuDto(
            String menuName,
            long quantity
    ) {
    }

    public record OfferedMenuDto(
            String menuName,
            long quantity
    ) {
    }

    public record DiscountedHistoryDto(
            String name,
            long discountedAmount
    ) {
    }

}
