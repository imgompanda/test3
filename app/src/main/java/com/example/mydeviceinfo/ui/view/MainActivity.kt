package com.example.mydeviceinfo.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup // Import ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout // Import LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.example.mydeviceinfo.R
import com.example.mydeviceinfo.data.repository.* // Import repositories
import com.example.mydeviceinfo.ui.viewmodel.MainViewModel
import com.example.mydeviceinfo.ui.viewmodel.MainViewModelFactory // Import factory

class MainActivity : AppCompatActivity() {

    // Create repository instances
    private val batteryRepository by lazy { BatteryRepository(applicationContext) }
    private val networkRepository by lazy { NetworkRepository(applicationContext) }
    private val deviceRepository by lazy { DeviceRepository() }
    private val storageRepository by lazy { StorageRepository(applicationContext) }
    private val permissionRepository by lazy { PermissionRepository(applicationContext) }

    // Provide the factory to viewModels delegate
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            application,
            batteryRepository,
            networkRepository,
            deviceRepository,
            storageRepository,
            permissionRepository
        )
    }

    // Views
    private lateinit var batteryLevelTextView: TextView
    private lateinit var batteryChargingTextView: TextView
    private lateinit var networkStatusTextView: TextView
    private lateinit var networkTypeTextView: TextView
    private lateinit var deviceModelTextView: TextView
    private lateinit var deviceManufacturerTextView: TextView
    private lateinit var deviceOsVersionTextView: TextView
    private lateinit var deviceApiLevelTextView: TextView
    private lateinit var storageTotalTextView: TextView
    private lateinit var storageAvailableTextView: TextView
    private lateinit var locationPermissionTextView: TextView
    private lateinit var requestLocationButton: Button
    // Sort Buttons
    private lateinit var sortDefaultButton: Button
    private lateinit var sortAzButton: Button
    private lateinit var sortTypeButton: Button

    // CardViews & Container
    private lateinit var cardsContainer: LinearLayout // Container for cards
    private lateinit var batteryCardView: CardView
    private lateinit var networkCardView: CardView
    private lateinit var deviceCardView: CardView
    private lateinit var storageCardView: CardView
    private lateinit var locationCardView: CardView
    // Store cards with their sort keys
    private lateinit var allCards: List<Pair<CardView, String>> // Pair: CardView, SortKey (Title)
    private val originalOrder: MutableList<CardView> = mutableListOf() // To restore default order

    private var defaultCardElevation: Float = 0f
    private var defaultCardTranslationZ: Float = 0f
    private var density: Float = 1f // Screen density

    // Activity Result Launcher for Permissions
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ : Boolean ->
        // Re-check permission status after user interaction
        viewModel.checkLocationPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        density = resources.displayMetrics.density // Get screen density

        initializeViews()
        storeOriginalCardOrder() // Store initial order before any sorting
        setupButtonClickListeners()
        setupCardTouchListeners() // This will now include enhanced 3D effects
        observeViewModel()
        loadAllData()
        startCardAnimations() 
    }

    private fun initializeViews() {
        batteryLevelTextView = findViewById(R.id.textView_battery_level)
        batteryChargingTextView = findViewById(R.id.textView_battery_charging)
        networkStatusTextView = findViewById(R.id.textView_network_status)
        networkTypeTextView = findViewById(R.id.textView_network_type)
        deviceModelTextView = findViewById(R.id.textView_device_model)
        deviceManufacturerTextView = findViewById(R.id.textView_device_manufacturer)
        deviceOsVersionTextView = findViewById(R.id.textView_device_os_version)
        deviceApiLevelTextView = findViewById(R.id.textView_device_api_level)
        storageTotalTextView = findViewById(R.id.textView_storage_total)
        storageAvailableTextView = findViewById(R.id.textView_storage_available)
        locationPermissionTextView = findViewById(R.id.textView_location_permission)
        requestLocationButton = findViewById(R.id.button_request_location)

        // Initialize CardViews
        batteryCardView = findViewById(R.id.card_battery)
        networkCardView = findViewById(R.id.card_network)
        deviceCardView = findViewById(R.id.card_device)
        storageCardView = findViewById(R.id.card_storage)
        locationCardView = findViewById(R.id.card_location)

        // Initialize Container
        cardsContainer = findViewById(R.id.linearLayout_cards_container)

        // Initialize Sort Buttons
        sortDefaultButton = findViewById(R.id.button_sort_default)
        sortAzButton = findViewById(R.id.button_sort_az)
        sortTypeButton = findViewById(R.id.button_sort_type)

        // Store default elevation/translationZ from the first card (assuming they are same)
        defaultCardElevation = batteryCardView.cardElevation
        defaultCardTranslationZ = batteryCardView.translationZ

        // Set camera distance for perspective
        val cameraDistance = 8000 * density
        val cardViews = listOf(batteryCardView, networkCardView, deviceCardView, storageCardView, locationCardView)
        cardViews.forEach { it.cameraDistance = cameraDistance }

        // Prepare list for sorting (CardView to its Title derived from ID)
        allCards = listOf(
            batteryCardView to "Battery",
            networkCardView to "Network",
            deviceCardView to "Device",
            storageCardView to "Storage",
            locationCardView to "Location Permission"
        )
    }

    private fun storeOriginalCardOrder() {
        // Store the initial order based on XML or allCards list
        originalOrder.addAll(allCards.map { it.first })
    }

    private fun setupButtonClickListeners() {
        requestLocationButton.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // Sort Button Listeners
        sortDefaultButton.setOnClickListener {
            reorderCards(originalOrder) // Restore original order
        }
        sortAzButton.setOnClickListener {
            val sortedCards = allCards.sortedBy { it.second }.map { it.first }
            reorderCards(sortedCards)
        }
        sortTypeButton.setOnClickListener {
            // Define custom type order (example)
            val typeOrder = mapOf(
                batteryCardView to 0, // System
                deviceCardView to 1,  // System
                storageCardView to 2, // System
                networkCardView to 3, // Connectivity
                locationCardView to 4 // Connectivity
            )
            val sortedCards = allCards.sortedBy { typeOrder[it.first] ?: Int.MAX_VALUE }.map { it.first }
            reorderCards(sortedCards)
        }
    }

    private fun reorderCards(sortedCards: List<CardView>) {
        cardsContainer.removeAllViews()
        sortedCards.forEach { cardView ->
            // Ensure the parent is null before adding back (though removeAllViews should handle this)
            (cardView.parent as? ViewGroup)?.removeView(cardView)
            cardsContainer.addView(cardView)
        }
        // Optionally, re-trigger entrance animations after sorting
        // startCardAnimations(sortedCards) // Need to adapt startCardAnimations
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupCardTouchListeners() {
        val cardViews = listOf(
            batteryCardView,
            networkCardView,
            deviceCardView,
            storageCardView,
            locationCardView
        )
        val maxTiltAngle = 10f // Slightly increased tilt angle
        val touchMoveDuration = 50L
        val touchUpDuration = 250L
        val liftAmount = 8 * density // Amount to lift the card on touch (dp to px)

        cardViews.forEach { cardView ->
            cardView.setOnTouchListener { view, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // Lift and scale up
                        view.animate()
                            .translationZ(defaultCardTranslationZ + liftAmount)
                            .scaleX(1.03f)
                            .scaleY(1.03f)
                            .setDuration(touchMoveDuration)
                            .start()
                        true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val viewWidth = view.width.toFloat()
                        val viewHeight = view.height.toFloat()
                        val touchRelX = event.x / viewWidth - 0.5f
                        val touchRelY = event.y / viewHeight - 0.5f
                        val rotationY = touchRelX * maxTiltAngle * -2f
                        val rotationX = touchRelY * maxTiltAngle * 2f
                        view.animate()
                            .rotationX(rotationX)
                            .rotationY(rotationY)
                            .setDuration(touchMoveDuration) // Keep rotation smooth during move
                            .start()
                        true
                    }
                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> {
                        // Animate back to default state
                        view.animate()
                            .rotationX(0f)
                            .rotationY(0f)
                            .translationZ(defaultCardTranslationZ)
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(touchUpDuration)
                            .start()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun observeViewModel() {
        // Observe Battery Info
        viewModel.batteryInfo.observe(this, Observer { batteryInfo ->
            batteryLevelTextView.text = "${batteryInfo.level}%"
            batteryChargingTextView.text = if (batteryInfo.isCharging) "Charging" else "Not Charging"
        })

        // Observe Network Info
        viewModel.networkInfo.observe(this, Observer { networkInfo ->
            networkStatusTextView.text = if (networkInfo.isConnected) "Connected" else "Disconnected"
            networkTypeTextView.text = networkInfo.networkType
        })

        // Observe Device Info
        viewModel.deviceInfo.observe(this, Observer { deviceInfo ->
            deviceModelTextView.text = deviceInfo.model
            deviceManufacturerTextView.text = deviceInfo.manufacturer
            deviceOsVersionTextView.text = "Android ${deviceInfo.osVersion}"
            deviceApiLevelTextView.text = "API ${deviceInfo.apiLevel}"
        })

        // Observe Storage Info
        viewModel.storageInfo.observe(this, Observer { storageInfo ->
            storageTotalTextView.text = String.format("Total: %.2f GB", storageInfo.totalInternalGb)
            storageAvailableTextView.text = String.format("Available: %.2f GB", storageInfo.availableInternalGb)
        })

        // Observe Location Permission Info
        viewModel.locationPermissionInfo.observe(this, Observer { permissionInfo ->
            locationPermissionTextView.text = if (permissionInfo.isGranted) "Granted" else "Not Granted"
            requestLocationButton.visibility = if (permissionInfo.isGranted) View.GONE else View.VISIBLE
        })
    }

    private fun loadAllData() {
        viewModel.loadBatteryInfo()
        viewModel.loadNetworkInfo()
        viewModel.loadDeviceInfo()
        viewModel.loadStorageInfo()
        viewModel.checkLocationPermission() // Check initial permission status
    }

    private fun startCardAnimations(cardsToAnimate: List<CardView> = originalOrder) {
        if (cardsToAnimate.isEmpty()) return // Avoid issues if list is empty

        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
        val initialDelay = 100L
        val staggerDelay = 80L

        cardsToAnimate.forEachIndexed { index, cardView ->
            animation.startOffset = initialDelay + index * staggerDelay
            // Ensure animation runs even if view was already visible
            cardView.clearAnimation()
            cardView.startAnimation(animation)
        }
    }
} 