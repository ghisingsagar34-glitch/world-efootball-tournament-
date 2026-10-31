WORLD EFOOTBALL TOURNAMENT - Android Studio project

यो Android Studio मा खोल्न मिल्ने APK project हो। यो ZIP आफैं APK होइन।

APK बनाउन:
1. Android Studio स्थापना गर्नुहोस्।
2. Android Studio > Open बाट यो ZIP extract गरेको WORLD_EFOOTBALL_ANDROID_PROJECT folder खोल्नुहोस्।
3. Gradle Sync पूरा हुन दिनुहोस् (इन्टरनेट चाहिन्छ)।
4. Build > Build Bundle(s) / APK(s) > Build APK(s) छान्नुहोस्।
5. APK app/build/outputs/apk/debug/app-debug.apk मा बन्नेछ।

हालको अवस्था:
- यो Android WebView shell हो, जसमा पहिल्यै बनाइएको HTML demo समावेश छ।
- Demo data सोही मोबाइल/ब्राउजरको local storage मा मात्र रहन्छ।
- धेरै खेलाडीले एउटै डाटा हेर्न real backend/database, secure admin login,
  online sync, chat moderation र hosting थप्नुपर्छ।
- Demo coins निःशुल्क र virtual मात्र हुन्; real money, deposit,
  cash prize वा withdrawal सुविधा छैन।
- सार्वजनिक रूपमा चलाउनुअघि security review र testing आवश्यक छ।

App ID: com.worldefootball.tournament
Version: 1.0


Admin demo login:
- Admin tab खोल्दा PIN मागिन्छ।
- Demo PIN: 2468
- Logout गरेपछि फेरि PIN चाहिन्छ।
- यो PIN HTML app भित्रै भएकाले सजिलै पत्ता लगाउन/बाइपास गर्न सकिन्छ।
  सार्वजनिक एपमा प्रयोग नगर्नुहोस्। वास्तविक admin login server-side
  authentication, password hashing, HTTPS र role-based permissions बाट बनाउनुपर्छ.
