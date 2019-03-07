# Island-NavigationBar
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Minimal requirements and features
- Supports API >= 17
- Highly customizable
- Easy to integrate
- Supports initializing via both the **XML** and inflating from **menu.xml**

## Usage

| IslandBar from menu | IslandBar from XML | IslandBar with stroke tabs |
| --- | --- | --- |
| ![Inflated from menu](https://github.com/kartollika/Island-NavigationBar/raw/master/samples/from_menu.gif) | ![Inflated from XML](https://github.com/kartollika/Island-NavigationBar/raw/master/samples/xml.gif) | ![Inflated from XML with stroke tabs](https://github.com/kartollika/Island-NavigationBar/raw/master/samples/xml_stroked.gif) |

**Attention!** 
Unfortunately, you can not create `IslandNavigationBar` by your own using code only, so you should firstly add `IslandNavigationBar` to your ***layout.xml***

## Initialize bar
```xml
<com.kartollika.views.islandnavigationbar.IslandNavigationBarView
        android:id="@+id/navigation_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
```

### Customizing 

| **Attribute**  | **Description** |
|:---:|---|
| barDistribution | Distribution of navigation bar items. Available values: `spread`, `packed`, `spread_inside`, so they are equals to the same mode in *Constraint layout chains* |
| barMenu | Optional, if you want to inflate your bar using ***menu.xml*** |
| barSelectedTab | Initial selected tab |
| barToggleInterpolator | If you want to see other animation of changing items, you should override this attribute. `AccelerateDecelerateInterpolator` is using by default |
| barTabToggleDuration | Duration of toggling tabs |

**Note**: you can change this parameters both via XML and code

All methods are listed in ***IslandNavigationBar*** interface:

| **Methods** |
| --- |
| fun inflateBarFromMenu(@MenuRes menuRes: Int) |
| fun setBarSelectedTab(position: Int) |
| fun setBarToggleTabsInterpolator(interpolator: Interpolator) |
| fun setBarTabsToggleDuration(duration: Int) |
| fun setBarTabsDistribution(chainMode: IslandNavigationBarView.ChainMode)|
| fun setOnTabActionListener(listener: IslandNavigationBarView.OnTabActionListener) |

**Example:** 

```kotlin
islandBar.apply { 
    setChainStyleMode(IslandNavigationBarView.ChainMode.PACKED)            
}
```

#### And now you are ready to add items to your bar!

## Initialize tabs

- **Add items via Using XML:**
```xml
   <com.kartollika.views.islandnavigationbar.IslandNavigationTabView
           android:id="@+id/tabProfile"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           app:tabBackground="@drawable/tab_person_background"
           app:tabIcon="@drawable/person_selector"
           app:tabTitle="Profile" />

   <com.kartollika.views.islandnavigationbar.IslandNavigationTabView
       android:id="@+id/tabFavorite"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       app:tabBackground="@drawable/tab_favorite_background"
       app:tabIcon="@drawable/favorite_selector"
       app:tabTitle="Favorite" />
       
   <!-- Add more child items here - max upto 5 -->
```

- **Or using menu.xml**

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/tab_home"
        android:icon="@drawable/home_selector"
        android:title="Home" />
    <item
        android:id="@+id/tab_alarm"
        android:icon="@drawable/alarm_selector"
        android:title="Alarm" />
        
    <!-- Add more child items here - max upto 5 -->
```

### Now you can customize tabs by your own!

| **Attribute**  | **Description** |
|:---:|---|
| tabTitle | Title of tab |
| tabIcon | Icon of tab. If you want to change tint dynamically - use selector for icon, where active state has `state_selected="true"` |
| tabBackground | Background of tab. Use `<transition>` to make smooth animation of changing state. Otherwise, use usual drawable and so on |
| tabToggleDuration | Duration of applying animations, such as text appearing and dissapearing, change background color, etc |
| tabTitleActiveColor | Change color of title when active state |
| tabTitleInactiveColor |  Change color of title when inactive state  |
| tabCustomPadding |  Change default padding by all directions. **Default** is 12dp  |

All methods are listed in ***IslandNavigationTab*** interface:

| **Methods** |
| --- |
| fun setTabTitle(title: String) |
| fun setTabIcon(drawable: Drawable?) |
| fun setTabToggleDuration(duration: Int) |
| fun setTabBackground(background: Drawable?) |
| fun setTabTitleActiveColor(color: Int) |
| fun setTabTitleInactiveColor(color: Int) |
| fun setTabTitleColorsStateList(colorStateList: ColorStateList) |
| fun setCustomInternalPadding(padding: Int) |
   
#### Sample of customization

- **From code**

```kotlin
with(islandBar) {
    getTabById(R.id.tab_home)?.let {
        it.setTabTitleActiveColor(Color.parseColor("#8122B4"))
        it.setTabBackground(
            ContextCompat.getDrawable(
                this@MenuIslandBarActivity,
                R.drawable.tab_home_background
            )
        )
        it.setTabToggleDuration(300)
    }
    getTabById(R.id.tab_person)?.let {
        it.setTabTitleActiveColor(Color.parseColor("#C90000"))
        it.setTabBackground(
            ContextCompat.getDrawable(
                this@MenuIslandBarActivity,
                R.drawable.tab_person_background
            )
        )
        it.setTabToggleDuration(300)
    }
}
```

- **Or using XML**

```xml
<com.kartollika.views.islandnavigationbar.IslandNavigationTabView
    android:id="@+id/tabProfile"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:tabBackground="@drawable/tab_person_background"
    app:tabIcon="@drawable/person_selector"
    app:tabToggleDuration="200"
    app:tabTitleActiveColor="@color/colorPrimaryDark"
    app:tabTitle="Profile" />
```




