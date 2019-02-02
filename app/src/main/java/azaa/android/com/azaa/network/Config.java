package azaa.android.com.azaa.network;

public class Config {

    private static final String HTTP_PROTOCOL = "https://";
    private static final String LOCATION = "newhabari.000webhostapp.com/";
    private static final String FILE = "azaa/";

    private static final String BASE_URL =HTTP_PROTOCOL+LOCATION+FILE;
    public static final String ITEMS_URL = BASE_URL+"getall.php";
    public static final String ITEMS_C= BASE_URL+"getc.php";
    public static final String ITEMS_E= BASE_URL+"gete.php";
    public static final String FRAG_URL= BASE_URL +"getany.php?c=";
    public static final String ALL_ITEMS = BASE_URL +"getall.php";

    public static final String TAG = "RESPONSE";
    public static final String IMAGE_URL =BASE_URL+"images/";
    public static final String UPLOAD_URL = BASE_URL+"insert.php";
    public static final String MY_PREFS_NAME ="PREFF";
    private static final String _FRAGMENT_STATE = "FRAGMENT_STATE";

    public static final String HELP_URL = HTTP_PROTOCOL+"zedoo.000webhostapp.com/ura";
    public static final String TERMS_URL = HTTP_PROTOCOL+"zedoo.000webhostapp.com/ura/index.html#features";

    //Google Ads ID
    public static final String AD_MOBI_APP_ID ="ca-app-pub-8221065292634424~1227538494";


    //Permission Request id's
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    public static final int  MY_PERMISSIONS_REQUEST_MAKE_CALLS = 1;
    public static final int  MY_PERMISSIONS_REQUEST_SEND_MESSAGE= 2;
    public static final int  MY_PERMISSIONS_REQUEST_SEND_EMAILS = 3;

    //Timeouts
    public static final Integer READ_TIMEOUT=45000;
    public static final Integer CONNECTION_TIMEOUT=450000;


}
