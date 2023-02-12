## Vcrypt
A simple Java Library that provides Encryptation functionalities for casual purposes.

All the implementations are made through the <b>dalosto.encode.Vcrypt</b> Class, providing:

```java
/** Encode the raw password */
String encode(String password);

/** Returns an encoded hash in the format: {hashSalt}hashPassword */
String encodeUsingSalt(String password, String salt);

/** Verify if the password after encoded matches the encodedpassword*/
boolean matches(String password, String encodedPassword);
```

The program allows for easy change of key size and salt size.

Default values are: <b>256bits</b> Salt and <b>1024bits</b> for the password, resulting in a 162-length encoded password.