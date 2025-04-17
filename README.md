# My Device Info (DeviceStatusViewer)

## 1. Project Overview (프로젝트 개요)

- **앱 이름:** My Device Info (또는 DeviceStatusViewer)
- **목적:** 사용자의 디바이스 상태(배터리, 네트워크, OS, 저장 공간, 위치 권한)를 한눈에 보여주는 Android 앱
- **타깃 사용자:** 일반 사용자, 개발자, 테스트 목적의 QA
- **사용 기술:** Kotlin 기반 / MVVM 아키텍처

## 2. Core Functionality (핵심 기능)

- **🔋 배터리 정보 확인:** 배터리 잔량, 충전 상태 조회 (`BatteryManager`)
- **📶 네트워크 상태 확인:** Wi-Fi 연결 여부, 네트워크 타입 (`ConnectivityManager`)
- **📱 디바이스 정보:** 모델명, 제조사, OS 버전, API 레벨 (`Build.VERSION`, `Build.MODEL`)
- **💾 저장 공간 조회:** 내부 저장소 총 용량 / 사용 가능 용량 (`StatFs`)
- **🌐 위치 권한 여부:** 위치 권한 허용 여부 확인 (권한 요청 UI 포함)

**⚠️ 모든 기능은 시뮬레이터에서 동작 가능한 항목으로 구성**

## 3. File Structure (폴더 구조)

```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/mydeviceinfo/  # 패키지 이름은 실제 프로젝트에 맞게 변경될 수 있음
│   │   │       ├── data/
│   │   │       │   ├── model/             # 데이터 모델 클래스
│   │   │       │   └── repository/        # 데이터 저장소 로직
│   │   │       ├── ui/
│   │   │       │   ├── view/              # Activity, Fragment
│   │   │       │   └── viewmodel/         # ViewModel 클래스
│   │   │       ├── util/                  # 유틸리티 클래스
│   │   │       └── MainActivity.kt        # 메인 액티비티
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml      # 메인 레이아웃
│   │   │   └── ... (drawable, mipmap, values 등)
│   │   └── AndroidManifest.xml
│   └── test/                              # 유닛 테스트
│   └── androidTest/                       # 통합 테스트
├── build.gradle (Project)
└── build.gradle (App)
```

## 4. Build & Run (빌드 및 실행)

1. Android Studio에서 프로젝트를 엽니다.
2. 디바이스 또는 에뮬레이터를 연결합니다.
3. 'Run' 버튼을 클릭하여 앱을 빌드하고 실행합니다.
