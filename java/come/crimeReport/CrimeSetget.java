package come.crimeReport;

/**
 * Created by os4ed on 11/18/2017.
 */

public class CrimeSetget {
    String id,crime_type,location,description,status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrime_type() {
        return crime_type;
    }

    public void setCrime_type(String crime_type) {
        this.crime_type = crime_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
