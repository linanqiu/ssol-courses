ssol-courses
============

https://github.com/linanqiu/ssol-courses

Register for courses on Columbia SSOL

## Team Members
- Linan Qiu (lq2137)
- Xingzhou He (xh2187)

## Features
- Automatically tries to get courses on SSOL
- Pick courses from the Columbia Bulletin
- Parses current schedule and adds courses using a GUI
- Keeps retrying for courses until you get the courses
- Optimal timing to avoid having IP blocked
- Wait timer for IP blocking

## Dependencies
### Selenium
We will be using the Selenium library for interactions with the site. It saves a lot of time in web automation.

### Java
Cross platform compatibility

## Homework Requirements
### Red Features
#### Interfaces
Interfaces will be used to define courses fetched from the bulletin. We will probably implement multithreading somewhere, so that's another interface. 

#### Inheritance
Selenium features different `WebDrivers` for automation. We are using `HtmlUnitDriver`, a subclass of `WebDriver` that is a minimalist embedded "browser" that does not require the presence of a browser (unlike `ChromeDriver`, `FirefoxDriver` etc)

### Blue Features
#### Java Graphics
Because we want our app to be used by people other than computer science majors. Also, my girlfriend wants to use this.

#### Networking
Because we interact with the site.

#### Multithreading
Because we want things to run fast

#### Advanced Java
We are using external libraries. 

## Work Breakdown
We will collaborate on GitHub. One of us will work on getting the Bulletin fetcher, while the other works on the SSOL component.