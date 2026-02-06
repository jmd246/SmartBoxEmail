import 'package:flutter/material.dart';
import 'package:flutter_inappwebview/flutter_inappwebview.dart';

class OAuthWebView extends StatelessWidget {
  const OAuthWebView({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Sign in with Google")),
      body: InAppWebView(
        initialUrlRequest: URLRequest(
          url: WebUri('http://localhost:8080/oauth2/authorization/google'),
        ),
        onLoadStart: (controller, url) {
          if (url == null) return;

          if (url.scheme == 'emailbox' && url.host == 'login-success') {
            Navigator.pop(context); // close WebView on success
          }
        },
      ),
    );
  }
}
