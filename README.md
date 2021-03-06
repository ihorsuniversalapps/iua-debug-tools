# IUA Debug Tools

Simple dialog for debugging purposes with logger.

![screenshot 1](https://github.com/ihorsuniversalapps/iua-debug-tools/raw/master/screenshot1.png "ScreenShot Of DebugPanel")

## Getting started

### Dependency

[Download](https://bintray.com/phoenixria/maven/iua-debug-tools/1.0.3/view)

Include the dependency:

```groovy
dependencies {
    compile 'ua.in.iua:iua-debug-tools:1.0.3'
}
```
### Usage

Put next code in your `Activity` class in the `#onCreate()` method, for instance
(In the reality you should open it from button click listener or some another trigger).

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final Logger logger = new LoggerImpl(100, true, LogType.DEBUG);

    logger.i(this, "Logger initialized");

    Collection<LogMessage> messages = logger.getMessages();
    List<LogMessage> messageList = new ArrayList<>(messages);
    DebugPanelDialog panel = new DebugPanelDialog.newInstance(
        "serverName", 
        BuildConfig.VERSION_NAME,
        new LogAdapter(this, Logger.getInstance().getLogMessages()));
    panel.show(this);
}
```
### Logger

For adding items to log call:

```java
final Logger logger = new LoggerImpl(100, true, LogType.DEBUG);

logger.i(this, "Info");
logger.d(this, "Debug");
logger.v(this, "Verbose");
logger.w(this, "Warning");
logger.e(this, "Error");
```

All logged records you can see in the debug panel.

# License

The MIT License (MIT)

Copyright (c) 2015 ihorsuniversalapps

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
