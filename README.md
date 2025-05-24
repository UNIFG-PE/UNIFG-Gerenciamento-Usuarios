
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

Replace *"host"*, *"port"*,*"your-database"*, *"user"*, and *"your-password"* with your MySQL server details.

## ✨ Features

- **1 - Register user:** Allows you to register a new user by providing name, address, telephone number, email and date of birth.
- **2 - Edit user:** Allows that admin edit the data of an existing user.
- **3 - Show user by ID:** Displays user details from the given ID.
- **4 - Delete user:** Allows the admin to remove a user from the system by ID.
- **0 - Exit:** Closes the program.

## 📌 Link for Features:

<a name="return-to-links-page-book"></a>
- [Register user](#register-user)
- [Edit user](#edit-user)
- [Show user by ID](#show-user-by-id)
- [Delete user](#delete-user)

## 🚀 How to Run

## ✅ 1. Register User
<a name="register-user"></a>

1. Request(Console Input):

```bash
Enter your name: "Maurício"

Enter your address: "Rua dois Pinheiros"

Enter your telephone: "(01)234567891"

Enter you e-mail: "mclovin@gmail.com"

Enter you date of birth(dd-MM-yyyy): "01-02-3456"

```

2. Response:
```bash
Sucess for user creation!
```

- [Return to links for features](#return-to-links-for-features)

## ✏️ 2. Edit User
<a name="edit-user"></a>

When this option is selected, the system prompts the user whether they want to update their information.

#### 🟡 Step 1: Confirmation

```bash
Do you want to update your data? (yes/no): yes
```

#### 🟡 Step 2: Enter New Information

````bash
New name: "Maurício Silva"
New address: "Rua três Pinheiros"
New telephone: "(01)987654321"
New e-mail: "mauricio.silva@gmail.com"
New date of birth(dd/MM/yyyy): "02/03/4567"
````

#### 🔁 The system will then attempt to update the user in the database:

* If succesful:
````bash
User updated successfully!
````
*If not:
```bash
Failed to update user.
```

### ⚠️ Error Handling

If an SQL error occurs during the update:

```bash
Error to update user: <error message>
```

- [Return to links for features](#return-to-links-for-features)

## 🔍 3. Search User
<a name="show-user-by-id"></a>

When this option is selected, the system prompts the user whether they want to update their information.

#### 🟢 Step 1: Enter Email

```bash
Enter your email: mclovin@gmail.com
```

#### 🔎 Step 2: System Behavior

* If the user exists, the system will print their data:

````bash
=================
Found User: 
ID: 1
Name: Maurício
Address: Rua dois Pinheiros
Telephone: (01)234567891
Email: mclovin@gmail.com
Date of Birth: 01-02-3456
=================
````

* If the user is an Administrator, it will also list all users:

````bash
-------------------------------
ID: 1
Name: Maurício
Email: mclovin@gmail.com
User Type: Administrator
-------------------------------
ID: 2
Name: João
Email: joao@gmail.com
User Type: Regular
````

* If the user is not an Administrator:

```bash
Access denied. Only Administrators can view all users.
```

* If no user is found:

```bash
No user found with email: example@email.com
```

### ⚠️ Error Handling

If an SQL error occurs during the update:

```bash
Error while updating user: <error message>
```

- [Return to links for features](#return-to-links-for-features)

## 🗑️ 4. Delete User
<a name="delete-user"></a>

Only users with the Administrator role are allowed to delete other users. This feature verifies the admin's email before proceeding with the deletion.

#### 🟢 Step 1: Admin Verification

```bash
Enter your email (to check if you're an administrator): admin@example.com
```

#### 🟡 Step 2: Enter New Information

````bash
New name: "Maurício Silva"
New address: "Rua três Pinheiros"
New telephone: "(01)987654321"
New e-mail: "mauricio.silva@gmail.com"
New date of birth(dd/MM/yyyy): "02/03/4567"
````

* If the email does not belong to an administrator:

```bash 
Access denied. Only administrators can delete users.
```

* If the email is not found in the database:

```bash
Administrator not found with the given email.
```

#### 🗑️ Step 2: Enter User Email for Deletion

```bash
Enter the email of the user you want to delete: user@example.com
```

* If the deletion is successful:

```bash
User deleted successfully.
```

* If no user is found with the given email:

```bash
No user found with the provided email, please try again.
```

### ⚠️ Error Handling

If any SQL-related error occurs during the process:

```bash
Error during deletion: <error message>
```

- [Return to links for features](#return-to-links-for-features)


  






