package com.example.diplom2

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import kotlin.collections.Map

class Map : AppCompatActivity()  {

    private lateinit var mapview : MapView;
    private lateinit var mapObjects :MapObjectCollection;

    private val tabListener : MapObjectTapListener = MapObjectTapListener { _, _ ->
        Log.d("Marker", "Latitude:")
        val intent = Intent(this, RestaurantRoute::class.java)
        startActivity(intent)
        true
    }

    private lateinit var placemark :  PlacemarkMapObject;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        MapKitFactory.initialize(this)

        val Target = Point(59.905218, 30.377888)

        mapview = findViewById<View>(R.id.mapview) as MapView
        mapview.map.move(
            CameraPosition(Target, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )

//        var mapkit = MapKitFactory.getInstance()
         mapObjects = mapview.map.mapObjects.addCollection()

        val tabListener = MapObjectTapListener{_, _ ->
            Log.d("Marker", "Latitude:")
            val intent = Intent(this, RestaurantRoute::class.java)
            startActivity(intent)
            true
        }


        mapview.map.mapObjects.addTapListener(tabListener)

        val bitmap = this.getBitmapFromVectorDrawable(R.drawable.ic_launcher_foreground)
        this.placemark = mapview.map.mapObjects.addPlacemark(Target, ImageProvider.fromBitmap(bitmap))
        this.placemark.addTapListener(this.tabListener)
    }

    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun getBitmapFromVectorDrawable(drawableId: Int): Bitmap? {
        var drawable = ContextCompat.getDrawable(this, drawableId) ?: return null

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable).mutate()
        }

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
}