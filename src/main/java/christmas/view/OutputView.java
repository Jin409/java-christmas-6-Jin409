package christmas.view;

import christmas.dto.BillsResponseDto;
import christmas.dto.BillsResponseDto.BadgeResponseDto;
import christmas.dto.BillsResponseDto.DiscountedHistoryDto;
import christmas.dto.BillsResponseDto.OfferedMenuDto;
import christmas.dto.BillsResponseDto.OrderedMenuDto;
import christmas.model.Bills.DiscountedHistory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JInternalFrame.JDesktopIcon;
import org.w3c.dom.ls.LSOutput;

public class OutputView {

    public static void displayBills(BillsResponseDto billsResponseDto) {
        List<String> parsedOrderedMenus = new ArrayList<>();
        for (OrderedMenuDto orderedMenuDto : billsResponseDto.orderedMenus()) {
            parsedOrderedMenus.add(orderedMenuDto.menuName() + " " + orderedMenuDto.quantity() + "개");
        }

        System.out.println("<주문 메뉴>");
        parsedOrderedMenus.forEach(System.out::println);

        System.out.println("<할인 전 총주문 금액>");
        System.out.printf("%,d%n", billsResponseDto.originPrice());

        System.out.println("<증정 메뉴>");
        parseOfferedMenusToDisplay(billsResponseDto.offeredMenuDtos()).forEach(System.out::println);

        System.out.println("<혜택 내역>");
        parseDiscountedHistory(billsResponseDto.discountedHistoryDtos()).forEach(System.out::println);

        System.out.println("<총혜택 금액>");
        System.out.println(parseTotalDiscountedAmount(billsResponseDto.totalDiscountedAmount()));

        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(parseTotalDiscountedPrice(billsResponseDto.totalDiscountedPrice()));

        System.out.println("<12월 이벤트 배지>");
        System.out.println(parseBadgeInformation(billsResponseDto.badgeResponseDto()));
    }

    private static String parseBadgeInformation(BadgeResponseDto badgeResponseDto) {
        if (badgeResponseDto == null) {
            return "없음";
        }
        return badgeResponseDto.name();
    }

    private static String parseTotalDiscountedPrice(long totalDiscountedPrice) {
        if (totalDiscountedPrice == 0) {
            return "0원";
        }
        return (java.lang.String.format("%,d", totalDiscountedPrice) + "원");
    }

    private static String parseTotalDiscountedAmount(long totalDiscountedAmount) {
        if (totalDiscountedAmount == 0) {
            return "0원";
        }
        return ("-" + String.format("%,d", totalDiscountedAmount) + "원");
    }

    private static List<String> parseOfferedMenusToDisplay(List<OfferedMenuDto> offeredMenuDtos) {
        List<String> parsedOfferedMenus = new ArrayList<>();

        if (offeredMenuDtos == null) {
            parsedOfferedMenus.add("없음");
            return parsedOfferedMenus;
        }
        for (OfferedMenuDto offeredMenuDto : offeredMenuDtos) {
            parsedOfferedMenus.add(offeredMenuDto.menuName() + " " + offeredMenuDto.quantity() + "개");
        }
        return parsedOfferedMenus;
    }

    private static List<String> parseDiscountedHistory(List<DiscountedHistoryDto> discountedHistoryDtos) {
        List<String> parsedDiscountedHistories = new ArrayList<>();

        if (discountedHistoryDtos == null) {
            parsedDiscountedHistories.add("없음");
            return parsedDiscountedHistories;
        }
        for (DiscountedHistoryDto discountedHistoryDto : discountedHistoryDtos) {
            if (discountedHistoryDto.discountedAmount() == 0) {
                continue;
            }
            parsedDiscountedHistories.add(
                    discountedHistoryDto.name() + ": -" + String.format("%,d", discountedHistoryDto.discountedAmount())
                            + "원");
        }
        return parsedDiscountedHistories;
    }
}
