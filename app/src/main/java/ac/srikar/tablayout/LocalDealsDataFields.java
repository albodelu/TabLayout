package ac.srikar.tablayout;


/**
 * Elements are shown in the home section as Horizontal Recycler View
 */
public class LocalDealsDataFields {

    /**
     * Individual Local Deal Title
     */
    private String localDealTitle;

    /**
     * Individual Local Deal Image
     */
    private String localDealImage;

    /**
     * Individual Local Deal Secondary Title
     */
    private String localDealSecondTitle;

    /**
     * Individual Local Deal Product Id
     */
    private int localDealId;

    public int getLocalDealId() {
        return localDealId;
    }

    public void setLocalDealId(int localDealProductId) {
        this.localDealId = localDealProductId;
    }

    public String getLocalDealTitle() {
        return localDealTitle;
    }

    public void setLocalDealTitle(String localDealTitle) {
        this.localDealTitle = localDealTitle;
    }

    public String getLocalDealImage() {
        return localDealImage;
    }

    public void setLocalDealImage(String localDealImage) {
        this.localDealImage = localDealImage;
    }

    public String getLocalDealSecondTitle() {
        return localDealSecondTitle;
    }

    public void setLocalDealSecondTitle(String localDealSecondTitle) {
        this.localDealSecondTitle = localDealSecondTitle;
    }
}
