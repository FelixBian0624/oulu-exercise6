import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;

public class Pizzeria {
    private static final String DEF_ORDER_ID = "DEF-SOH-099";
    private static final String DEF_PIZZA_INGREDIENTS = "Mozzarella Cheese";
    private static final double DEF_ORDER_TOTAL = 15.00;

    public String storeName;
    public String storeAddress;
    public String storeEmail;
    public String storePhone;
    public List<String> storeMenu;
    public List<String> pizzaIngredients;
    public double pizzaPrice;
    public List<String> sides;
    public List<String> drinks;
    private String orderID;
    private double orderTotal;
    private String specialPizza;
    private String specialSide;
    private double specialPrice;

    public Pizzeria() {
        this.storeName = "Slice-o-Heaven";
        this.storeAddress = "Unknown";
        this.storeEmail = "N/A";
        this.storePhone = "N/A";
        this.storeMenu = new ArrayList<>();
        this.pizzaIngredients = new ArrayList<>();
        this.pizzaIngredients.add(DEF_PIZZA_INGREDIENTS);
        this.pizzaPrice = 0.0;
        this.sides = new ArrayList<>();
        this.sides.add("None");
        this.drinks = new ArrayList<>();
        this.drinks.add("None");
        this.orderID = DEF_ORDER_ID;
        this.orderTotal = DEF_ORDER_TOTAL;
    }

    public Pizzeria(String storeName, String storeAddress, String storeEmail, String storePhone) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeEmail = storeEmail;
        this.storePhone = storePhone;
        this.storeMenu = new ArrayList<>();
        this.pizzaIngredients = new ArrayList<>();
        this.pizzaPrice = 0.0;
        this.sides = new ArrayList<>();
        this.drinks = new ArrayList<>();
        this.orderID = DEF_ORDER_ID;
        this.orderTotal = DEF_ORDER_TOTAL;
    }

    public Pizzeria(String orderID, List<String> pizzaIngredients, double orderTotal) {
        this.storeName = "Slice-o-Heaven";
        this.storeAddress = "Unknown";
        this.storeEmail = "N/A";
        this.storePhone = "N/A";
        this.storeMenu = new ArrayList<>();
        this.pizzaIngredients = pizzaIngredients;
        this.pizzaPrice = 0.0;
        this.sides = new ArrayList<>();
        this.sides.add("None");
        this.drinks = new ArrayList<>();
        this.drinks.add("None");
        this.orderID = orderID;
        this.orderTotal = orderTotal;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void takeOrder() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter three ingredients for your pizza (use spaces to separate ingredients):");
        String[] ingredients = scanner.nextLine().split("\\s+");
        String ing1 = ingredients.length > 0 ? ingredients[0] : "None";
        String ing2 = ingredients.length > 1 ? ingredients[1] : "None";
        String ing3 = ingredients.length > 2 ? ingredients[2] : "None";
        this.pizzaIngredients = new ArrayList<>();
        this.pizzaIngredients.add(ing1);
        this.pizzaIngredients.add(ing2);
        this.pizzaIngredients.add(ing3);

        System.out.println("Enter size of pizza (Small, Medium, Large):");
        String pizzaSize = scanner.nextLine();
        switch (pizzaSize.toLowerCase()) {
            case "small": this.pizzaPrice = 8.00; break;
            case "medium": this.pizzaPrice = 10.00; break;
            case "large": this.pizzaPrice = 12.00; break;
            default: this.pizzaPrice = 10.00; // Default to medium if invalid
        }

        System.out.println("Do you want extra cheese (Y/N):");
        String extraCheese = scanner.nextLine();
        if (extraCheese.equalsIgnoreCase("Y")) {
            this.pizzaPrice += 2.00;
            this.pizzaIngredients.add("Extra Cheese");
        }

        System.out.println("Enter one side dish (Calzone, Garlic bread, None):");
        String sideDish = scanner.nextLine();
        this.sides = new ArrayList<>();
        this.sides.add(sideDish);

        System.out.println("Enter drinks (Cold Coffee, Cocoa drink, Coke, None):");
        String drink = scanner.nextLine();
        this.drinks = new ArrayList<>();
        this.drinks.add(drink);

        this.orderID = generateOrderID();
        calculatedOrderTotal();

        System.out.println("Would you like the chance to pay only half for your order? (Y/N):");
        String wantDiscount = scanner.nextLine();
        if (wantDiscount.equalsIgnoreCase("Y")) {
            isItYourBirthday();
        } else {
            makeCardPayment();
        }
    }

    private String generateOrderID() {
        return "ORD" + System.currentTimeMillis();
    }

    private void calculatedOrderTotal() {
        this.orderTotal = pizzaPrice;
        for (String side : sides) {
            if (!side.equalsIgnoreCase("None")) this.orderTotal += 5.00;
        }
        for (String drink : drinks) {
            if (!drink.equalsIgnoreCase("None")) this.orderTotal += 3.00;
        }
    }

    private void isItYourBirthday() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your birthday (YYYY-MM-DD):");
        String birthdateInput = scanner.nextLine();
        LocalDate birthdate = LocalDate.parse(birthdateInput);
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthdate, today);
        int years = age.getYears();

        boolean isBirthdayToday = birthdate.getMonth() == today.getMonth() && birthdate.getDayOfMonth() == today.getDayOfMonth();
        if (years < 18 && isBirthdayToday) {
            System.out.println("Congratulations! You pay only half the price for your order");
            this.orderTotal /= 2;
        } else {
            System.out.println("Too bad! You do not meet the conditions to get our 50% discount");
        }
    }

    private void makeCardPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        long cardNumber = scanner.nextLong();
        scanner.nextLine(); // 清空缓冲区
        System.out.println("Enter card expiry date (MM/YY):");
        String expiryDate = scanner.nextLine();
        System.out.println("Enter CVV (3 digits):");
        int cvv = scanner.nextInt();
        processCardPayment(cardNumber, expiryDate, cvv);
    }

    public void processCardPayment(long cardNumber, String expiryDate, int cvv) {
        String cardNumberStr = Long.toString(cardNumber);
        int cardLength = cardNumberStr.length();
        if (cardLength == 14) {
            System.out.println("Card accepted");
        } else {
            System.out.println("Invalid card");
            return;
        }

        int firstCardDigit = Integer.parseInt(cardNumberStr.substring(0, 1));
        System.out.println("First digit of card: " + firstCardDigit);

        long blacklistedNumber = 12345678901234L;
        if (cardNumber == blacklistedNumber) {
            System.out.println("Card is blacklisted. Please use another card");
            return;
        }

        int lastFourDigits = Integer.parseInt(cardNumberStr.substring(cardNumberStr.length() - 4));
        System.out.println("Last four digits: " + lastFourDigits);

        StringBuilder cardNumberToDisplay = new StringBuilder(cardNumberStr);
        for (int i = 1; i < cardLength - 4; i++) {
            cardNumberToDisplay.setCharAt(i, '*');
        }
        System.out.println("Card number to display: " + cardNumberToDisplay.toString());
    }

    public void specialOfTheDay(String pizzaOfTheDay, String sideOfTheDay, double specialPrice) {
        this.specialPizza = pizzaOfTheDay;
        this.specialSide = sideOfTheDay;
        this.specialPrice = specialPrice;
        StringBuilder specialInfo = new StringBuilder();
        specialInfo.append("Today's Special: ")
                   .append(pizzaOfTheDay)
                   .append(" with ")
                   .append(sideOfTheDay)
                   .append(" for $")
                   .append(String.format("%.2f", specialPrice));
        System.out.println(specialInfo.toString());
    }

    private void printReceipt() {
        System.out.println("\nReceipt for Order ID: " + orderID);
        System.out.println("Pizzeria: " + storeName);
        System.out.println("Address: " + storeAddress);
        System.out.println("Email: " + storeEmail);
        System.out.println("Phone: " + storePhone);
        System.out.println("Pizza Ingredients: " + String.join(", ", pizzaIngredients));
        System.out.println("Sides: " + String.join(", ", sides));
        System.out.println("Drinks: " + String.join(", ", drinks));
        System.out.println("Order Total: $" + String.format("%.2f", orderTotal));
    }

    public void displayReceipt() {
        printReceipt();
    }

    public void makePizza() {
        System.out.println("Making a pizza with the following ingredients: " + String.join(", ", pizzaIngredients));
    }

    public static void main(String[] args) {
        Pizzeria sliceOHeaven = new Pizzeria("Slice-o-Heaven", "123 Pizza St, Pizzaville", "contact@sliceoheaven.com", "123-456-7890");
        System.out.println("测试 takeOrder 方法：");
        sliceOHeaven.takeOrder();
        sliceOHeaven.makePizza();
        sliceOHeaven.displayReceipt();

        System.out.println("\n测试每日特价：");
        sliceOHeaven.specialOfTheDay("Margherita Pizza", "Fries", 12.99);
    }
}