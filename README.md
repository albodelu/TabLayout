The project creates a tab layout with fragments. But the data isn't getting properly loaded into the fragments. The repository contains the code and details about the issue is shared here http://stackoverflow.com/questions/42280813/recyclerview-onbindviewholder-called-only-once-inside-tab-layout.

Any help is appreciated.

![Tab Layout Screenshot 1](https://raw.github.com/gSrikar/TabLayout/master/screeenshots/Screenshot_2017-02-21-20-26-22-331.jpeg)

![Tab Layout Screenshot 2](https://raw.github.com/gSrikar/TabLayout/master/screeenshots/Screenshot_2017-02-21-20-26-16-577.jpeg)


Here is the App flow

App launched -> MainActivity -> LocalFragment

Local Fragment has a Button, Slider and a Tab layout.

First Tab loads FabDealsFragment, second one loads FoodDrinksFragment, Third one loads HotelsFragment, Fourth one one loads OthersFragment.

The data inside these fragments is loaded using a helper class InitializeLocalFragment.
