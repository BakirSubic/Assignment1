import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        File file1 = new File("input/GooglePlayStoreApps.csv");

        try {
            Scanner scanner = new Scanner(file1);

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            Pattern pattern = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            Map<String, Integer> categoryCounts = new HashMap<>();
            Map<String, Integer> companyAppCounts = new HashMap<>();
            Map<String, Integer> developerAppCounts = new HashMap<>();
            Map<String, Double> appPrices = new HashMap<>();
            int freeAppsCount = 0;
            int notFreeAppsCount = 0;
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                try {
                    lineNum++;
                    String line = scanner.nextLine();
                    String[] data = line.split(String.valueOf(pattern));
                    String appName = data[0].trim();
                    String appId = data[1].trim();
                    String category = data[2].trim();
                    double rating = Double.parseDouble(data[3].trim());
                    Integer ratingCount = Integer.parseInt(data[4].trim());
                    String installs = data[5].trim();
                    Integer minInstalls = Integer.parseInt(data[6].trim());
                    Integer maxInstalls = Integer.parseInt(data[7].trim());
                    String isFree = data[8].trim();
                    String priceString = data[9].trim();
                    String currency = data[10].trim();
                    String size = data[11].trim();
                    String minAndroid = data[12].trim();
                    String developerID = data[13].trim();
                    String developerWeb = data[14].trim();
                    String developerEmail = data[15].trim();
                    String released = data[16].trim();
                    String lastUpdated = data[17].trim();
                    String contentRating = data[18].trim();
                    String privacyPolicy = data[19].trim();
                    Boolean adSupported = Boolean.parseBoolean(data[20].trim());
                    Boolean inAppPurchases = Boolean.parseBoolean(data[21].trim());
                    Boolean editorsChoice = Boolean.parseBoolean(data[22].trim());
                    String scrapedTime = data[23].trim();

                    GooglePlayStoreApps gp = new GooglePlayStoreApps(appName, appId, category, rating ,ratingCount, installs, minInstalls, maxInstalls, isFree, priceString, currency, size, minAndroid, developerID, developerWeb, developerEmail, released, lastUpdated, contentRating, privacyPolicy, adSupported, inAppPurchases, editorsChoice, scrapedTime);

                    categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);

                    String companyName = extractCompanyName(appId);
                    companyAppCounts.put(companyName, companyAppCounts.getOrDefault(companyName, 0) + 1);

                    developerAppCounts.put(developerEmail, developerAppCounts.getOrDefault(developerEmail, 0) + 1);

                    double price = priceString.isEmpty() ? 0.0 : Double.parseDouble(priceString);
                    appPrices.put(appId, price);

                    if (isFree.equalsIgnoreCase("TRUE")) {
                        freeAppsCount++;
                    } else {
                        notFreeAppsCount++;
                    }
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid line number: " + lineNum );
                }
            }

            scanner.close();

            // Output to CSV files
            outputToCSV(categoryCounts, "report/AppsPerCategory.csv");
            outputToCSV(sortedCompanies(companyAppCounts), "report/Top100Companies.csv");
            outputToCSV(sortedDevelopers(developerAppCounts), "report/TopDevelopersWithoutCompanies.csv");
            outputBudgetPurchases(appPrices, 1000.0, "report/PurchasesWith1000Budget.csv");
            outputBudgetPurchases(appPrices, 10000.0, "report/PurchasesWith10000Budget.csv");
            outputFreeNotFreeCounts(freeAppsCount, notFreeAppsCount, "report/FreeNotFreeCounts.csv");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void outputToCSV(Map<String, Integer> data, String filename) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        }
    }

    private static Map<String, Integer> sortedCompanies(Map<String, Integer> data) {
        return data.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(100)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
    }

    private static Map<String, Integer> sortedDevelopers(Map<String, Integer> data) {
        return data.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
    }

    private static void outputBudgetPurchases(Map<String, Double> appPrices, double budget, String filename) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            int appsPurchased = 0;
            double currentBalance = budget;
            for (Map.Entry<String, Double> entry : appPrices.entrySet()) {
                double appPrice = entry.getValue();
                if (currentBalance >= appPrice) {
                    currentBalance -= appPrice;
                    appsPurchased++;
                } else {
                    break;
                }
            }
            writer.println("Number of apps purchased with a $" + budget + " is: " + appsPurchased);
        }
    }

    private static void outputFreeNotFreeCounts(int freeAppsCount, int notFreeAppsCount, String filename) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("FreeAppsCount," + freeAppsCount);
            writer.println("NotFreeAppsCount," + notFreeAppsCount);
        }
    }

    private static String extractCompanyName(String appId) {
        String[] parts = appId.split("\\.");
        if (parts.length >= 2) {
            return parts[0] + "." + parts[1];
        }
        return appId;
    }
}
