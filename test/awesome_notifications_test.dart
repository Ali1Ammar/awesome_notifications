import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
//import 'package:awesome_notifications/awesome_notifications.dart';

void main() {
  const MethodChannel channel = MethodChannel('awesome_notifications');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });
/*
  test('getPlatformVersion', () async {
    DateTime? referenceDate = DateUtils.parseStringToDate('2021-01-12 20:00:00');
    DateTime? expectedDate = DateUtils.parseStringToDate('2021-01-12 21:00:00');
    NotificationSchedule schedule = NotificationSchedule(initialDateTime: expectedDate);

    DateTime? result = await AwesomeNotifications().getNextDate(schedule, fixedDate: referenceDate);
    expect(result, expectedDate);

  });
  */
}
