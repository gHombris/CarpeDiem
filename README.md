# Carpe Diem

Carpe Diem is an Android application designed to help users manage their digital wellbeing by actively blocking short-form video content on platforms such as TikTok, YouTube (Shorts), and Instagram (Reels). 

The application utilizes Android's Accessibility Services to detect specific UI nodes and view states in real-time, preventing the user from engaging with addictive short video loops without disabling the core functionality of the affected applications.

## Features

- **Granular Blocking Controls**: Users can toggle blocking independently for TikTok, YouTube Shorts, and Instagram Reels.
- **Context-Aware Detection**: 
  - For TikTok, the application blocks access immediately upon launch.
  - For YouTube and Instagram, the block is triggered conditionally. It reads the accessibility node tree to identify specific interactions (e.g., selecting the "Shorts" or "Reels" tabs or entering the short-video player), allowing users to browse standard videos and feeds normally.
- **System Overlay**: Employs a full-screen overlay to interrupt the video consumption flow and gracefully redirect the user back to their home screen.
- **Minimalist Interface**: Clean, modern settings interface built with standard Android XML layouts and custom vector drawables.

## Technical Architecture

- **AccessibilityService**: The core monitoring engine (`ShortsBlockerService.java`). It intercepts `AccessibilityEvent` dispatches and analyzes the `AccessibilityNodeInfo` tree to find specific text patterns, resource IDs, and `isSelected()` states.
- **SharedPreferences**: Handles state management for user preferences and block toggles.
- **Custom XML Layouts**: The UI utilizes `SwitchCompat` and `AppCompatButton` components customized via XML shapes and drawables to achieve a clean aesthetic.

## Requirements

- Minimum SDK: Android 6.0 (API Level 23)
- Java 8+

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/gHombris/CarpeDiem.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Build and run the application on an Android device or emulator.

## Configuration & Usage

Upon first launch, the application will prompt the user for two essential system permissions:
1. **Draw Over Other Apps**: Required to display the blocking overlay over other applications.
2. **Accessibility Service**: Required to monitor active window packages and UI node states.

Once permissions are granted, use the main interface to enable the global blocker and select the specific platforms you wish to monitor.

## License

This project is licensed under the MIT License.
