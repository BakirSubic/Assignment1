public class GooglePlayStoreApps {
    private final String AppName;

    private final String AppID;

    private final String Category;
    private final Double Rating;
    private final Integer RatingCount;
    private final String Installs;
    private final Integer MinInstalls;
    private final Integer MaxInstalls;
    private final String Free;
    private final String Price;
    private final String Currency;
    private final String Size;
    private final String MinAndroid;
    private final String DeveloperID;
    private final String DeveloperWeb;
    private final String DeveloperEmail;
    private final String Released;
    private final String LastUpdated;
    private final String ContentRating;
    private final String PrivacyPolicy;
    private final Boolean AdSupported;
    private final Boolean InAppPurchases;
    private final Boolean EditorsChoice;
    private final String ScrapedTime;

    public GooglePlayStoreApps(String appName, String appID, String category,double rating ,Integer ratingCount, String installs, Integer minInstalls, Integer maxInstalls, String free, String price, String currency, String size, String minAndroid, String developerID, String developerWeb, String developerEmail, String released, String lastUpdated, String contentRating, String privacyPolicy, Boolean adSupported, Boolean inAppPurchases, Boolean editorsChoice, String scrapedTime) {
        this.AppName = appName;
        this.AppID = appID;
        this.Category = category;
        this.Rating = rating;
        this.RatingCount = ratingCount;
        this.Installs = installs;
        this.MinInstalls = minInstalls;
        this.MaxInstalls = maxInstalls;
        this.Free = free;
        this.Price = price;
        this.Currency = currency;
        this.Size = size;
        this.MinAndroid = minAndroid;
        this.DeveloperID = developerID;
        this.DeveloperWeb = developerWeb;
        this.DeveloperEmail = developerEmail;
        this.Released = released;
        this.LastUpdated = lastUpdated;
        this.ContentRating = contentRating;
        this.PrivacyPolicy = privacyPolicy;
        this.AdSupported = adSupported;
        this.InAppPurchases = inAppPurchases;
        this.EditorsChoice = editorsChoice;
        this.ScrapedTime = scrapedTime;
    }
    public String getCategory() {
        return Category;
    }

    public String getAppID() {
        return AppID;
    }

    public String getDeveloperEmail() {
        return DeveloperEmail;
    }

    public String getPrice() {
        return Price;
    }

    public String getInstalls() {
        return Installs;
    }

    public String getFree() {
        return Free;
    }

    public String getAppName() {
        return AppName;
    }
    public Double getRating() {
        return Rating;
    }

}
