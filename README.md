# Rival Android Coding Challenge

In this exercise you will be extending an existing Android project by:

  1. Call our login API endpoint to verify credentials in the Login activity.
  2. Call our recipe API endpoint to populate the recipe list and detail
   activities.

Extra credit if time allows and you feel you have a solid implementation of the
two requirements above:

  3. Add a text input to the recipe list activity to filter the
   list of recipes.

## Instructions

  * Download the project from https://s3-us-west-2.amazonaws.com/rival-recipes/android.zip
  * Using the API details below, add code to authenticate and fetch the required data
  * Merge the recipe data with the image data based on recipe_id
  * When you are done, zip up your completed project and email it back to us.

We typically expect this to take around **2 hours** to complete. We don't want
to take up too much of your time, so please don't spend more than 3 hours on it.
If you do hit the 3 hour mark, just send what you have. We are more interested
in the quality of your code and the approach you have taken than in
completeness.

## Android Studio Project

Do not change the minimum SDK version (14). You can start from scratch if you
wish -- the project files you see here were generated in a few minutes using
Android Studio 3.0.1 and the templates Login Activity, and Master/Detail Flow.

## API Details

The **base URL for all endpoints** is [https://mobile.rival.run](https://mobile.rival.run/)

**/login (POST)**

* Takes a body of type **application/json** with two entries “**email**” and “**password**”:

       {
           "email":"user@email.com",
           "password":"verysecure"
       }

* Valid credentials are **email: **user@email.com **password: **verysecure
* Will return a **user_token **in the response if the login is good:

      {
        "user_token": <some_token>
      }

**/recipes (GET)**

* Requires an **Authorization** header field with a value that is the **user_token **returned during login:

      {
          "Authorization": <some_token>
      }

* Will return a list of recipe results if the auth is OK, otherwise a 401
* The list will look like:

      {
          "results":[
              <item>,
              <item>,
              ...
          ]
      }

* Each item in the results list will look like:

      {
        "recipe_id":1,
        "recipe_name":"Mary’s Victoria sandwich"
      }

**/recipe-images (GET)**

* Requires an **Authorization** header field with a value that is the **user_token **returned during login
* Will return a list of recipe images results if the auth is OK, otherwise a 401
* The list will look like:

      {
          "results":[
              <item>,
              <item>,
              ...
          ]
      }

* Each item in the response will look like:

      {
        "recipe_id": "1",
        "img_urls": {
              "sm_url": "https://s3-us-west-2.amazonaws.com/recipe-imgs/thumb/q.jpg",
              "lg_url": "https://s3-us-west-2.amazonaws.com/recipe-imgs/large/x.jpg"
        }
      }

**/recipes/:id (GET)**

* Requires an **Authorization** header field with a value that is the **user_token **returned during login
* Will return recipe details for the recipe with the given id if the auth is OK, otherwise a 401

## Results

The resulting screen(s) should look something like:

TBD

## Notes

* Please try and use native frameworks rather than relying on 3rd party libraries.
* Consider the efficiency/complexity of your algorithm when matching recipes to their images.
* Your submission will be mainly judged on code organization and cleanliness, and the performance and responsiveness of the UI.
* The endpoints for this exercise are hosted on Amazon API Gateway. Incorrectly formed requests (typo in URL, wrong HTTP method etc.) can result in a response that implies authorization issues, when in fact it is just a problem with the request unrelated to auth. An example of this kind of response is as follows:

      Authorization header requires 'Credential' parameter.
      Authorization header requires 'Signature' parameter.
      Authorization header requires 'SignedHeaders' parameter.
      Authorization header requires existence of either a 'X-Amz-Date' or a 'Date' header
