package com.example.mydeviceinfo.ui.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mydeviceinfo.data.model.*
import com.example.mydeviceinfo.data.repository.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    // Rule to execute LiveData operations synchronously
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Mocks for dependencies
    @Mock lateinit var mockApplication: Application
    @Mock lateinit var mockBatteryRepository: BatteryRepository
    @Mock lateinit var mockNetworkRepository: NetworkRepository
    @Mock lateinit var mockDeviceRepository: DeviceRepository
    @Mock lateinit var mockStorageRepository: StorageRepository
    @Mock lateinit var mockPermissionRepository: PermissionRepository

    // Mock observers for LiveData
    @Mock lateinit var batteryObserver: Observer<BatteryInfo>
    @Mock lateinit var networkObserver: Observer<NetworkInfo>
    @Mock lateinit var deviceObserver: Observer<DeviceInfo>
    @Mock lateinit var storageObserver: Observer<StorageInfo>
    @Mock lateinit var permissionObserver: Observer<LocationPermissionInfo>

    // ViewModel under test
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        // Create ViewModel with mocked dependencies (using constructor injection)
        viewModel = MainViewModel(
            mockApplication,
            mockBatteryRepository,
            mockNetworkRepository,
            mockDeviceRepository,
            mockStorageRepository,
            mockPermissionRepository
        )

        // Observe LiveData on the ViewModel instance under test
        viewModel.batteryInfo.observeForever(batteryObserver)
        viewModel.networkInfo.observeForever(networkObserver)
        viewModel.deviceInfo.observeForever(deviceObserver)
        viewModel.storageInfo.observeForever(storageObserver)
        viewModel.locationPermissionInfo.observeForever(permissionObserver)

        // Common mock setup if needed - Removed unnecessary stubbing
        // whenever(mockApplication.applicationContext).thenReturn(mockApplication)
    }

    @Test
    fun `loadBatteryInfo updates LiveData`() {
        println("\n--- 테스트 시작: 배터리 정보 로딩 --- ")
        // Arrange
        println("[Arrange] 모의 배터리 정보 생성 및 Repository 동작 설정")
        val fakeBatteryInfo = BatteryInfo(level = 80, isCharging = false)
        whenever(mockBatteryRepository.getBatteryInfo()).thenReturn(fakeBatteryInfo)

        // Act
        println("[Act] viewModel.loadBatteryInfo() 호출")
        viewModel.loadBatteryInfo()

        // Assert
        println("[Assert] batteryObserver.onChanged 호출 검증")
        verify(batteryObserver).onChanged(fakeBatteryInfo)
        println("--- 테스트 종료: 배터리 정보 로딩 --- ")
    }

    @Test
    fun `loadNetworkInfo updates LiveData`() {
        println("\n--- 테스트 시작: 네트워크 정보 로딩 --- ")
        // Arrange
        println("[Arrange] 모의 네트워크 정보 생성 및 Repository 동작 설정")
        val fakeNetworkInfo = NetworkInfo(isConnected = true, networkType = "WiFi")
        whenever(mockNetworkRepository.getNetworkInfo()).thenReturn(fakeNetworkInfo)

        // Act
        println("[Act] viewModel.loadNetworkInfo() 호출")
        viewModel.loadNetworkInfo()

        // Assert
        println("[Assert] networkObserver.onChanged 호출 검증")
        verify(networkObserver).onChanged(fakeNetworkInfo)
        println("--- 테스트 종료: 네트워크 정보 로딩 --- ")
    }

    @Test
    fun `loadDeviceInfo updates LiveData`() {
        println("\n--- 테스트 시작: 디바이스 정보 로딩 --- ")
        // Arrange
        println("[Arrange] 모의 디바이스 정보 생성 및 Repository 동작 설정")
        val fakeDeviceInfo = DeviceInfo(model = "Test Model", manufacturer = "Test Co", osVersion = "14", apiLevel = 34)
        whenever(mockDeviceRepository.getDeviceInfo()).thenReturn(fakeDeviceInfo)

        // Act
        println("[Act] viewModel.loadDeviceInfo() 호출")
        viewModel.loadDeviceInfo()

        // Assert
        println("[Assert] deviceObserver.onChanged 호출 검증")
        verify(deviceObserver).onChanged(fakeDeviceInfo)
        println("--- 테스트 종료: 디바이스 정보 로딩 --- ")
    }

    @Test
    fun `loadStorageInfo updates LiveData`() {
        println("\n--- 테스트 시작: 저장 공간 정보 로딩 --- ")
        // Arrange
        println("[Arrange] 모의 저장 공간 정보 생성 및 Repository 동작 설정")
        val fakeStorageInfo = StorageInfo(totalInternalGb = 100.0f, availableInternalGb = 50.5f)
        whenever(mockStorageRepository.getStorageInfo()).thenReturn(fakeStorageInfo)

        // Act
        println("[Act] viewModel.loadStorageInfo() 호출")
        viewModel.loadStorageInfo()

        // Assert
        println("[Assert] storageObserver.onChanged 호출 검증")
        verify(storageObserver).onChanged(fakeStorageInfo)
        println("--- 테스트 종료: 저장 공간 정보 로딩 --- ")
    }

    @Test
    fun `checkLocationPermission updates LiveData`() {
        println("\n--- 테스트 시작: 위치 권한 확인 --- ")
        // Arrange
        println("[Arrange] 모의 위치 권한 정보 생성 및 Repository 동작 설정")
        val fakePermissionInfo = LocationPermissionInfo(isGranted = true)
        whenever(mockPermissionRepository.checkLocationPermission()).thenReturn(fakePermissionInfo)

        // Act
        println("[Act] viewModel.checkLocationPermission() 호출")
        viewModel.checkLocationPermission()

        // Assert
        println("[Assert] permissionObserver.onChanged 호출 검증")
        verify(permissionObserver).onChanged(fakePermissionInfo)
        println("--- 테스트 종료: 위치 권한 확인 --- ")
    }
} 