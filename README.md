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
