# Costify

Costify is a Java-based console application designed to help users estimate and manage project costs. By offering features such as user management, project tracking, and cost analysis tools, Costify aims to empower individuals and businesses to better understand and control their project expenses. The application provides users with insights into their project costs and helps them manage materials and labor effectively.

## 🚀 About Me
Coding enthusiast and full stack developer who loves solving problems and bringing ideas to reality using web development tools.

I'm a developer who loves and uses Laravel to create web applications. My motto in web development is to create something that I want to use as a client because I think that if we understand our customers' needs, we will satisfy them. Web development is not just about a website but instead a brand and an identity that represents the developer and the client. I will do the best I can to develop the best product.

## Features

- **User Registration and Login**: Users can create an account and log in to manage their projects.
- **Manage Projects**: Users can add projects with details such as materials and labor costs.
- **Generate Estimates**: Users can generate estimates for their projects showing material and labor costs.
- **View and Edit Personal Information**: Users can view and update their personal information.
- **View Projects and Estimates**: Users can view a list of their projects and estimates.
  
## Client Features

- **User Registration and Login**: Clients can create an account and log in to access their dashboard.
- **Manage Projects**: Clients can  view their own projects.
- **Add and Manage Labor**: Clients can add labor costs associated with their projects and manage labor details.
- **Generate Estimates**: Clients can manage their estimates of their projects.
- **View and Edit Personal Information**: Clients can view and update their personal information.

## Admin Features

- **User Management**: Admins can add , view, and delete user accounts.
- **Project Management**: Admins can view, update, and delete any project in the system.
- **Material and Labor Management**: Admins can add  material and labor entries for the project.
- **Generate System-wide Reports**: Admins can generate reports that provide insights into overall system usage, project costs, and user activity.

## Code Overview

The project contains the following key components:

- **Auth/**
  - **Login**: Handles user login functionality.
  - **Register**: Manages user registration processes.

- **config/**
  - **Database**: Contains configuration settings for database connections.

- **Domain/**
  - **Enum/**
    - **Staus**: Enum class defining different status of projects.
    - **Role**: Difining different roles of the user
  - **Material**: Represents material-related cost data.
  - **Labor**: Represents labor-related cost data.
  - **Estimate**: Generates estimates based on project data.
  - **Project**: Represents a project in the system, including materials and labor costs.
  - **User**: Represents a user in the system, including personal information and a list of projects and estimates.

- **Repository/**
  - **MaterialRepository**: Data access layer for handling material data.
  - **LaborRepository**: Data access layer for handling labor data.
  - **EstimateRepository**: Data access layer for handling estimate data.
  - **ProjectRepository**: Data access layer for handling project data.
  - **UserRepository**: Data access layer for handling user data.

- **Services/**
  - **Implementations/**
    - **MaterialService**: Business logic related to material data.
    - **LaborService**: Business logic related to labor data.
    - **EstimateService**: Business logic related to estimate generation.
    - **ProjectService**: Business logic related to project management.
    - **UserService**: Business logic related to user management.

- **utils/**
  - **DateUtil**: Utility functions for handling date operations.

- **Main**: Entry point of the application, handles user menus and interactions.

- **Steps To Create the Jar file/**
    - **Compile the main file**: we need to compile the enter point of our code main.java using the command ```javac main.java``` , we will take class file and we will put it into a new folder at the root called ```srcr```
    - **Copying all the other classes**: since IntelliJ compile our base code to class code and putting them into the out/ folder we will copy the classes of the out/production folder and we will paste it into the ```srcr``` folder aside the Main.class . If you dont work with an ide that compile the code you can compile all the java files to class files using ``` javac -d ./srcr/ (Get-ChildItem -Recurse -Filter *.java -Path ./src).FullName``` this command will recompile the main file , delete the old one .
    - **Creating of the MANIFEST.MF**: we will create a mf file that reference to the Main.class | MANIFEST file will include : ```Manifest-Version: 1.0   
      Main-Class: Main  ```
    - **Creating the jar **:  now we will run the command that point to the manifest file and the folder srcr like so : ```jar cfm costify.jar src/MANIFEST.MF -C srcr . ```| where the srcr folder contains the compiled files
    - **Executing the Jar**: running ```java -jar costify.jar``` will execute the jar | we need to be at the same path as the jar to have the permission to execute it using the command line .

## 🛠 Skills
<p>
    <img src="https://skillicons.dev/icons?i=git,idea,java" height="45" alt="skills"  />
</p>
