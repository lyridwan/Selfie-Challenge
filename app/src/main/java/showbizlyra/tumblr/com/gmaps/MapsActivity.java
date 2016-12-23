package showbizlyra.tumblr.com.gmaps;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    //Gmaps variable
    private GoogleMap mMap;

    //Permision Request
    private static final int MY_PERMISSIONS_REQUEST = 99; //int bebas, maks 1 byte

    //User Location
    private Location myLoc;

    //Google API, to take user location
    GoogleApiClient mGoogleApiClient;

    //Frequently request to get latest user location
    LocationRequest mLocationRequest;

    //ArrayList to store Marker
    private List<Marker> markers = new ArrayList<Marker>();

    //Arraylist temp Var
    private Marker m;

    // dummy Location
    private static final LatLng mkDummy = new LatLng(-6.8710681, 107.6399409);
    private static final LatLng mkRumah = new LatLng(-6.8711124, 107.6386405);

    //marker around GIK
    private static final LatLng mkGIK = new LatLng(-6.860418, 107.589889);
    private static final LatLng mkFPOK= new LatLng(-6.860778,107.5899322);
    private static final LatLng mkFPBS= new LatLng(-6.8609252, 107.5936627);
    private static final LatLng mkFIP= new LatLng(-6.8608252, 107.5908414);
    private static final LatLng mkAlFurqon= new LatLng(-6.8630013, 107.5934672);
    private static final LatLng mkJICA = new LatLng(-6.861539, 107.5899495);
    private static final LatLng mkLabSchool = new LatLng(-6.8616725, 107.5923246);
    private static final LatLng mkKOPMA = new LatLng(-6.8612098, 107.592216);
    private static final LatLng mkWarung79 = new LatLng(-6.8635493, 107.589679);
    private static final LatLng mkKostAgung = new LatLng(-6.863971, 107.5890346);

    // Radius
    private final float radius = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
        createLocationRequest();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    /**
     * Jika maps sudah ready
     * Berisi marker2 yang ditambahkan secara manual
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /**
         * Marker Dummy
         * Marker yang ada sekitar Rumah (testing purpose)
         */
        m = mMap.addMarker(new MarkerOptions()
                .position(mkDummy)
                .title("Mesjid An-Nafi")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);

        m = mMap.addMarker(new MarkerOptions()
                .position(mkRumah)
                .title("Home Sweet Home")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);


        /**
         * Marker around UPI
         * Marker yang ada disekitar UPI
         */
        m = mMap.addMarker(new MarkerOptions()
                .position(mkGIK)
                .title("Gedung Ilmu Komputer FPMIPA-C")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);

        m = mMap.addMarker(new MarkerOptions()
                .position(mkWarung79)
                .title("Warung 79")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);

        m = mMap.addMarker(new MarkerOptions()
                .position(mkKostAgung)
                .title("Agung's House")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);

        m = mMap.addMarker(new MarkerOptions()
                .position(mkFPOK)
                .title("FPOK UPI")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);

        m = mMap.addMarker(new MarkerOptions()
                .position(mkFIP)
                .title("FIP UPI")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);

        m = mMap.addMarker(new MarkerOptions()
                .position(mkJICA)
                .title("FPMIPA-A UPI")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);

        m = mMap.addMarker(new MarkerOptions()
                .position(mkFPBS)
                .title("FPBS UPI")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);

        m = mMap.addMarker(new MarkerOptions()
                .position(mkKOPMA)
                .title("KOPMA BS UPI")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);

        m = mMap.addMarker(new MarkerOptions()
                .position(mkLabSchool)
                .title("Lab School UPI")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);

        m = mMap.addMarker(new MarkerOptions()
                .position(mkAlFurqon)
                .title("Mesjid Al-Furqon")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal)));
        markers.add(m);


        // Add icon to my Location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //Seting kamera agar bisa mengarah ke lokasi user (icon pojok kanan atas)
        mMap.setMyLocationEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mkGIK, 17));

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }


    /**
     * Ketika User mengklik Marker yang ada di map
     * Maka akan di cek, apakah marker berada pada radius atau tidak
     * Jika ya, maka ketika di klik akan muncul dialog yg mengarah ke activity lain
     * jika tidak tampilkan toast diluar radius
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        float[] results = new float[1];

        //perhitungan jarak antara lokasi user dan marker yang di klik
        Location.distanceBetween(myLoc.getLatitude(), myLoc.getLongitude(), marker.getPosition().latitude, marker.getPosition().longitude, results);


        float distanceInMeters = results[0];
        //jika marker yang diklik berada dalam radius maka tampilkan dialog yang mengarah ke activity lain
        if(distanceInMeters < radius){
            final CharSequence[] dialogitem = {"Take a Selfie", "Mark as Done", "Cancel"};
            final AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

            builder.setTitle("Actions");

            //click handler, ketika pilihan alert dialog
            builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch(item){
                        case 0 :
                            Intent i = new Intent(getApplicationContext(), CameraActivity.class);
//                            i.putExtra("nama", selection);
                            startActivity(i);
                            break;

                        case 1 :
                            marker.remove();
                            markers.remove(marker);
                            //reserve kodingan jika ingin menghapus marker
//                              Toast.makeText(this, "//code deleting marker ", Toast.LENGTH_SHORT).show();
//                            Intent in = new Intent(getApplicationContext(), UpdateBiodata.class);
//                            in.putExtra("nama", selection);
//                            startActivity(in);
                            break;

                        case 2 :

                            break;
                    }
                }
            });


            //menampilkan alert dialog
            builder.create().show();

        }else{
            Toast.makeText(this, "Challenge not available, get closer... ", Toast.LENGTH_SHORT).show();
        }


        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    /**
     * Request user Location
     * Langkah2 hampir sama dengan yg dimodul mobprog materi lokasiRealtime
     * Pertama, get permission
     * Kedua, createlocationrequest (untuk frequently request location)
     * Ketiga, ambilLokasi untuk update UI
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    //muncul dialog & user memberikan respon (allow/deny), method ini akan dipanggil
    @Override
    public void onRequestPermissionsResult( int requestCode,
                                            String permissions[], int [] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults. length > 0
                    && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED) {

                //permission diberikan, mulai ambil lokasi
                buildGoogleApiClient();
            } else {
                //permssion tidak diberikan, tampilkan pesan
                AlertDialog ad = new AlertDialog.Builder( this ).create();
                ad.setMessage( "Tidak mendapat ijin, tidak dapat mengambil lokasi" );
                ad.show();
            }
            return ;
        }
    }

    //build googleAPI
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder( this )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi(LocationServices. API)
                .build();
    }

    /**
     * Frequently request user location
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        //10 detik sekali minta lokasi (10000ms = 10 detik)
        mLocationRequest.setInterval(10000);
        //tapi tidak boleh lebih cepat dari 5 detik
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void ambilLokasi() {
        /* mulai Android 6 (API 23), pemberian persmission
        dilakukan secara dinamik (tdk diawal)
        untuk jenis2 persmisson tertentu, termasuk lokasi
        */
        // cek apakah sudah diijinkan oleh user, jika belum tampilkan dialog
        if (ActivityCompat. checkSelfPermission ( this ,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager. PERMISSION_GRANTED )
        {
            ActivityCompat. requestPermissions( this ,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
            return ;
        }

        //set agar setiap update lokasi maka UI bisa diupdate
        //setiap ada update maka onLocationChanged akan dipanggil
        LocationServices. FusedLocationApi.requestLocationUpdates( mGoogleApiClient ,
                mLocationRequest , this );
    }

    @Override
    protected void onStart() {
        super .onStart();
        mGoogleApiClient .connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient .disconnect();
        super .onStop();
    }

    /**
     * Ketika terkoneksi dengan google maps
     * maka ambil lokasi
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // get location
        ambilLokasi();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    /**
     * Ketika lokasi berubah
     * Maka, kita akan mengecek setiap marker
     * Akan looping marker yang ada di arraylist, apakah jaraknya sudah dekat dengan user atau belum,
     * Jika dekat maka icon+snippet berubah
     * Jika jauh, icon+snippet kembali menjadi default
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        myLoc = location;

        //for sebanyak marker yang ada di arraylist
        for(int i=0; i<markers.size();i++){
            //variable untuk menampung jarak
            float[] results = new float[1];

            //proses perhitungan jarak antara lokasi user dan lokasi marker sesuai index arraylist
            Location.distanceBetween(myLoc.getLatitude(), myLoc.getLongitude(), markers.get(i).getPosition().latitude, markers.get(i).getPosition().longitude, results);

            //penampungan hasil dr perhitungan jarak
            float distanceInMeters = results[0];

            //jika berada dalam radius 20meter, ganti icon +snippet
            if(distanceInMeters < radius){
                markers.get(i).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cam_closer));
                markers.get(i).setSnippet("Take a Selfie!");
            }else{
                markers.get(i).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cam_normal));
                markers.get(i).setSnippet("Selfie Challenge is Here!");
//                Toast.makeText(this, "Di luar Radius" + myLoc.getLatitude(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
