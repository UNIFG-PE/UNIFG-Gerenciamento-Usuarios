
# 👤 User Registration Java Program

This is a simple Java console application that inserts user data into a MySQL database table called users. It collects user info from the terminal and saves it with a default ACTIVE status.

## 📦  Cloning the Project

To clone the project, open your terminal and run:

```bash
cd Desktop/
``` 

- Then execute the following command:

```bash
git clone https://github.com/UNIFG-PE/UNIFG-Gerenciamento-Usuarios.git
```

## 📦 Installing Dependencies

This Java program requires a JDBC driver for MySQL. Make sure you have it included in your classpath.

If using Maven, add this dependency:

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.32</version>
</dependency>
```

## 🔐 Environment Configuration

Before running the program, configure your database connection inside the code:

#### 📝 Step-by-step:

```java
String url = "jdbc:mysql://<host>:<port>/<your-database>";
String user = "<user>";
String password = "<your-password>";
```

Replace <host>, <port>, <your-database>, <user>, and <your-password> with your MySQL server details.


```env
MONGO_URI=mongodb://your-host.com:12345/mydatabase
```

⚠️ Make sure your MongoDB server is running and accessible through the given URI.

## ✨ Features

- Reads user input from the console: username, password, email, and CPF.
- Inserts new user record into the users table.
- Automatically sets the status field to ACTIVE on creation.

## 🚀 How to Run

1. Request:

```bash
Enter your name: "Maurício"

Enter your password: "1234"

Enter your email: "mclovin@dasilva.com"

Enter you cpf: "123.456.789-01"
```

2. Response:
```bash
Sucess for user creation!
```






