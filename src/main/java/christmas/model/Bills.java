package christmas.model;

import java.util.List;

public class Bills {
    private final Orders orders;
    private final List<OfferedMenu> offeredMenus;
    private final List<DiscountedHistory> discountedHistories;
    private final Badge badge;

    public Bills(Orders orders, List<OfferedMenu> offeredMenus, List<DiscountedHistory> discountedHistories,
                 Badge badge) {
        this.orders = orders;
        this.offeredMenus = offeredMenus;
        this.discountedHistories = discountedHistories;
        this.badge = badge;
    }

    public String getNameOfBadge() {
        if (badge == null) {
            return null;
        }
        return this.badge.name();
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
