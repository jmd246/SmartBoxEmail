import 'dart:io';

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:smart_box_client/api/email_service.dart';
import 'package:smart_box_client/platform/platform_utils.dart';
import 'package:smart_box_client/screens/inbox_screen.dart';
import 'package:url_launcher/url_launcher.dart';

// Conditional import
import '../o_auth_desktop_view.dart'
    if (dart.library.io) '../o_auth_mobile_web_view.dart';


class LoginScreen extends StatelessWidget {

  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context) {
    //check if logged in if not show login button
    return Scaffold(
      body: Center(
        child: ElevatedButton(
          onPressed: () => _handleLogin(context),
          child: const Text("Login with Google"),
        ),
      ),
    );
  }

Future<String?> listenForOAuthToken() async {
  final server = await HttpServer.bind(InternetAddress.loopbackIPv4, 9000);
  final request = await server.first;

  final sessionID = request.uri.queryParameters['sid'];
  //shared preferences save token
  SharedPreferences prefs = await SharedPreferences.getInstance();
  await prefs.setString('JSESSIONID', sessionID!);

  request.response
    ..statusCode = 200
    ..headers.set('Content-Type', 'text/html')
    ..write('<h1>Login Successful! You can close this window.</h1>')
    ..close();

  await server.close();
  return sessionID;
}


void _handleLogin(BuildContext context) async {
  final uri = Uri.parse('http://localhost:8080/oauth2/authorization/google');

  if (isMobile) {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (_) => const OAuthWebView()),
    );
  } else {
    // Desktop: launch system browser
    await launchUrl(
      uri,
      mode: LaunchMode.externalApplication,
    );

    // Start local server to capture redirect

    final sessionID = await listenForOAuthToken();

      // Create service instance
      final emailService = EmailService(baseUrl: 'http://localhost:8080');

      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (_) => InboxScreen(
            emailService: emailService,
          ),
        ),
      );


  } 
}



}
