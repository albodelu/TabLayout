The project creates a tab layout with fragments. But the data isn't getting properly loaded into the fragments. The repository contains the code and details about the issue is shared here http://stackoverflow.com/questions/42280813/recyclerview-onbindviewholder-called-only-once-inside-tab-layout.

Any help is appreciated.

![Tab Layout Screenshot 1](https://raw.github.com/gSrikar/TabLayout/master/screeenshots/Screenshot_2017-02-21-20-26-22-331.jpeg)

![Tab Layout Screenshot 2](https://raw.github.com/gSrikar/TabLayout/master/screeenshots/Screenshot_2017-02-21-20-26-16-577.jpeg)


**Here is the App flow**

App launched -> MainActivity -> LocalFragment

Local Fragment has a Button, Slider and a Tab layout.

First Tab loads FabDealsFragment, second one loads FoodDrinksFragment, Third one loads HotelsFragment, Fourth one loads OthersFragment.

The data inside these fragments is loaded using a helper class InitializeLocalFragment.

Finally, Sample JSON data is in the samplejson folder.

**Weird behaviour I found while testing**

Even with one tab I'm facing the same issue as long as I'm not loading `FoodDrinksFragment` (It is the fragment that is loaded usually in the second tab and is currently showing all the tabs as shown in the fig).

When single tab or all tabs load `FoodDrinksFragment`, all 20 items are loaded as expected. Similarly, whatever is the position of the `FoodDrinksFragment`.

All these Four Fragments have the same lines of code.

I'm passing the `categoryName` as one of the argument to the helper class `InitializeLocalFragment`. If I pass Food Drinks as the category name via `FabDealsFragment` or `FoodDrinksFragment` all the items are loaded but if I pass the same category name via `HotelsFragment` or `OthersFragment` then the adapter's `onBindViewHolder` is only called once.

The issue is not definitely with the adapter size. The below image shoes just that https://github.com/gSrikar/TabLayout/blob/master/screeenshots/adapter_size.png


