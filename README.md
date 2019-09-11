# Data Feeder Demo App

# General:
   - Version "1.0"
 
# Features:
  - Fetch posts from remote server using Retrofit
  - Present said posts in a RecyclerView
  - Filtering posts
  - Allow interaction with the post in the form of opening a WebView or a video player.

# Installation:
   - Clone the repository.
   - Open the project in Android Studio.
   - Make yourself a cup of coffee because Gradle builds take forever....
   - Run the project either on a virtual device or on a real device connected via usb or WiFi
   - If you have an APK of this app simply connect a device and run ```adb install filename.apk``` in shell

# Test Scenarios:
- Opening the app with no Internet connection should prompt a message that something went wrong.
- Filtering for non existing values should give an empty list of posts.
- Clicking on an post fron the list and receiving the correct response (either a Webview or a VideoView will open)
- Intercation with filtered data should still give the correct output.

# Known Issues:
- The app doesn't support Horizontal Layout
- The app doesn't support LTR

