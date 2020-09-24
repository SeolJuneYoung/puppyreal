package org.techtown.puppydiary.network;

import org.techtown.puppydiary.Login;
import org.techtown.puppydiary.network.Data.EmailData;
import org.techtown.puppydiary.network.Data.account.AccountUpdateData;
import org.techtown.puppydiary.network.Data.calendar.CalendarPhotoData;
import org.techtown.puppydiary.network.Data.calendar.CalendarUpdateData;
import org.techtown.puppydiary.network.Data.CheckemailData;
import org.techtown.puppydiary.network.Data.FindpwData;
import org.techtown.puppydiary.network.Data.account.InsertAccountData;
import org.techtown.puppydiary.network.Data.KgupdateData;
import org.techtown.puppydiary.network.Data.ProfileData;
import org.techtown.puppydiary.network.Data.RegisterData;
import org.techtown.puppydiary.network.Data.SigninData;
import org.techtown.puppydiary.network.Data.SignupData;
import org.techtown.puppydiary.network.Data.UpdatepwData;
import org.techtown.puppydiary.network.Response.EmailResponse;
import org.techtown.puppydiary.network.Response.account.AccountUpdateResponse;
import org.techtown.puppydiary.network.Response.calendar.CalendarPhotoResponse;
import org.techtown.puppydiary.network.Response.calendar.CalendarUpdateResponse;
import org.techtown.puppydiary.network.Response.account.CheckAccountResponse;
import org.techtown.puppydiary.network.Response.CheckemailResponse;
import org.techtown.puppydiary.network.Response.account.DeleteAccountResponse;
import org.techtown.puppydiary.network.Response.FindpwResponse;
import org.techtown.puppydiary.network.Response.account.InsertAccountResponse;
import org.techtown.puppydiary.network.Response.KgupdateResponse;
import org.techtown.puppydiary.network.Response.MyinfoResponse;
import org.techtown.puppydiary.network.Response.ProfileResponse;
import org.techtown.puppydiary.network.Response.RegisterResponse;
import org.techtown.puppydiary.network.Response.account.ShowAccountResponse;
import org.techtown.puppydiary.network.Response.calendar.ShowDayResponse;
import org.techtown.puppydiary.network.Response.ShowKgResponse;
import org.techtown.puppydiary.network.Response.calendar.ShowMonthResponse;
import org.techtown.puppydiary.network.Response.SigninResponse;
import org.techtown.puppydiary.network.Response.SignupResponse;
import org.techtown.puppydiary.network.Response.UpdatepwResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ServiceApi {

    String jwtToken = Login.jwtToken;


    @POST("/user/signup") //회원가입
    Call<SignupResponse> usersignup(@Body SignupData data);
    /*
    {
        "email" : "jooe0824@naver.com",
        "password" : "123123",
        "passwordConfirm" : "123123"
    }
    */

    @POST("/user/signin") //로그인
    Call<SigninResponse> usersignin(@Body SigninData data);
    /*
    {
        "email" : "jooe0824@naver.com",
        "password" : "123123"
    }
    */

    @POST("/user/checkemail") //이메일 중복확인
    Call<CheckemailResponse> checkemail(@Body CheckemailData data);
    /*
    {
        "email" : "hyohyo@naver.com"
    }
     */

    @POST("/user/findpw") //비밀번호 찾기
    Call<FindpwResponse> findpw (@Body FindpwData data);
    /*
    {
        "email" : "hyohyo@naver.com"
    }
    */


    @Headers("token:{jwtToken}")
    @POST("/user/updatepw") //비밀번호 업데이트
    Call<UpdatepwResponse> updatepw (@Body UpdatepwData data);
    /*
    {
    "email" : "ga0@naver.com",
    "password" : "123123",
    "newpassword" : "1231234",
    "passwordConfirm" : "1231234"
    }
     */

    @GET("/user/getemail") //비밀번호 업데이트
    Call<EmailResponse> getEmail ();

    @Headers("token:{jwtToken}")
    @POST("/mypage/registermyinfo") //강아지 정보 등록/업데이트
    Call<RegisterResponse> registerinfo (@Body RegisterData data);
    /*
    {
        "puppyname" : "maru",
        "age" : "4",
        "birth" : "2017.12.19",
        "gender" : "1"
    }
     */

    @Headers("token:{jwtToken}")
    @POST("/user/profile") //프로필 사진 업데이트
    Call<ProfileResponse> profile (@Body ProfileData data);
    /*
    form-date
    {
        "key" : "file type"
        "values" : "file location"
    }
     */

    @GET("/mypage/myinfo") //강아지 정보 조회
    Call<MyinfoResponse> Getmyinfo ();

    @GET("/calendar/show/{year}/{month}") //달력 월별 조회
    Call<ShowMonthResponse> showmonth (@Path("year") int year, @Path("month") int month);

    @GET("/calendar/show/{year}/{month}/{date}") //달력 일일 조회
    Call<ShowDayResponse> showday (@Path("year") int year, @Path("month") int month, @Path("date") int date);

    @Multipart
    @POST("/calendar/{year}/{month}/{date}/photo") //달력 사진 업로드
    Call<CalendarPhotoResponse> calendarphoto (@Path("year") int year, @Path("month") int month, @Path("date") int date,  @Part MultipartBody photo); //@Body CalendarPhotoData data,

    @POST("/calendar/update") //달력 update
    Call<CalendarUpdateResponse> calendarupdate (@Body CalendarUpdateData data);

    @GET("/kg/show/{year}") //kg 조회
    Call<ShowKgResponse> showkg (@Path("year") int year);

    @POST("/kg/update") //kg update
    Call<KgupdateResponse> kgupdate (@Body KgupdateData data);

    @GET("/account/show/{year}/{month}/{date}") //가계부 조회
    Call<ShowAccountResponse> showaccount (@Path("year") int year, @Path("month") int month, @Path("date") int date);

    @POST("/account/insert") //가계부 아이템 추가
    Call<InsertAccountResponse> insertaccount (@Body InsertAccountData data);

    @GET("/account/check/{year}/{month}/{date}/{item}/{price}") //가계부 아이템 조회 확인(idx)
    Call<CheckAccountResponse> checkaccount (@Path("year") int year, @Path("month") int month, @Path("date") int date, @Path("item") String item, @Path("price") int price);

    @POST("/account/update/{idaccount}") //가계부 아이템 수정
    Call<AccountUpdateResponse> accountupdate (@Path("idaccount") int idaccount, @Body AccountUpdateData data);

    @DELETE("/account/delete/{idaccount}") //가계부 아이템 삭제
    Call<DeleteAccountResponse> deleteaccount (@Path("idaccount") int idaccount);

}



//public interface MemberFactoryIm {
//    //통신 담당 메소드 구현.
//    @GET("login")
//    Call<List<User>> login(@Query("userEmail") String email, @Query("userPwd") String password);
//
//    @POST("join")
//    Call<Res_join> join(@Body Req_join user); //보낼때는 Req로 보내고 받을 때는 Res로 받음.
//
//    @GET("dupl")
//    Call<String> dupl(@Query("userEmail") String email);
//}