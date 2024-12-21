# Flight

## Objectives

How can we reduce the time it takes for ideas to get into the hands of mobile users? How do technologies such as generative AI and machine learning fit into a mobile landscape? How can we reduce duplication across mobile platforms by removing logic from the application?

This app is intended to tackle these problems and provide a test bed for future questions. Here are some more, concrete, questions I seek to answer:

1. How can businesses reduce the time it takes for code to from idea into a user's hand? 
    i. Experiment with out [cashapp/zipline](https://github.com/cashapp/zipline)
2. How can the working development exeprience be improved by avoiding long compilation times?
    i. Can we use [dropbox/focus](https://github.com/dropbox/focus) to reduce the amount of Gradle modules configured
    ii. Can we make heavy usage of demo applications to reduce compilation required to test the application
    iii. Can we use Compose and Kotlin multiplatform to avoid compilation of Android
3. How do you actually design a design system?
   i. [cashapp/molecule](https://github.com/cashapp/redwood)
4. Reactive Streams are great, Rx or Flow we have the same concepts. Can we replace that with imperative coding?
    i. [cashapp/molecule](https://github.com/cashapp/molecule)
    ii. [slackhq/circuit](https://github.com/slackhq/circuit)
5. How can we experiment with testing to allow us to move very quickly?

## Features

- Search for the flights using flight numbers
- View arrivals and departures from the