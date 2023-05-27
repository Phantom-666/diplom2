package com.example.diplom2

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError
import android.graphics.PointF
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.RotationType

@Suppress("DEPRECATION")
class Map : AppCompatActivity()
    ,DrivingSession.DrivingRouteListener
{
    private lateinit var mapview : MapView
    private lateinit var mapObjects :MapObjectCollection
    private lateinit var locationmapkit: UserLocationLayer
    private var drivingRouter: DrivingRouter? = null
    private var drivingSession:DrivingSession? = null

    private val tabListener : MapObjectTapListener = MapObjectTapListener { first, second ->
        val intent = Intent(this, RestaurantRoute::class.java)

        intent.putExtra("latitude", second.latitude.toString())
        intent.putExtra("longitude", second.longitude.toString())

        startActivityForResult(intent, 0)
        true
    }

    private lateinit var placemark :  PlacemarkMapObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 0)
            return
        }
        val mapKit = MapKitFactory.getInstance()


        mapview = findViewById<View>(R.id.mapview) as MapView
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: android.location.Location? ->
            val userLocation = Point(location?.latitude.toString().toDouble(), location?.longitude.toString().toDouble())

            mapview.map.move(
            CameraPosition(userLocation, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null)
        }


        locationmapkit = mapKit.createUserLocationLayer(mapview.mapWindow)
        locationmapkit.isVisible = true

        mapObjects = mapview.map.mapObjects.addCollection()
        mapview.map.mapObjects.addTapListener(tabListener)

        val targetLocations = mutableListOf<Point>(
            Point(55.639369, 51.810729),
            Point(55.638079, 51.803866)
        )

        for (t in targetLocations) {

            val bitmap = this.getBitmapFromVectorDrawable(R.drawable.res_trans_50)
            this.placemark = mapview.map.mapObjects.addPlacemark(t, ImageProvider.fromBitmap(bitmap))
            this.placemark.addTapListener(this.tabListener)
        }


        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
    }

    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val action = data?.getStringExtra("action")
            val latitude = data?.getStringExtra("latitude")
            val longitude = data?.getStringExtra("longitude")


            if (action == "make_route") {
                val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 0)
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: android.location.Location? ->
                    val userLocation = Point(location?.latitude.toString().toDouble(), location?.longitude.toString().toDouble())

                    mapview.map.move(
                        CameraPosition(Point(latitude!!.toDouble(), longitude!!.toDouble()), 14.0f, 0.0f, 0.0f),
                        Animation(Animation.Type.SMOOTH, 0F),
                        null)


                    val drivingOptions = DrivingOptions()
                    val vehicleOptions = VehicleOptions()
                    val requestPoints:ArrayList<RequestPoint> = ArrayList()
                    requestPoints.add(RequestPoint(userLocation,
                        RequestPointType.WAYPOINT,null))
                    requestPoints.add(RequestPoint(Point(latitude!!.toDouble(), longitude!!.toDouble()),
                        RequestPointType.WAYPOINT,null))

                    if (drivingRouter == null) {

                        Toast.makeText(this, "Null", Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(this, "Прокладываю путь", Toast.LENGTH_LONG).show()
                        drivingSession = drivingRouter!!.requestRoutes(requestPoints, drivingOptions, vehicleOptions, this@Map)
                    }
                }


            }
        }
    }



    private fun getBitmapFromVectorDrawable(drawableId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(this, drawableId) ?: return null

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview.onStart()
    }

    override fun onDrivingRoutes(p0: MutableList<DrivingRoute>) {
        for(route in p0){
            mapObjects!!.addPolyline(route.geometry)
        }
    }

    override fun onDrivingRoutesError(p0: com.yandex.runtime.Error) {
        var errorMessage = "Неизвестная ошибка!"
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
    }
}