import 'package:flutter/material.dart';
import 'package:smart_box_client/screens/login_screen.dart';
import 'platform/platform_utils.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  //init deep link listener

  runApp(MyApp(initialRoute: '/'));
}


class MyApp extends StatefulWidget {
  const MyApp({super.key, required String initialRoute});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();

    if (isMobile) {
      debugPrint("Platform: Mobile");
    } else if (isDesktop) {
      debugPrint("Platform: Desktop");
    } else {
      debugPrint("Platform: Web");
    }
  }

  @override
  Widget build(BuildContext context) {
    return  MaterialApp(
      debugShowCheckedModeBanner: false,
      home: LoginScreen(),
    );
  }
}
