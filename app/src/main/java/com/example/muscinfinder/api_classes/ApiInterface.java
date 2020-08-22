package com.example.muscinfinder.api_classes;

import com.example.muscinfinder.modal.BookModal;
import com.example.muscinfinder.modal.FavouriteModal;
import com.example.muscinfinder.modal.CollectionModal;
import com.example.muscinfinder.modal.GetFavouriteModal;
import com.example.muscinfinder.modal.InstituteModal;
import com.example.muscinfinder.modal.RecommendModal;
import com.example.muscinfinder.modal.UpdateModal;
import com.example.muscinfinder.modal.UserModal;
import com.example.muscinfinder.required_classes.SignUpResponse;
import com.example.muscinfinder.required_classes.UserModalAno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    // Node API for products
    @GET("product/getproduct")
    Call<List<RecommendModal>> getProduct();

    @GET("institute/getinstitute")
    Call<List<InstituteModal>> getInstitute();

    // Node API for collections
    @GET("collections")
    Call<List<CollectionModal>> getCollection();

    //register user
    @POST("register/register_user")
    Call<Void> registerUser(@Body UserModal usersCUD);

    //for logging into the system
    @FormUrlEncoded
    @POST("register/login_user")
    Call<SignUpResponse> checkUser(@Field("email") String email, @Field("password") String password);

    @GET("register/me")
    Call<UserModalAno> getUserDetails(@Header("Authorization") String token);

    @POST("cart/addcart")
    Call<Void> addCart(@Body FavouriteModal postReviewModal);

    @FormUrlEncoded
    @POST("products/getProductUnlimit")
    Call<List<RecommendModal>> loadProduct(@Field("product") String product);

    // view cart by userid
    @FormUrlEncoded
    @POST("cart/getCartJoin")
    Call<List<GetFavouriteModal>> loadCartSession(@Field("userid") String userid);

    // add book restaurant
    @POST("book/res")
    Call<Void> addBook(@Body BookModal bookModal);

    // update user information
    @PUT("register/me")
    Call<UpdateModal> edituser(@Header("Authorization")String token, @Body UpdateModal userUpdateModel);

}