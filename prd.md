✅ 1. Project Overview (프로젝트 개요)

오늘의 한 문장 메모장

- 사용자가 하루에 한번만 메모를 할 수 있다.
- 정말 단 한번만 기록이 되어야한다.
- 미니멀 감성

⸻

✅ 2. Core Functionality (핵심 기능)

- 메모 작성 및 저장 수정
- 내부 저장소에서 기록 카운트 (1일 1회)
- 이전에 작성한 메모 확인

⸻

✅ 3. Docs

- 필요한 참고 사항을 직접 파악하여 작성하세요.
- 언어: Kotlin
- 아키텍처: MVVM (Model-View-ViewModel)
- 주요 라이브러리:
  - Jetpack Compose (UI)
  - ViewModel (UI 상태 관리 및 비즈니스 로직)
  - Room (데이터 영속성 - 메모 저장)
  - LiveData 또는 Kotlin Flow (데이터 관찰)
  - Hilt (의존성 주입)
  - SharedPreferences (1일 1회 작성 제한 플래그 관리)
- 1일 1회 작성 제한 로직:
  - 메모 저장 시, 현재 날짜를 `SharedPreferences`에 기록합니다.
  - 앱 실행 시 또는 메모 작성 화면 진입 시, `SharedPreferences`에 저장된 날짜와 현재 날짜를 비교하여 오늘 이미 작성했는지 확인합니다. 날짜가 다르면 작성을 허용하고, 같으면 제한합니다.

⸻

✅ 4. File Structure (폴더 구조)

- MVVM 디자인 패턴으로 구조 생성

```
app/
├── java/com/example/todayonelinememo/
│   ├── data/                    # 데이터 관리 계층
│   │   ├── model/               # 데이터 모델 (Room Entity)
│   │   │   └── Memo.kt
│   │   ├── local/               # 로컬 데이터 소스
│   │   │   ├── dao/             # Data Access Object
│   │   │   │   └── MemoDao.kt
│   │   │   ├── db/              # Room Database
│   │   │   │   └── AppDatabase.kt
│   │   │   └── prefs/           # SharedPreferences 관리
│   │   │       └── AppPreferences.kt
│   │   └── repository/          # 데이터 저장소 (추상화)
│   │       ├── MemoRepository.kt
│   │       └── impl/
│   │           └── MemoRepositoryImpl.kt
│   ├── ui/                      # UI 계층
│   │   ├── theme/               # Compose 테마
│   │   ├── view/                # 화면 (Composable 함수)
│   │   │   ├── MainScreen.kt
│   │   │   └── HistoryScreen.kt
│   │   └── viewmodel/           # ViewModel
│   │       ├── MainViewModel.kt
│   │       └── HistoryViewModel.kt
│   ├── di/                      # 의존성 주입 (Hilt Modules)
│   │   └── AppModule.kt
│   ├── util/                    # 유틸리티 클래스
│   │   └── DateUtil.kt
│   └── MainActivity.kt          # 메인 액티비티
└── res/                         # 리소스 폴더
```
