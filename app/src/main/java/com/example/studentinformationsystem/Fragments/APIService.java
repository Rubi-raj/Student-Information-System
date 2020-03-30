package com.example.studentinformationsystem.Fragments;

import com.example.studentinformationsystem.Notifications.MyResponse;
import com.example.studentinformationsystem.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAzO1Y3BE:APA91bE3E-Iu75p3XnA0JW9zAsWLgj83QqA62XVekcMUT0s8gBKFDEJbqdLFP8OfkJcUtZa_envqjrxCsugmezoUFpNJF49kNfzdqs-DN1zabwpbpGHrWgHeww73Z1J-x2reFIK7Phb6"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
