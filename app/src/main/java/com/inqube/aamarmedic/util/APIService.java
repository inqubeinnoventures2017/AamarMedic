package com.inqube.aamarmedic.util;

//import com.inqube.aamarmedic.model.getCategoryList.MSG;
//import com.inqube.aamarmedic.model.login.LoginJSONParameter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIService {
    @POST("user/login")
    Call<com.inqube.aamarmedic.model.login.MSG> loginData(@Body LoginJSONParameter Param);

    @GET("user/list/specialization")
    Call<com.inqube.aamarmedic.model.specializationlist.MSG> getSpecializationList(@Query("token") String token, @Query("language_id") String language_id);

    @GET("user/dependent/cliniclist/{spec_id}")
    Call<com.inqube.aamarmedic.model.clinic.MSG> getClinicList(@Path("spec_id") String spec_id,@Query("token") String token);

    @GET("user/dependent/daylist/{spec_id}/{clinic_id}")
    Call<com.inqube.aamarmedic.model.daylist.MSG> getDayList(@Path("spec_id") String spec_id,@Path("clinic_id") String clinic_id,@Query("token") String token);

    @GET("user/list/category/doctor?")
    Call<com.inqube.aamarmedic.model.doctorlist.MSG>getDoctorlist(@Query("spec_id") String spec_id,
                                                                  @Query("clinic_id") String clinic_id,
                                                                  @Query("day") String day_id,
                                                                  @Query("token") String token,
                                                                  @Query("lang_id") String lang_id);

    @GET("user/list/doctor?")
    Call<com.inqube.aamarmedic.model.doctorlistbyname.MSG>getDoctorlistbyname(@Query("doc") String doc,
                                                                  @Query("token") String token, @Query("lang_id") String lang_id);

    @GET("user/district_list")
    Call<com.inqube.aamarmedic.model.districtlist.MSG> getDistrictList(@Query("token") String token);

    @GET("user/city_list/{dist_id}")
    Call<com.inqube.aamarmedic.model.citylist.MSG> getCityList(@Path("dist_id") String dist_id, @Query("token") String token);

    @FormUrlEncoded
    @POST("user/appointment")
    Call<com.inqube.aamarmedic.model.appointment.MSG>getDoctorAppointment(@Field("doctor_schedule") String doctor_schedule,
                                                                 @Field("patient_name") String patient_name,
                                                                 @Field("mobile") String mobile,
                                                                 @Field("address") String address,
                                                                 @Field("pin_code") String pin_code,
                                                                 @Field("district") String district,
                                                                 @Field("city") String city,
                                                                 @Field("remark") String remark,
                                                                 @Query("token") String token);

    @FormUrlEncoded
    @POST("user/patient/register")
    Call<com.inqube.aamarmedic.model.telehealthpersonal.MSG>submitTeleHealthPersonal(@Field("patient_name") String patient_name,
                                                                                     @Field("mobile_no") String mobile_no,
                                                                                     @Field("aadhar_no") String aadhar_no,
                                                                                     @Field("email_id") String email_id,
                                                                                     @Field("address") String address,
                                                                                     @Field("city") String city,
                                                                                     @Field("area") String area,
                                                                                     @Field("pin_code") String pin_code,
                                                                                     @Field("gender") String gender,
                                                                                     @Field("date_of_birth") String date_of_birth,
                                                                                     @Query("token") String token);

    @GET("user/patient/personal_details?")
    Call<com.inqube.aamarmedic.model.patientpersonaldetails.MSG> getPatientPersonalDetails(@Query("mobile") String mobile,
                                                                                           @Query("token") String token);

    @FormUrlEncoded
    @POST("user/patient/medical1")
    Call<com.inqube.aamarmedic.model.telehealthmedical.MSG>submitTeleHealthMedical(@Field("patient_id") String patient_id,
                                                                                     @Field("body_temperature") String body_temperature,
                                                                                     @Field("is_cough") boolean is_cough,
                                                                                     @Field("cough_pattern") String cough_pattern,
                                                                                     @Field("cough_no_of_days") boolean cough_no_of_days,
                                                                                     @Field("is_shortness_of_breath") boolean is_shortness_of_breath,
                                                                                     @Field("shortness_of_breath_type") String shortness_of_breath_type,
                                                                                     @Field("is_blood_pressure") boolean is_blood_pressure,
                                                                                     @Field("last_blood_pressure") String last_blood_pressure,
                                                                                     @Field("is_blood_sugar") boolean is_blood_sugar,
                                                                                     @Field("blood_sugar_fasting") String blood_sugar_fasting,
                                                                                     @Field("blood_sugar_pp") String blood_sugar_pp,
                                                                                     @Field("is_allergic") boolean is_allergic,
                                                                                     @Query("token") String token);

    @GET("user/patient/check_duplicate_aadhar?")
    Call<com.inqube.aamarmedic.model.checkduplicateaadhar.MSG> checkDuplicateAadharNo(@Query("aadhar_no") String aadhar_no,
                                                                                           @Query("token") String token);

    @GET("user/list/booking?")
    Call<com.inqube.aamarmedic.model.agentbookinglist.MSG>getAgentBookingList(@Query("agent_id") String agent_id,
                                                                              @Query("token") String token);

    @GET("user/list/language?")
    Call<com.inqube.aamarmedic.model.languagelist.MSG>getLanguageList(@Query("token") String token);

    @GET("user/details/agent?")
    Call<com.inqube.aamarmedic.model.agentprofiledetails.MSG>getAgentProfileDetails(@Query("id") String id,
                                                                                    @Query("token") String token);

    @FormUrlEncoded
    @PUT("user/update/agent")
    Call<com.inqube.aamarmedic.model.agentprofileupdate.MSG>updateAgentProfile(@Field("id") String id,
                                                                               @Field("name") String name,
                                                                               @Field("email") String email,
                                                                               @Field("mobile") String mobile,
                                                                               @Field("language_id") String language_id,
                                                                               @Query("token") String token);

   /* @FormUrlEncoded
    @PUT("user/update/agent/image")
    Call<com.inqube.aamarmedic.model.agentprofileimageupdate.MSG>updateAgentProfileImage(@Field("id") String id,
                                                                               @Field("old_image_name") String old_image_name,
                                                                               @Field("image_url") String image_url,
                                                                               @Query("token") String token);*/

    @GET("user/logout")
    Call<com.inqube.aamarmedic.model.logout.MSG> logoutData(@Query("token") String token);

}
