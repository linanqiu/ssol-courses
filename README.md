ssol-courses
============

Register for courses on SSOL Columbia

## Prerequisites
- Decent JRE
- Mac OSX. Sorry Windows users. Didn't have time to build one for you.

## To Run
- Download SSOL.jar
- Download ChromeDriver and place in the same directory.
- Run SSOL.jar

## Inputs
The error handling is really weak because I didn't have much time to code this, so make sure you follow every one of these instructions.

Only enter course ID on the left. Each course should be separated by a break. For example, if I want to sign up for the courses "55555", "43434", and "12312", I will type in:

    55555
    43434
    12312

and nothing more.

Enter your UNI and password. This should be a no brainer.

Interval is the time (in milliseconds) interval between successive tries. SSOL blocks you if you log in too often, and I have no intention (public at least) to spoof the system. Also, don't abuse the system lest you clog it (and bar me from signing up for my own classes). 

**Interval should at least be 20000. If you don't want to risk a ban, put it at 60000. You are asking for it if you place a 1 there.**

Tries is the number of times it tries to sign up for the courses on the left.

## Built Using
- Selenium IDE

