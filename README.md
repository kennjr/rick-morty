# Rick & Morty

![Portfolio](https://img.shields.io/badge/Portfolio-%23000000.svg?style=for-the-badge&logo=firefox&logoColor=#FF7139) ![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white) ![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white) ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white) ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

---

Ever wanted to find some information about a character on the popular TV series Rick & Morty? It could be information about which seasons the character appeared in, whether the character is alive or dead, the character's origin or the character's species. Well look no further, this Rick & Morty application has all that you're looking for.

With the help of a [free API](https://rickandmortyapi.com/) this application gets you all the information related to any character that appeared in the show, and not just the popular characters but also those that appeared in even a single episode like **Agency Director**.

Thanks to [Axel Fuhrmann](https://afuh.dev/) from Berlin, I was able to build an application for all Rick & Morty fans. His free-to-use [api](https://rickandmortyapi.com/) powers this application by providing all the data served to our users.

---

  
### Look and feel

Here are some screenshots of the application

|                                       Characters List                                        |                                       Character Details                                       |  Character Episodes|
|:--------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------:|:---------------------:|
| ![First screenshot](https://github.com/kennjr/rick-morty/blob/dev/screenshot_1.jpg?raw=true) | ![Second screenshot](https://github.com/kennjr/rick-morty/blob/dev/screenshot_2.jpg?raw=true) | ![Third screenshot](https://github.com/kennjr/rick-morty/blob/dev/screenshot_3.jpg?raw=true)|
    
  
### Tech stack

Being an educational project I built it with the latest and greatest android technologies:

    - Jetpack Compose
    : for the project's UI
    - Ktor
    : for all network requests
    - Dagger hilt
    : for dependency injection
    - Coil
    : for image loading

  
### Features

What makes this app worthwhile, you might be asking. Here are some of the features our users enjoy:

    - All-encompassing list of characters and detailed information about each one.
      : This allows users to browse the characters and get all of the information about a desired(selected) character in the details page.
        While building this I got a chance to work with Jetpack Compose, Coil and Ktor. I solidified my learnings through this project and even worked on a custom pagination feature.
    - Character episodes
      : A user might want to know which episodes a character appears in and they'll find this information in the episodes page
        Since this is a separate page I used Compose navigation with hilt to navigate the user to this page from the details page
  
  
### Support

For any support report an issue on this repository, I'll work on it and update the codebase as soon as I can.

Better yet, you could fork the project and try fixing it, if it's something you think you can mend.  

  
### License
This software is publicly available under the [MIT](LICENSE) license
