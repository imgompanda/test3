⸻

📄 README.md 템플릿 (실습용)

# 📌 프로젝트명

간단한 프로젝트 설명 (한 줄 요약)

---

## 🔧 프로젝트 개요
- 어떤 기능을 하는 프로젝트인지 요약
- 주요 사용 기술 (예: Python, FastAPI, SQLite 등)

---

## 📁 폴더 구조
```plaintext
project-root/
├── src/
├── docs/
├── tests/
├── refactoring.md
└── README.md



⸻

🗂️ 기능 목록
	•	기능 1: XXX
	•	기능 2: YYY
	•	기능 3: ZZZ

⸻

🧠 클래스 구조 다이어그램 (Mermaid 사용 예시)

classDiagram
    class User {
        -id: int
        -name: string
        +login(): bool
    }

    class AuthService {
        +authenticate(user: User): bool
    }

    class UserRepository {
        +findById(id: int): User
    }

    User --> AuthService
    AuthService --> UserRepository



⸻

📝 주석 작성 규칙

Cursor에서 자동으로 주석을 생성할 때 따르는 기준을 정리한 문서:
docs/comment-style.md

⸻

🪛 리팩토링 계획 문서

AI가 이해할 수 있도록 작성한 리팩토링 요구사항 문서:
refactoring.md

⸻

✅ 실행 방법
	•	설치 방법
	•	실행 명령어
	•	테스트 방법

⸻

🤖 AI 도구 활용 팁
	•	어떻게 Cursor를 활용했는지 간단 요약
	•	주석/문서화 자동화 시 팁 등

⸻

📚 참고 문서
	•	기술 문서나 가이드 링크 등

---

