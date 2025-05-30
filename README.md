# KVantage
### Minimal control center for Lenovo laptops on Linux, inspired by GTK and macOS aesthetics.

![KVantage logo](repo_images/main_logo.png)

**KVantage** is a sleek and minimal desktop app designed to provide users of Lenovo laptops running Linux with easy access to essential device settings. Inspired by the clean aesthetics of **GNOME (GTK)** and **macOS**, KVantage is built with **Kotlin + Compose Multiplatform for Desktop**, and aims to offer just the core features users care about. No bloat.

---

## 🚀 Current Status

- **Development Stage:** Early Development (UI prototypes completed, backend control system in progress). By now, I think I will rely on the wonderful project [batmanager](https://github.com/LevitatingBusinessMan/batmanager) written in Rust as the backend. If not, then I will probably rely on pure Kotlin (I'll figure that our later).
- **Functionality:** The GUI allows toggling performance profiles, battery thresholds, and rapid charge settings. System-level integration is being tested on Lenovo devices.
- **Usability:** Designed to be intuitive, light, and visually appealing, following minimal principles and Gruvbox theming.
- **Known Limitations:** System compatibility is limited to Lenovo laptops that expose firmware interfaces via `/sys`, `acpi`, or `ideapad-laptop` kernel module. Some features may require root permissions or external CLI tools.

---

## 🎯 Features (Planned & Implemented)

❌ **Toggle Performance Profiles** (e.g., Powersave, Performance, Intelligent Cooling)  
❌ **Battery Charge Threshold Management**  (AKA limit battery charge at 80% to improve battery lifespan)  
❌ **Rapid Charge Enable/Disable**  
❌ **Dark and Light Themes with Gruvbox Palette**  
❌ **System Detection for Compatibility**  
❌ **Native image packaging** (Planned)

---

## 🛠️ Technologies Used

- **Kotlin** – Primary language
- **Compose Multiplatform Desktop** – UI framework

---

## 🤝 Contributing
Contributions are welcome! Feel free to fork the repository and submit pull requests. If you have ideas, suggestions, or bug reports, open an issue on GitHub.

[//]: # (## 🎒 Resources)


## 📜 License
![GPLv3 License logo. Copyright © 2012 Christian Cadena](repo_images/license-logos-by-christian-candena-GNU_GPLv3_License.png)

[GPLv3 (GNU General Public License v3)](LICENSE.txt) – Free to use, modify, and distribute as long as this remains open source, and it is not use for profitable purposes.

    GPLv3 Logos:
    Copyright © 2012 Christian Cadena
    Available under the Creative Commons Attribution 3.0 Unported License.


---
> **Note:** KVantage is a personal learning project and is not affiliated with Lenovo or any other brand or product.
---
KVantage Copyright (c) 2025  Kosail