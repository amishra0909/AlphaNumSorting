In most of the programming languages strings are sorted using their ASCII value. This works for most parts for a machine but sometimes is non-sensical to humans. For example if a string array looks {a1, a2, a13 a113}, ASCII based sorting will result in {a1, a13, a113, a2} but for human readability it will be better to sort it order as {a1, a2, a13, a113}.
This project aims at providing a human readable sorting solution of the above kind for Java.

The idea is to be able to split the string into sections of alphabets and numerics and sort based on the sections. Alphabetic parts are compared using the ASCII value of characters in consideration and numeric parts should be compared using the absolute value of the number.
