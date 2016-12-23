package showbizlyra.tumblr.com.gmaps;

/**
 * Created by GalihPW on 14/12/2016.
 */

public class Player {
    private String nama;
    private String score;
    private String longitude;
    private String latitude;
    private String online;


    public Player(){

    }

    public Player(String nama, String score, String longitude, String latitude, String online){
        this.nama = nama;
        this.score = score;
        this.longitude = longitude;
        this.latitude = latitude;
        this.online = online;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
