package christmas.model;

import java.util.List;

public class Bills {
    private final Orders orders;
    // 할인 전 총주문 금액은 orders 에서 가져오기
    private final List<OfferedMenu> offeredMenus;
    private final List<DiscountedHistory> discountedHistories;
    // 총혜택금액은 discountedHistories 의 합으로
    // 할인 후 예상 결제 금액도 discountedHistories 의 합으로


    public Bills(Orders orders, List<OfferedMenu> offeredMenus, List<DiscountedHistory> discountedHistories) {
        this.orders = orders;
        this.offeredMenus = offeredMenus;
        this.discountedHistories = discountedHistories;
    }

    public List<OfferedMenu> getOfferedMenus() {
        return offeredMenus;
    }

    public List<DiscountedHistory> getDiscountedHistories() {
        return discountedHistories;
    }

    public Orders getOrders() {
        return orders;
    }

    public static class OfferedMenu {
        private final Menu menu;
        private final long quantity;

        public OfferedMenu(Menu menu, long quantity) {
            this.menu = menu;
            this.quantity = quantity;
        }

        public String getNameOfMenu() {
            return menu.getName();
        }

        public long getQuantity() {
            return quantity;
        }
    }

    public static class DiscountedHistory {
        private final String eventName;
        private final long discountedAmount;

        public DiscountedHistory(String eventName, long discountedAmount) {
            this.eventName = eventName;
            this.discountedAmount = discountedAmount;
        }

        public String getEventName() {
            return eventName;
        }

        public long getDiscountedAmount() {
            return discountedAmount;
        }
    }
}
