import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import '../models/inbox_response.dart';
import '../api/email_service.dart';

class InboxScreen extends StatelessWidget {
  final EmailService emailService;

  const InboxScreen({
    super.key,
    required this.emailService,
  });

 @override
Widget build(BuildContext context) {
  return Scaffold(
    appBar: AppBar(title: const Text('Inbox')),
    body: FutureBuilder<InboxResponse>(
      future: fetchInboxWithSession(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        }

        if (snapshot.hasError) {
          debugPrint('Error fetching emails: ${snapshot.error}');
          return Center(child: Text('Error: ${snapshot.error}'));
        }

        if (!snapshot.hasData || snapshot.data!.emails.isEmpty) {
          return const Center(child: Text('No emails found.'));
        }

        final inbox = snapshot.data!;
        final emails = inbox.emails;

        return ListView.builder(
          itemCount: emails.length,
          itemBuilder: (context, index) {
            final email = emails[index];
            return ListTile(
              title: Text(email.subject),
              subtitle: Text(email.from),
            );
          },
        );
      },
    ),
  );
}

// Wrap fetchEmails() call to include session cookie
Future<InboxResponse> fetchInboxWithSession() async {
  final prefs = await SharedPreferences.getInstance();
  final sessionCookie = prefs.getString('JSESSIONID');

  final response = await http.get(
    Uri.parse(
      'http://localhost:8080/email/inbox'),
      headers: {
        'Content-Type': 'application/json',
        'Cookie': 'JSESSIONID=$sessionCookie',
      }
    );


  if (response.statusCode == 200) {
      final Map<String, dynamic> jsonMap = jsonDecode(response.body);
    return InboxResponse.fromJson(jsonMap);
  } else if (response.statusCode == 401) {
    // Session expired
    await prefs.remove('JSESSIONID');
    throw Exception('Session expired, please log in again.');
  } else {
    throw Exception('Failed to load emails: ${response.statusCode}');
  }
}
}