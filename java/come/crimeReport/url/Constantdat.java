package come.crimeReport.url;

/**
 * Created by root on 9/11/17.
 */

public class Constantdat {

    public static String baseurl="http://103.230.103.93:8080/ejob/student/CARSystem/index.php?";
    //public static String baseurl="http://103.230.103.142:8080/ejob/student/CARSystem/index.php?";
   // public static String baseurl="http://192.168.1.2:8080/ejob/student/CARSystem/index.php?";
    public static String registration=baseurl+"registration&";
    public static String login=baseurl+"login&";
    public static String profile=baseurl+"profile&";
    public static String sEdit_profile=baseurl+"edit_profile&";
    public static String sChangePassword=baseurl+"change_password&";
    public static String sCrimeDetails=baseurl+"crime_details&";
    public static String sMissing_details=baseurl+"missing_details&";
    public static String sgetCrimeDetailsList=baseurl+"getCrimeDetails&";
    public static String sgetMissingDetailsList=baseurl+"getMissingDetails&";
}
