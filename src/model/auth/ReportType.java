package model.auth;

/**
 * Created by Nkosi Kee on 10/13/2016.
 */
public enum ReportType {
    MAKEAVAI("Submit Water Source Report","../view/WaterAvailabilityReport.fxml"),
    VIEWAVAI("View Water Source Reports","../view/WaterSourcesReport.fxml"),
    MAKEPURI("Submit Water Purity Report","../view/WaterPurityReport.fxml"),
    VIEWHIST("View Historical Report","../view/HistoricalReport.fxml"),
    VIEWREPS("View Submitted Reports","../view/SubmittedReports.fxml"),
    VIEWUSERS("View All Users","../view/UserManagement.fxml"),
    VIEWSECUR("View Security Reports","../view/SecurityReports.fxml");


    private String title;
    private String path;

    /**
     * Create role
     * @param title is the string representation of the Role
     */
    private ReportType(String title,String path) {
        this.title = title;
        this.path = path;
    }

    //************* Getters***********
    public String getTitle() {
        return title;
    }
    public String getPath() {
        return path;
    }

    /**
     * Returns String representation
     * @return the string representation of the role.
     */
    @Override
    public String toString() {
        return this.title;
    }
}
