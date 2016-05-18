package projekti;

/**
 * Ova klasa treba da sadrži sve atribute projekta
 * @author amel
 */
public class ProjekatBean {
    private int id;
    private String projectTitle;//Naziv projekta
    private String location; //gdje se odvija projekat
    private String cost;//Cjena projekta
    private String roleInProject;//uloga u projektu: Coordinator, co-applicant,affiliated entity
    private String donorsOfProject;//Ko daje donaciju
    private String ammountOfDonors;//Iznos donacije
    private String projectDuration;//Trajanje projekta npr. 2012-2016
    private String Objectives;//Sažetak ciljevi i rezultati
    //private ArrayList<TFBParticipant> paticipants;//Ucesnici sa tfb-a
    private String projectPath; 
    private String participants;
    private String fileName;//Skraćeno ime projekta    

    public ProjekatBean() { } 
    /**
     * @return the projectTitle
     */
    public String getProjectTitle() {return projectTitle;}
    /**
     * @param projectTitle the projectTitle to set
     */
    public void setProjectTitle(String projectTitle) { this.projectTitle = projectTitle; }

    /**
     * @return the location
     */
    public String getLocation() {return location;}

    /**
     * @param location the location to set
     */
    public void setLocation(String location) { this.location = location; }

    /**
     * @return the cost
     */
    public String getCost() { return cost; }

    /**
     * @param cost the cost to set
     */
    public void setCost(String cost) {  this.cost = cost;}

    /**
     * @return the roleInProject
     */
    public String getRoleInProject() {return roleInProject; }

    /**
     * @param roleInProject the roleInProject to set
     */
    public void setRoleInProject(String roleInProject) { this.roleInProject = roleInProject; }

    /**
     * @return the donorsOfProject
     */
    public String getDonorsOfProject() { return donorsOfProject; }

    /**
     * @param donorsOfProject the donorsOfProject to set
     */
    public void setDonorsOfProject(String donorsOfProject) { this.donorsOfProject = donorsOfProject;}

    /**
     * @return the ammountOfDonors
     */
    public String getAmmountOfDonors() {return ammountOfDonors;}

    /**
     * @param ammountOfDonors the ammountOfDonors to set
     */
    public void setAmmountOfDonors(String ammountOfDonors) { this.ammountOfDonors = ammountOfDonors;}

    /**
     * @return the projectDuration
     */
    public String getProjectDuration() {return projectDuration; }

    /**
     * @param projectDuration the projectDuration to set
     */
    public void setProjectDuration(String projectDuration) {this.projectDuration = projectDuration;}

    /**
     * @return the Objectives
     */
    public String getObjectives() {return Objectives;}

    /**
     * @param Objectives the Objectives to set
     */
    public void setObjectives(String Objectives) { this.Objectives = Objectives;}

    /**
     *
     * @return
     */
    public String getProjectPath() {return projectPath;}

    /**
     * @param projectPath the projectPath to set
     */
    public void setProjectPath(String projectPath) { this.projectPath = projectPath; }

    /**
     * @return the participants
     */
    public String getParticipants() { return participants; }

    /**
     * @param participants the participants to set
     */
    public void setParticipants(String participants) {this.participants = participants; }

    /**
     * Skraćeno ime projekta
     * @return the fileName
     */
    public String getFileName() {return fileName;}

    /**
     * Skraćeno ime projekta
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {this.fileName = fileName;}

    /**
     * @return the id
     */
    public int getId() { return id;}

    /**
     * @param id the id to set
     */
    public void setId(int id) { this.id = id; }
    
}
