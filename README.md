# SmartBox AI 📬🤖

SmartBox AI is an **AI-powered email client** focused on helping users cut through inbox noise. It connects securely to Gmail, analyzes messages using modern NLP models, and presents **smart summaries, prioritization, and insights** instead of raw email overload.

This project is both a **learning-focused full‑stack system** and a foundation for a real, production‑ready product.

---

## ✨ Features

* 🔐 **Secure Gmail OAuth2 Authentication** (no password storage)
* 📥 **Smart Inbox View** with clean, minimal UI
* 🧠 **AI Email Summaries** (thread-level & message-level)
* ⭐ **Priority Classification** (important vs low-signal emails)
* 🔍 **Metadata Extraction** (senders, deadlines, action items)
* ⚡ **Backend-driven Gmail API integration** (no direct Gmail calls from client)
* 📱 **Cross-platform Flutter app** (Android / iOS / Web-ready)

---

## 🏗️ Architecture Overview

```
[ Flutter App ]
      │
      │ HTTPS (JWT / Session)
      ▼
[ Spring Boot Backend ]
      │
      ├── Gmail API (OAuth2)
      ├── AI/NLP Pipeline (Summarization & Classification)
      └── Persistence / Caching Layer
```

### Why this architecture?

* Keeps **OAuth tokens secure** on the server
* Allows **AI models to evolve independently** of the client
* Makes it easy to add support for **non‑Gmail providers** later

---

## 🧠 AI & NLP

SmartBox AI uses **pre-trained transformer models** (e.g. BERT / T5-style architectures) to:

* Summarize long email threads
* Detect urgency and intent
* Classify emails into priority buckets

The AI pipeline is designed so models can be:

* Swapped
* Fine-tuned
* Replaced with hosted or local inference

---

## 🛠️ Tech Stack

### Frontend

* **Flutter / Dart**
* Material UI
* Secure session handling

### Backend

* **Java 17+**
* **Spring Boot**
* Spring Security (OAuth2 Client)
* Gmail API
* RESTful APIs

### AI / ML

* Transformer-based NLP models
* Python or Java-based inference layer (pluggable)

### Infrastructure (Planned / Optional)

* Docker
* Reverse proxy (NGINX)
* Cloud deployment (GCP / AWS / Fly.io)

---

## 🔐 Authentication Flow

1. User logs in via Gmail OAuth
2. Google redirects to backend OAuth callback
3. Backend stores secure session / token mapping
4. Flutter app calls backend APIs only
5. Backend fetches + processes Gmail data

> 🔒 **SmartBox never stores Gmail passwords**

---

## 🚀 Getting Started

### Backend

```bash
./mvnw spring-boot:run
```

Configure the following:

* Google OAuth Client ID & Secret
* Gmail API scopes
* Redirect URIs

### Frontend

```bash
flutter pub get
flutter run
```

Ensure the backend base URL is configured correctly.

---

## 🧪 Testing Strategy

* **Unit Tests**

  * Email parsing
  * AI classification logic
  * DTO mapping

* **Integration Tests**

  * Gmail API interaction
  * OAuth2 login flow

* **Manual Testing**

  * OAuth redirect handling
  * Session expiration behavior

---

## 🧭 Roadmap

This roadmap is **capability-based**, not date-driven. It reflects how SmartBox AI evolves from a secure email client into an intelligent, provider-agnostic assistant.

### Phase 1 — Core Platform (Current)

* Gmail OAuth2 login with secure backend-managed sessions
* Gmail inbox fetch, normalization, and DTO mapping
* Basic inbox UI in Flutter
* Initial AI-powered summaries (message & thread level)
* Priority classification (important vs low-signal)

### Phase 2 — Intelligence Layer

* Thread-aware summarization (conversation context)
* Action item & deadline extraction
* Improved priority scoring using multiple signals
* Sender reputation & frequency analysis

### Phase 3 — Product Polish

* Offline caching & background refresh
* Smart notifications ("needs attention", "reply pending")
* Performance tuning & Gmail API rate-limit handling
* Improved UX for long threads and bulk actions

### Phase 4 — Agent & Expansion

* Agent-style actions (auto-draft replies, archive, label)
* Multi-provider support (Outlook, generic IMAP)
* Fine-tuned custom email models
* Plug-in style AI providers (local vs hosted)

### Explicit Non-Goals (for now)

* Replacing Gmail’s UI entirely
* Training large language models from scratch
* Acting on emails without explicit user confirmation

---

## 🎯 Project Goals

This project is designed to:

* Explore **real-world OAuth security**
* Apply **AI to a practical consumer problem**
* Build a **clean, scalable backend architecture**
* Serve as a strong **portfolio project**

---

## ⚠️ Disclaimer

SmartBox AI is not affiliated with Google or Gmail.

---

## 📄 License

MIT License

---

Built with curiosity, caffeine, and too many unread emails ☕📨
