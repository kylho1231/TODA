"use strict";

// San Jose de Buenavista TODA Routes (from TodaRoute.kt)
const routesData = [
  { id: "R01", origin: "Poblacion (Town Proper)", dest: "Tradetown / Public Market", km: 1.0, reg: 15.0, disc: 12.0, toda: "San Jose de Buenavista TODA - Route 1", ord: "MO No. 2022-18", landmarks: ["Municipal Hall", "Public Market", "Brgy 3"] },
  { id: "R02", origin: "Poblacion (Town Proper)", dest: "St. Anthony's College (SAC)", km: 1.5, reg: 15.0, disc: 12.0, toda: "St. Joseph TODA", ord: "MO No. 2022-18", landmarks: ["SAC Main Gate", "St. Anthony Cathedral", "San Angel"] },
  { id: "R03", origin: "Poblacion (Town Proper)", dest: "Antique National School / EBJ Gym", km: 1.5, reg: 15.0, disc: 12.0, toda: "San Jose TODA - Route 1", ord: "MO No. 2022-18", landmarks: ["ANS Campus", "EBJ Memorial Gym", "Brgy 4"] },
  { id: "R04", origin: "Poblacion (Town Proper)", dest: "Ceres Bus Terminal (Bantayan)", km: 2.2, reg: 18.0, disc: 14.4, toda: "Alliance of Concerned TODA (ACTODA)", ord: "MO No. 2022-18", landmarks: ["Bantayan Ceres Terminal", "Provincial Highway"] },
  { id: "R05", origin: "Poblacion (Town Proper)", dest: "Barangay Funda-Dalipe", km: 2.8, reg: 20.0, disc: 16.0, toda: "Funda-Dalipe TODA", ord: "MO No. 2022-18", landmarks: ["Funda-Dalipe Plaza", "Coastal Road"] },
  { id: "R06", origin: "Poblacion (Town Proper)", dest: "Barangay San Pedro", km: 3.0, reg: 20.0, disc: 16.0, toda: "San Pedro TODA", ord: "MO No. 2022-18", landmarks: ["San Pedro Parish", "San Pedro Brgy Hall"] },
  { id: "R07", origin: "Poblacion (Town Proper)", dest: "Barangay Maybato", km: 3.5, reg: 20.0, disc: 16.0, toda: "Maybato TODA", ord: "MO No. 2022-18", landmarks: ["Maybato North", "Maybato South", "Resorts"] },
  { id: "R08", origin: "Poblacion (Town Proper)", dest: "Evelio B. Javier (EBJ) Airport", km: 4.2, reg: 25.0, disc: 20.0, toda: "Airport TODA Service", ord: "MO No. 2022-18", landmarks: ["Antique Airport Terminal", "Fundan Bridge"] },
  { id: "R09", origin: "Poblacion (Town Proper)", dest: "Barangay Atabay", km: 4.5, reg: 25.0, disc: 20.0, toda: "Atabay TODA", ord: "MO No. 2022-18", landmarks: ["Atabay Elem School", "Provincial Hospital Area"] },
  { id: "R10", origin: "Poblacion (Town Proper)", dest: "Barangay Badiang", km: 4.8, reg: 25.0, disc: 20.0, toda: "Badiang TODA", ord: "MO No. 2022-18", landmarks: ["Badiang Barangay Center", "Agricultural belt"] },
  { id: "R11", origin: "Poblacion (Town Proper)", dest: "Barangay Mojon", km: 5.0, reg: 25.0, disc: 20.0, toda: "Mojon TODA", ord: "MO No. 2022-18", landmarks: ["Mojon Crossroad", "National Highway"] },
  { id: "R12", origin: "Poblacion (Town Proper)", dest: "Barangay Malandog", km: 5.5, reg: 25.0, disc: 20.0, toda: "Malandog Historical TODA", ord: "MO No. 2022-18", landmarks: ["Malandog Landing Landmark", "Historic Riverbed"] },
  { id: "R13", origin: "Poblacion (Town Proper)", dest: "Barangay Igbonglo", km: 7.5, reg: 30.0, disc: 24.0, toda: "So-ong Pantao Igbonglo Lugutan TODA", ord: "MO No. 2024-01", landmarks: ["Igbonglo Proper", "Lugutan Junction"] },
  { id: "R14", origin: "Poblacion (Town Proper)", dest: "Barangay Pantao", km: 8.2, reg: 30.0, disc: 24.0, toda: "So-ong Pantao Igbonglo Lugutan TODA", ord: "MO No. 2024-01", landmarks: ["Pantao Brgy Hall", "So-ong Cross"] },
  { id: "R15", origin: "Poblacion (Town Proper)", dest: "Barangay Lugutan", km: 7.0, reg: 30.0, disc: 24.0, toda: "So-ong Pantao Igbonglo Lugutan TODA", ord: "MO No. 2024-01", landmarks: ["Lugutan Primary School"] },
  { id: "R16", origin: "Poblacion (Town Proper)", dest: "Barangay Malaiba", km: 6.5, reg: 28.0, disc: 22.4, toda: "Malaiba TODA", ord: "MO No. 2022-18", landmarks: ["Malaiba Bridge"] },
  { id: "R17", origin: "Poblacion (Town Proper)", dest: "Barangay Supa", km: 7.8, reg: 30.0, disc: 24.0, toda: "Supa TODA", ord: "MO No. 2022-18", landmarks: ["Supa Barangay Plaza"] },
  { id: "R18", origin: "Poblacion (Town Proper)", dest: "Barangay Bariri", km: 5.2, reg: 25.0, disc: 20.0, toda: "Bariri TODA", ord: "MO No. 2022-18", landmarks: ["Bariri Crossing"] }
];

// App State
let passengerType = "regular";
let passengerCount = 1;
let isSpecialTrip = false;

// DOM Elements
const selectOrigin = document.getElementById("select-origin");
const selectDest = document.getElementById("select-destination");
const btnSwap = document.getElementById("btn-swap");
const chipBtns = document.querySelectorAll(".chip-btn");
const stepperMinus = document.getElementById("stepper-minus");
const stepperPlus = document.getElementById("stepper-plus");
const stepperNum = document.getElementById("stepper-num");
const checkSpecial = document.getElementById("check-special");

const fareAmountElem = document.getElementById("calculated-fare");
const fareDistanceElem = document.getElementById("calculated-distance");
const fareTodaElem = document.getElementById("calculated-toda");
const fareOrdinanceElem = document.getElementById("calculated-ordinance");
const fareNoteElem = document.getElementById("calculated-note");

const tabBtns = document.querySelectorAll(".tab-btn");
const tabPanels = document.querySelectorAll(".tab-panel");
const matrixList = document.getElementById("matrix-list");
const searchInput = document.getElementById("matrix-search");

// Modal Elements
const modal = document.getElementById("report-modal");
const openReportBtns = document.querySelectorAll("[data-open-report]");
const closeReportBtn = document.getElementById("close-report-btn");
const submitReportBtn = document.getElementById("submit-report-btn");

// Populate Dropdowns
function initDropdowns() {
  const origins = ["Poblacion (Town Proper)"];
  routesData.forEach(r => {
    if (!origins.includes(r.origin)) origins.push(r.origin);
  });
  
  selectOrigin.innerHTML = origins.map(o => `<option value="${o}">${o}</option>`).join("");
  selectDest.innerHTML = routesData.map(r => `<option value="${r.dest}">${r.dest} (${r.km} km)</option>`).join("");
  
  // Default to Funda-Dalipe
  selectDest.selectedIndex = 4;
}

// Find Route
function getSelectedRoute() {
  const dest = selectDest.value;
  return routesData.find(r => r.dest === dest) || routesData[0];
}

// Calculate Fare
function recalculateFare() {
  const route = getSelectedRoute();
  let baseRate = route.reg;
  let discountRate = 0.0;

  if (passengerType === "student" || passengerType === "senior" || passengerType === "pwd") {
    discountRate = 0.20;
  }

  const rateAfterDiscount = baseRate * (1 - discountRate);

  let totalFare = 0;
  if (isSpecialTrip) {
    // Pakyaw / Solo Charter: 3x regular standard base fare
    totalFare = baseRate * 3;
  } else {
    totalFare = rateAfterDiscount * passengerCount;
  }

  fareAmountElem.textContent = `₱${totalFare.toFixed(2)}`;
  fareDistanceElem.textContent = `${route.km} km`;
  fareTodaElem.textContent = route.toda;
  fareOrdinanceElem.textContent = route.ord;

  if (isSpecialTrip) {
    fareNoteElem.textContent = "Special Trip (Pakyaw): Exclusive solo ride / direct destination charter (standard 3-seat rate)";
  } else if (discountRate > 0) {
    fareNoteElem.textContent = `20% Statutory Discount applied for ${passengerType.toUpperCase()} (Base: ₱${baseRate.toFixed(2)} → ₱${rateAfterDiscount.toFixed(2)})`;
  } else {
    fareNoteElem.textContent = `Standard shared fare (₱${baseRate.toFixed(2)} × ${passengerCount} passenger${passengerCount > 1 ? "s" : ""})`;
  }
}

// Render Matrix
function renderMatrix(filterText = "") {
  const filtered = routesData.filter(r => {
    const q = filterText.toLowerCase();
    return r.dest.toLowerCase().includes(q) || 
           r.toda.toLowerCase().includes(q) || 
           r.landmarks.some(l => l.toLowerCase().includes(q));
  });

  if (filtered.length === 0) {
    matrixList.innerHTML = `<p style="padding: 20px; text-align: center; color: #64748b; font-size: 13px;">No routes found matching "${filterText}".</p>`;
    return;
  }

  matrixList.innerHTML = filtered.map(r => `
    <div class="route-card">
      <div class="route-card-header">
        <span class="route-card-dest">${r.dest}</span>
        <div class="route-card-fares">
          <span class="regular-price">₱${r.reg.toFixed(2)}</span>
          <span class="discounted-price">Disc: ₱${r.disc.toFixed(2)}</span>
        </div>
      </div>
      <div class="route-meta">
        <span>📍 ${r.km} km</span>
        <span>•</span>
        <span>${r.toda}</span>
        <span>•</span>
        <span style="color: #1e3523; font-weight: 600;">${r.ord}</span>
      </div>
      <div class="route-landmarks">
        <strong>Landmarks:</strong> ${r.landmarks.join(", ")}
      </div>
    </div>
  `).join("");
}

// Event Listeners
selectOrigin.addEventListener("change", recalculateFare);
selectDest.addEventListener("change", recalculateFare);

btnSwap.addEventListener("click", () => {
  const currentOrigin = selectOrigin.value;
  const currentDest = selectDest.value;
  // If destination exists in origin list, flip
  const canSwap = Array.from(selectOrigin.options).some(o => o.value === currentDest);
  if (canSwap) {
    selectOrigin.value = currentDest;
  }
  recalculateFare();
});

chipBtns.forEach(chip => {
  chip.addEventListener("click", () => {
    chipBtns.forEach(c => c.classList.remove("active"));
    chip.classList.add("active");
    passengerType = chip.dataset.type;
    recalculateFare();
  });
});

stepperMinus.addEventListener("click", () => {
  if (passengerCount > 1) {
    passengerCount--;
    stepperNum.textContent = passengerCount;
    recalculateFare();
  }
});

stepperPlus.addEventListener("click", () => {
  if (passengerCount < 5) {
    passengerCount++;
    stepperNum.textContent = passengerCount;
    recalculateFare();
  }
});

checkSpecial.addEventListener("change", () => {
  isSpecialTrip = checkSpecial.checked;
  recalculateFare();
});

// Tab Navigation
tabBtns.forEach(btn => {
  btn.addEventListener("click", () => {
    tabBtns.forEach(b => b.classList.remove("active"));
    tabPanels.forEach(p => p.classList.remove("active"));
    btn.classList.add("active");
    const target = document.getElementById(btn.dataset.tab);
    if (target) target.classList.add("active");
  });
});

// Search input
if (searchInput) {
  searchInput.addEventListener("input", (e) => {
    renderMatrix(e.target.value);
  });
}

// Modal handling
openReportBtns.forEach(btn => {
  btn.addEventListener("click", () => {
    modal.classList.add("open");
  });
});

closeReportBtn.addEventListener("click", () => {
  modal.classList.remove("open");
});

submitReportBtn.addEventListener("click", () => {
  const bodyNum = document.getElementById("report-body-num").value;
  if (!bodyNum) {
    alert("Please input the Tricycle Body Number.");
    return;
  }
  modal.classList.remove("open");
  alert(`Report for Tricycle Body #${bodyNum} recorded. Hotlines for San Jose Traffic Management Office (TMO: 0917-846-8632) and San Jose Police Station (0998-598-6288) are ready.`);
});

// Initialize on Load
initDropdowns();
recalculateFare();
renderMatrix();
