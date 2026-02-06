import 'package:flutter/material.dart';

class OAuthWebView extends StatelessWidget {
  const OAuthWebView({super.key});

  @override
  Widget build(BuildContext context) {
    // Just a placeholder for desktop/web
    return Scaffold(
      body: Center(
        child: Text(
          "OAuth WebView not supported on this platform.\nOpening browser instead.",
          textAlign: TextAlign.center,
        ),
      ),
    );
  }
}
