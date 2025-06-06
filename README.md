# KVantage
### Minimal control center for Lenovo laptops on Linux, inspired by GTK and macOS aesthetics.

![KVantage logo](repo_images/logo_light.png)

**KVantage** is a sleek and minimal desktop app designed to provide users of Lenovo laptops running Linux with easy access to essential device settings. Inspired by the clean aesthetics of **GNOME (GTK)** and **macOS**, KVantage is built with **Kotlin + Compose Multiplatform for Desktop**, and aims to offer just the core features users care about. No bloat.

---

## 🚀 Current Status
#### Development Stage:
The graphical interface is already completed at 100%, and the backend (which is the one that actually makes the changes) is completed at 100% too. Right now, I'm working on linking the GUI to the daemon to make them work together.
<br><br>
At first I thought of using the wonderful project [batmanager, by LevitatingBusinessMan](https://github.com/LevitatingBusinessMan/batmanager) which is exactly what this program aims for, but in CLI format. However, I know no Rust and I couldn't make it work on NixOS (dynamic linking executable issues), and it is my main Linux distro.<br><br>
At the end, due some limitations of the JVM and Kotlin Native, I decided to reimplement batmanager in Golang. More about this in the [backend section below](#backend).
<br><br>
But going back to the main topic, the current status:
- **Functionality:** The GUI allows toggling performance profiles, battery thresholds, and rapid charge settings. System-level integration is being tested on Lenovo devices.
- **Usability:** Designed to be intuitive, light, and visually appealing, following minimal principles and Gruvbox theming. More themes may be implemented in the future.
- **Known Limitations:** System compatibility is limited to Lenovo laptops that expose firmware interfaces via `/sys`, `acpi`, or `ideapad-laptop` kernel module. Some features may require root permissions or external CLI tools.

---

## 🎯 Features (Planned & Implemented)
> **Note:** ⚠️  Means partially implemented.

⚠️ **Toggle Performance Profiles** (e.g., Powersave, Performance, Intelligent Cooling)  
⚠️ **Battery Charge Threshold Management**  (AKA limit battery charge at 80% to improve battery lifespan)  
⚠️ **Rapid Charge Enable/Disable**  
✅ **Dark and Light Themes with Gruvbox Palette**  
❌ **System Detection for Compatibility**  
❌ **Native image packaging** (Planned)

![Animated Background ON with Gruvbox theme](repo_images/gruvbox_theme_1.png)
<details>
  <summary>More images over here:</summary>

![Animated Background OFF with Gruvbox theme](repo_images/gruvbox_theme_2.png)
![Settings](repo_images/gruvbox_theme_3.png)

</details>

---
##  Backend
To keep things clean, I will maintain only the GUI app here, and kvand (the backend daemon) in another repository. In this repo you'll only find the native kbatd executable. To see kbatd source code, comments and notes about it, please check the [daemon's repository here at my GitHub profile](https://github.com/kosail/kbatd).


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
KVantage Copyright © 2025, kosail 
<br>
With love, from Honduras.