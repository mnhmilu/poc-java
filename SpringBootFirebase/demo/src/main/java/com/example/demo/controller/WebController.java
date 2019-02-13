package com.example.demo.controller;

import com.example.demo.entity.NotificationRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class WebController {

    private final String TOPIC = "Ezbitex Exchange";

    @Autowired
    com.example.demo.service.AndroidPushNotificationsService androidPushNotificationsService;



    @PostMapping("/postmsg")
    public ResponseEntity<String> postmsg(@RequestBody NotificationRequest notificationRequest) throws JSONException {


        try {
            String pushNotification = androidPushNotificationsService.sendPushNotification(notificationRequest.getDeviceToken(),notificationRequest.getMessage(),notificationRequest.getMessage2()
   ,notificationRequest.getTitle(),notificationRequest.getBody()            );

            return new ResponseEntity<>(pushNotification, HttpStatus.OK);
        } catch (IOException e) {
        }
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);


    }

    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Ezbitex");
        notification.put("body", "Welcome to Ezbitex Exchange!");

        JSONObject data = new JSONObject();
        data.put("Key-1", "JSA Data 1");
        data.put("Key-2", "JSA Data 2");

        body.put("notification", notification);
        body.put("data", data);

        /**
         * { "notification": { "title": "JSA Notification", "body": "Happy Message!" },
         * "data": { "Key-1": "JSA Data 1", "Key-2": "JSA Data 2" }, "to":
         * "/topics/JavaSampleApproach", "priority": "high" }
         */

        HttpEntity<String> request = new HttpEntity<>(body.toString());
        String deviceToken = "fuM5jajR5nA:APA91bF42zQEEOZIdwaD5cXAsHcTpb5ZDXA_8g-uo2MBISXmhJDtPvW8Wzrdy3R8PNU89JEOBZFZgf_WQZwr7JIG1gufqa2WX6IymJjSWeQv691dbQr-Tuaq1W9jQwRjiiHSmlsaPxCA";
        try {
            String pushNotification = androidPushNotificationsService.sendPushNotification(deviceToken, "Hello dude",
                    "welcome to Ezbitex exchange","title","body");

            return new ResponseEntity<>(pushNotification, HttpStatus.OK);
        } catch (IOException e) {
        }
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}