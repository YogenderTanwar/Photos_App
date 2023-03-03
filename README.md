# Photos_App

Photo Application facilitates to display of random dog images directly fetched from an API.Application is based on MVVM architecture 

ImageViewModel file contains the business logic for the helper method mentioned below.

getImage() - Gets one image of a dog from the library.

getImages(int number) - Gets the number of dog images mentioned in the method

getNextImage() - Gets the next image of a dog

getPreviousImage() - Gets the previous image of a dog

API Information:
API endpoint - https://dog.ceo/api/breeds/image/random
Sample JSON response -

{
    "message": "https://images.dog.ceo/breeds/leonberg/n02111129_2785.jpg",
    "status": "success"
}

Please refer to this Dog.ceo link for API documentation : https://dog.ceo/dog-api/documentation/random

Here is the Debug Version of API link - https://drive.google.com/file/d/11b0Cv9bzDItsMgBTkklFsze5qRQJh9gH/view?usp=sharing . Feel free to download and use it. 


# Steps for Integrating the Library in your project 
1. Add  maven { url 'https://jitpack.io' } it in your root setting.gradle at the end of repositories:  [Reference](https://github.com/YogenderTanwar/Photos_App/blob/master/settings.gradle#L13)
2. Add the dependency 'com.github.YogenderTanwar:Photos_App:1.0.1' in your bulild.gradle file . [Reference](https://github.com/YogenderTanwar/Photos_App/blob/master/app/build.gradle#L59)
3. Step the ImageViewModel class object using ViewModelProvider and access the above mentioned method according to your requirements. [Ref](https://github.com/YogenderTanwar/Photos_App/blob/master/app/src/main/java/com/example/photos/MainActivity.java#L52) 

[Reference of calling getImages methods](https://github.com/YogenderTanwar/Photos_App/blob/master/app/src/main/java/com/example/photos/MainActivity.java#L87)
