import 'package:flutter/material.dart';
import 'package:flutter_inappwebview/flutter_inappwebview.dart';

class OAuthWebView extends StatelessWidget {
  final Uri oauthUri;
  const OAuthWebView({super.key, required this.oauthUri});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Sign in with Google")),
      body: InAppWebView(
        initialUrlRequest: URLRequest(
          url: WebUri(oauthUri.toString()),
        ),
        onLoadStart: (controller, url) {
          if (url == null) return;
          if (url.scheme == 'smartbox' && url.host == 'login-success') {
            Navigator.pop(context); // close WebView on success
          }
        },
      ),
    );
  }
}
