import 'package:http/http.dart' as http;

class EmailService {
  final String baseUrl;

  EmailService({
    required this.baseUrl,
  });

 



Future<http.Response> fetchEmails() async {

  final response = await http.get(
    Uri.parse('$baseUrl/email/inbox'),headers: {'content-type': 'application/json'}
  );
  if(response.statusCode != 200) {
    throw Exception('Failed to load emails: ${response.statusCode}');
  }

  return response;
}
}
