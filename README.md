# SmartBox AI 📬🤖

SmartBox AI is an **AI-powered email client** focused on helping users cut through inbox noise. It connects securely to Gmail, analyzes messages using modern NLP models, and presents **smart summaries, prioritization, and insights** instead of raw email overload.

This project is both a **learning-focused full-stack system** and a foundation for a real, production-ready product.

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
* Makes it easy to add support for **non-Gmail providers** later

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
* Python or
