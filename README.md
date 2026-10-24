# San Jose TODA — Official Municipal Fare Guide & Matrix

[![Android](https://img.shields.io/badge/Platform-Android_API_37-3DDC84.svg?style=flat&logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF.svg?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Material Design 3](https://img.shields.io/badge/UI-Material_Design_3-795548.svg?style=flat&logo=material-design&logoColor=white)](https://m3.material.io)
[![Live Web Portal](https://img.shields.io/badge/Live_Portal-GitHub_Pages-22c55e.svg?style=flat&logo=githubpages&logoColor=white)](https://kylho1231.github.io/TODA/)
[![GitHub Release](https://img.shields.io/badge/Download_APK-v1.0.0-blue.svg?style=flat&logo=android&logoColor=white)](https://github.com/kylho1231/TODA/releases/latest)

> **Official Municipal Tricycle Fare Guide & Calculator Application** for commuters, students, senior citizens, and tricycle operators across **San Jose de Buenavista, Antique, Philippines**.  
> Governed by **Municipal Ordinance No. 2022-18** and **Municipal Ordinance No. 2024-01**.

---

## 🚀 Live Access & Downloads

* 🌐 **Live Web Application & Calculator:** [https://kylho1231.github.io/TODA/](https://kylho1231.github.io/TODA/)  
  *(Works directly on any mobile browser, iPhone, or desktop without installation!)*
* 📲 **Direct Android APK Download (v1.0.0):** [Download SanJoseTODA-v1.0.0.apk](https://github.com/kylho1231/TODA/releases/download/v1.0.0/SanJoseTODA-v1.0.0.apk)  
  *Compatible with Android 7.0 (Nougat, API 24) through Android 15 / 16 (API 37).*

---

## 📌 Problem & Purpose

Commuters and students throughout San Jose de Buenavista frequently encounter disputes regarding irregular tricycle fares, unclear per-kilometer adjustments, and unauthorized "special trip / pakyaw" charges. 

**San Jose TODA** provides:
1. **Instant Regulated Fare Calculation:** Point-to-point calculation between Poblacion (Town Proper) and all 25+ barangays and major landmarks.
2. **Automated 20% Statutory Discounts:** Automatic discount calculation for **Students (with valid ID)**, **Senior Citizens (60+)**, and **Persons with Disability (PWD)**.
3. **Transparent Pakyaw / Special Trip Rules:** Clarifies exclusive solo/charter rates (3-seat standard charter multiplier) to protect commuters from excessive overcharging.
4. **Official Municipal Tariff Matrix:** Searchable, full-text database of regulated rates per barangay, covering distances, landmarks, and responsible TODA associations.
5. **Direct Incident Reporting Hotlines:** Direct dial to the **San Jose Traffic Management Office (TMO)** and **San Jose Municipal Police Station (PNP)** with penalty references under MO 2022-18.

---

## 🏛️ Municipal Ordinances & Fare Structure

### 1. Base Rates (Municipal Ordinance No. 2022-18)
* **First 2.0 kilometers:** ₱15.00 regular / ₱12.00 discounted
* **Succeeding kilometers:** Regulated graduated tariffs based on distance and road elevation.

### 2. Special Expansion (Municipal Ordinance No. 2024-01)
* Added official routes for upland and rural areas (Barangay Igbonglo, Pantao, and Lugutan) regulated under the *So-ong Pantao Igbonglo Lugutan TODA*.

### 3. Statutory Discounts (RA 11314, RA 9994, RA 10754)
* **20% statutory discount** applies at all times to:
  * **Students:** Valid school ID or registration form.
  * **Senior Citizens:** OSCA ID or valid government ID indicating age 60+.
  * **PWD:** PWD identification card.

### 4. Overcharging Penalties (MO 2022-18)
* **1st Offense:** ₱1,000 fine and mandatory driver counseling.
* **2nd Offense:** ₱1,500 fine and 30-day suspension of TODA franchise.
* **3rd Offense:** ₱2,500 fine and permanent revocation of Motorized Tricycle Operator's Permit (MTOP).

---

## 🛠️ Architecture & Tech Stack

### Android Application
* **Language:** Kotlin (100% idiomatic Kotlin)
* **Target SDK:** Android 16 (API 37) | **Min SDK:** Android 7.0 (API 24)
* **UI Design System:** Material Design 3 (Forest Green Theme)
* **View Layer:** Android ViewBinding & Material Components
* **List Presentation:** RecyclerView with custom `RouteFareAdapter` and DiffUtil
* **Architecture:** Clean Architecture with Repository pattern (`SanJoseFareRepository`, `FareCalculator`, `PassengerType`, `TripType`)

### Web Portal (GitHub Pages)
* **Stack:** HTML5, CSS3 (Material 3 Design Tokens), Vanilla JavaScript (ES6+)
* **PWA:** Web App Manifest for mobile home-screen installation
* **Hosting:** Automated deployment to GitHub Pages via GitHub Actions

---

## 🚦 Emergency & Reporting Hotlines

| Office | Purpose | Hotline |
|---|---|---|
| **San Jose Traffic Management Office (TMO)** | Franchising, fare disputes &amp; driver compliance | **0917-846-8632** / **(036) 540-8000** |
| **San Jose Municipal Police Station (PNP)** | Commuter security &amp; urgent assistance | **0998-598-6288** / **(036) 540-9110** |

---

## 💻 Local Android Development Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/kylho1231/TODA.git
   cd TODA
   ```

2. **Open in Android Studio:**
   * Select **Open an existing Android Studio project**.
   * Choose the cloned `TODA` directory.
   * Allow Gradle to sync.

3. **Run or Build APK:**
   * To build debug APK:
     ```bash
     ./gradlew assembleDebug
     ```
   * The generated APK will be placed in `app/build/outputs/apk/debug/app-debug.apk`.

---

## 👨‍💻 Author

**Jhon Kylho Estrabo**  
*BS Information Technology · St. Anthony's College*  
San Jose de Buenavista, Antique, Philippines  
* **Portfolio:** [https://kylho1231.github.io/portfolio/](https://kylho1231.github.io/portfolio/)  
* **GitHub:** [@kylho1231](https://github.com/kylho1231)  
* **Email:** [jhonkylhoestraboofficial@gmail.com](mailto:jhonkylhoestraboofficial@gmail.com)

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
