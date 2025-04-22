# 엉망진창 날씨 앱 - 버그 및 오류 목록

## 코드 구조 및 아키텍처 문제

1. **GlobalScope 사용 (DataProcessor.kt)**

   - viewModelScope을 임포트했음에도 GlobalScope 사용
   - 메모리 누수 및 생명주기 문제 발생 가능성 높음
   - ViewModel이 파괴될 때 코루틴 작업이 취소되지 않음

2. **하드코딩된 값 사용**

   - "Seoul"과 같은 값이 코드에 하드코딩됨
   - 사용자 위치 정보를 반영하지 않음

3. **강제 언래핑(!!)**
   - `DataProcessor.kt`에서 `result.metricB!!` 강제 언래핑 사용
   - `MainActivity.kt`에서 `info!!.condition` 강제 언래핑 사용
   - metricB와 condition은 null 가능성이 있어 런타임 NullPointerException 위험 높음

## UI 관련 문제

4. **레이아웃 문제**

   - Column에 padding, alignment, fillMaxWidth 등 적용 안됨
   - 사용자 경험(UX) 저하됨
   - UI 요소의 계층 구조가 단순하고 최적화되지 않음

5. **Nullable 값 처리 미흡**

   - 날씨 상태(condition)가 null일 때 적절한 처리 부재
   - valueA는 safe call(?.)을 사용하지만, condition은 강제 언래핑(!!) 사용

6. **에러 처리 및 재시도 UI 부재**
   - 오류 발생 시 사용자에게 적절한 피드백 없음
   - 데이터 로딩 실패 시 재시도 옵션 부재

## 데이터 처리 문제

7. **임의의 NULL 값**

   - `SourceFetcher.kt`에서 데이터를 임의로 null로 설정
   - 안정적인 앱 동작을 방해

8. **예외 처리 및 로깅**

   - 예외 발생 시 간단한 println으로만 로깅
   - 시스템적인 로깅 체계 부재
   - 특정 예외 타입에 맞는 처리 부재

9. **상태 관리 문제**
   - 로딩 중, 오류, 성공 상태를 명확히 구분하지 않음
   - UI 상태와 데이터 상태가 적절히 동기화되지 않음

## 기타 문제

10. **주석 관련 문제**

    - 일부 코드에 불명확한 주석 사용
    - 코드 목적과 의도가 제대로 설명되지 않음

11. **불필요한 코드 및 최적화 부재**

    - 사용되지 않는 임포트 존재 (Modifier 등)
    - 코드 구조화 및 모듈화 미흡

12. **Preview 기능 미흡**
    - DefaultPreview가 실제 데이터를 보여주지 않음
    - 개발 과정에서 UI 확인이 어려움
